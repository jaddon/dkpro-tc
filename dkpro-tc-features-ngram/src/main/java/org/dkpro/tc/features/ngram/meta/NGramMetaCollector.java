/*******************************************************************************
 * Copyright 2015
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.dkpro.tc.features.ngram.meta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.model.DfModel;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.util.TfidfUtils;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.util.FeatureUtil;
import org.dkpro.tc.api.type.TextClassificationUnit;
import org.dkpro.tc.features.ngram.base.FrequencyDistributionNGramFeatureExtractorBase;
import org.dkpro.tc.features.ngram.base.NGramFeatureExtractorBase;
import org.dkpro.tc.features.ngram.util.NGramUtils;

public class NGramMetaCollector
    extends FreqDistBasedMetaCollector
{
    public static final String NGRAM_FD_KEY = "ngrams.ser";

    @ConfigurationParameter(name = FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_FD_FILE, mandatory = true)
    private File ngramFdFile;
    
    @ConfigurationParameter(name = FrequencyDistributionNGramFeatureExtractorBase.PARAM_DFSTORE_FILE, mandatory = true)
    private File dfstoreFile;

    @ConfigurationParameter(name = NGramFeatureExtractorBase.PARAM_NGRAM_MIN_N, mandatory = true, defaultValue = "1")
    private int ngramMinN;

    @ConfigurationParameter(name = NGramFeatureExtractorBase.PARAM_NGRAM_MAX_N, mandatory = true, defaultValue = "3")
    private int ngramMaxN;

    @ConfigurationParameter(name = NGramFeatureExtractorBase.PARAM_NGRAM_STOPWORDS_FILE, mandatory = false)
    private String ngramStopwordsFile;
    
    @ConfigurationParameter(name = NGramFeatureExtractorBase.PARAM_FILTER_PARTIAL_STOPWORD_MATCHES, mandatory = true, defaultValue="false")
    private boolean filterPartialStopwordMatches;

    @ConfigurationParameter(name = NGramFeatureExtractorBase.PARAM_NGRAM_LOWER_CASE, mandatory = true, defaultValue = "true")
    private boolean ngramLowerCase;

    private  Set<String> stopwords;
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        
        try {
            stopwords = FeatureUtil.getStopwords(ngramStopwordsFile, ngramLowerCase);
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }
    
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	try{
    		dfStore.registerNewDocument();
    		TextClassificationUnit target = JCasUtil.selectSingle(jcas, TextClassificationUnit.class);
	        FrequencyDistribution<String> documentNGrams = NGramUtils.getDocumentNgrams(
	                jcas, target, ngramLowerCase, filterPartialStopwordMatches, ngramMinN, ngramMaxN, stopwords);  
	        for (String ngram : documentNGrams.getKeys()) {
	            fd.addSample(ngram, documentNGrams.getCount(ngram));
	            dfStore.countTerm(ngram);
	        }    
	        dfStore.closeCurrentDocument();
    	} catch(TextClassificationException e){
    		throw new AnalysisEngineProcessException(e);
    	}
    }

    @Override
    public Map<String, String> getParameterKeyPairs()
    {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put(FrequencyDistributionNGramFeatureExtractorBase.PARAM_NGRAM_FD_FILE, NGRAM_FD_KEY);
        mapping.put(FrequencyDistributionNGramFeatureExtractorBase.PARAM_DFSTORE_FILE, DfModel.FILE_NAME);
        return mapping;
    }

    @Override
    protected File getFreqDistFile()
    {
        return ngramFdFile;
    }
    
    @Override
    protected File getDfStoreFile()
    {
        return dfstoreFile;
    }
    
    
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();

        try {
            TfidfUtils.writeDfModel(dfStore, getDfStoreFile().getAbsolutePath());
        }
        catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

}

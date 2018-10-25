/*******************************************************************************
 * Copyright 2018
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
package org.dkpro.tc.ml.vowpalwabbit;

import java.io.File;

import de.tudarmstadt.ukp.dkpro.core.api.resources.RuntimeProvider;

public class TestDummy {
	
	private static final String classpath = "classpath:/org/dkpro/tc/ml/vowpalwabbit/";
	static RuntimeProvider runtimeProvider = null;
	public static void main(String[] args) throws Exception {
		runtimeProvider = new RuntimeProvider(classpath);
		File file = null;
		
		try{
		    file = runtimeProvider.getFile("vw");
		}catch(Exception e) {
		    System.out.println(e.getMessage());
		    int a=0;
		    a++;
		}
		
		
		
		System.out.println(file.getAbsolutePath());
	}

}

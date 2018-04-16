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
package org.dkpro.tc.core.ml.builder;

import org.dkpro.tc.core.Constants;

public enum FeatureMode implements Constants
{
    DOCUMENT(FM_DOCUMENT), 
    UNIT(FM_UNIT),
    PAIR(FM_PAIR),
    SEQUENCE(FM_SEQUENCE);

    private String name;

    private FeatureMode(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}

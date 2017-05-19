/*******************************************************************************
 * Copyright 2017
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
package org.dkpro.tc.core.ml;

import org.dkpro.lab.reporting.ReportBase;
import org.dkpro.lab.task.impl.TaskBase;

/**
 * Interface for machine learning frameworks in TC
 */
public interface TcDeepLearningAdapter
{
    public static final String PREPARATION_FOLDER = "preparationFolder";

    /*
     * Usually the init-test task to retrieve that document/instance N was document/instance
     * ABC.txt/walking
     */
    public static final String TARGET_ID_MAPPING = "targetIdFolder";

    /**
     * @return The task that reads the ML feature store format, trains the classifier and stores the
     *         test results.
     */
    public TaskBase getTestTask();

    /**
     * This report is always added to {@code testTask} reports by default in
     * ExperimentCrossValidation and ExperimentTrainTest.
     *
     * @return The report that collects the outcomeId to prediction values.
     */
    public Class<? extends ReportBase> getOutcomeIdReportClass();

}
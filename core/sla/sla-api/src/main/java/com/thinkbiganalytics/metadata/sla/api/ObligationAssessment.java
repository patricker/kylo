/**
 *
 */
package com.thinkbiganalytics.metadata.sla.api;

/*-
 * #%L
 * thinkbig-sla-api
 * %%
 * Copyright (C) 2017 ThinkBig Analytics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.util.Set;

/**
 * Described a assessment of an obligation.
 */
public interface ObligationAssessment extends Comparable<ObligationAssessment>, Serializable {

    /**
     * @return the obligation that was assesssed
     */
    Obligation getObligation();

    /**
     * @return a message describing the result of the assessment
     */
    String getMessage();

    /**
     * @return the result status of the assessment
     */
    AssessmentResult getResult();

    /**
     * @return the assessments of all metrics of this obligation
     */
    <D extends Serializable> Set<MetricAssessment<D>> getMetricAssessments();

}

/**
 *
 */
package com.thinkbiganalytics.alerts.spi;

/*-
 * #%L
 * thinkbig-alerts-api
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

import com.thinkbiganalytics.alerts.api.Alert;
import com.thinkbiganalytics.alerts.api.AlertCriteria;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 *
 */
public interface AlertSource {

    AlertCriteria criteria();

    Alert.ID resolve(Serializable value);

    Set<AlertDescriptor> getAlertDescriptors();

    void addReceiver(AlertNotifyReceiver receiver);

    void removeReceiver(AlertNotifyReceiver receiver);

    Optional<Alert> getAlert(Alert.ID id);

    Iterator<Alert> getAlerts(AlertCriteria criteria);
}

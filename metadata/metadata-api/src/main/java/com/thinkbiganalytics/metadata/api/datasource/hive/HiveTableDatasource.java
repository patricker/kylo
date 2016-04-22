/**
 * 
 */
package com.thinkbiganalytics.metadata.api.datasource.hive;

import com.thinkbiganalytics.metadata.api.datasource.Datasource;

/**
 *
 * @author Sean Felten
 */
public interface HiveTableDatasource extends Datasource {

    String getDatabaseName();
    
    String getTableName();
}
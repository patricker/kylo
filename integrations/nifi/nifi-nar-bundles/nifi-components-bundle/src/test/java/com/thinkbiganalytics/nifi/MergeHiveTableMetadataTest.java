package com.thinkbiganalytics.nifi;

import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jeremy Merrifield on 2/22/16.
 */
public class MergeHiveTableMetadataTest {
    private InputStream testDocument;
    private TestRunner nifiTestRunner;

    @Before
    public void setUp() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        testDocument = classloader.getResourceAsStream("tableMetadata/test.json");
    }

    //@Test
    public void tester() {
        runProcessor(testDocument);
        assertTrue(true);

    }

    private void runProcessor(InputStream testDocument) {
        nifiTestRunner = TestRunners.newTestRunner(new MergeHiveTableMetadata()); // no failures
        nifiTestRunner.setValidateExpressionUsage(true);
        nifiTestRunner.setProperty(MergeHiveTableMetadata.DATABASE_NAME, "NAME");
        nifiTestRunner.setProperty(MergeHiveTableMetadata.DATABASE_OWNER, "OWNER_NAME");
        nifiTestRunner.setProperty(MergeHiveTableMetadata.TABLE_CREATE_TIME, "CREATE_TIME");
        nifiTestRunner.setProperty(MergeHiveTableMetadata.TABLE_NAME, "TBL_NAME");
        nifiTestRunner.setProperty(MergeHiveTableMetadata.TABLE_TYPE, "TBL_TYPE");
        nifiTestRunner.setProperty(MergeHiveTableMetadata.COLUMN_NAME, "COLUMN_NAME");
        nifiTestRunner.setProperty(MergeHiveTableMetadata.COLUMN_TYPE, "TYPE_NAME");
        nifiTestRunner.assertValid();

        nifiTestRunner.enqueue(testDocument, new HashMap<String, String>() {{
            put("doc_id", "8736522777");
        }});
        nifiTestRunner.run(1, true, true);

        nifiTestRunner.assertAllFlowFilesTransferred(IndexElasticSearch.REL_SUCCESS, 1);
        final MockFlowFile out = nifiTestRunner.getFlowFilesForRelationship(IndexElasticSearch.REL_SUCCESS).get(0);
        String outgoingJson = new String(out.toByteArray());
        assertNotNull(out);
        out.assertAttributeEquals("doc_id", "8736522777");
    }
}
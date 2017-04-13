package com.yourcompany.Tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.yourcompany.Pages.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import static org.junit.Assert.*;

public class FollowLinkTest extends TestBase {

    private static BatchInfo batchInfo;

    public FollowLinkTest(String os,
                          String version, String browser, String deviceName, String deviceOrientation, String resolution) {
        super(os, version, browser, deviceName, deviceOrientation, resolution);
    }

    /**
     * Runs a simple test verifying link can be followed.
     * @throws InvalidElementStateException
     */
    @Test
    public void verifyLinkTest() throws InvalidElementStateException {
        // group tests under the same batch instance.
        eyes.setBatch(batchInfo);

        // Start visual testing with browser viewport set to 1024x768.
        // Make sure to use the returned driver from this point on.
        driver = eyes.open(driver, "Applitools", "Test Web Page", new RectangleSize(1024, 768));


        GuineaPigPage page = GuineaPigPage.visitPage(driver);

        // Visual validation point #1
        eyes.checkWindow("Main Guinea Pig Page");


        page.followLink();

        // Visual validation point #2
        eyes.checkWindow("Followup page");

        // End visual testing. Validate visual correctness.
        eyes.close();
    }
    @BeforeClass
    public static void batchTestsTogether()
    {
        String JenkinsBatchId = System.getenv("APPLITOOLS_BATCH_ID");

        // If the test runs via Jenkins, set the batch ID accordingly.
        if (JenkinsBatchId != null) {
            batchInfo = new BatchInfo(System.getenv("JOB_NAME"));
            batchInfo.setId(JenkinsBatchId);
        }
        else{
            batchInfo = new BatchInfo("Applitools and Sauce Example Tests");
        }
    }

}
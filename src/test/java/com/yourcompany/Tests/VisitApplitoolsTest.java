package com.yourcompany.Tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.yourcompany.Pages.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import java.net.MalformedURLException;

public class VisitApplitoolsTest extends TestBase {

	private static BatchInfo batchInfo;

	public VisitApplitoolsTest(String os, String version, String browser, String deviceName, String deviceOrientation, String resolution) {
		super(os, version, browser, deviceName, deviceOrientation, resolution);
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
			batchInfo = new BatchInfo("Example Tests");
		}
	}
	
	/**
	 * Runs a simple test verifying link can be followed.
	 * 
	 * @throws InvalidElementStateException
	 * @throws MalformedURLException 
	 */
	@Test
	public void verifyLinkTest() {

		// group tests under the same batch instance.
		eyes.setBatch(batchInfo);
		
		// Start visual testing with browser viewport set to 1024x768.
		// Make sure to use the returned driver from this point on.
		driver = eyes.open(driver, "Applitools", "Test Web Page", new RectangleSize(1024, 768));

		ApplitoolsMainPage mainPage = ApplitoolsMainPage.visitPage(driver);

		// Visual validation point #1
		eyes.checkWindow("Main Page");

		ApplitoolsFeaturesPage featuresPage = mainPage.goToFeaturesPage();

		// Visual validation point #2
		eyes.checkWindow("Features page");

		// End visual testing. Validate visual correctness.
		eyes.close();
	}
}
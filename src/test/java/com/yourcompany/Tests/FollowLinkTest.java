package com.yourcompany.Tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.yourcompany.Pages.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import static org.junit.Assert.*;

public class FollowLinkTest extends TestBase {


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

        // Start visual testing with browser viewport set to 1024x768.
        // Make sure to use the returned driver from this point on.

        GuineaPigPage page = GuineaPigPage.visitPage(driver);

        // Visual validation point #1
        eyes.checkWindow("Main Guinea Pig Page");


        page.followLink();

        // Visual validation point #2
        eyes.checkWindow("Followup page");

    }
}
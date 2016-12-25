package com.yourcompany.Tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.net.URL;
import java.util.LinkedList;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;



/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API to mark
 * the test as passed or failed.
 *
 * @author Neil Manvar
 */
@Ignore
@RunWith(ConcurrentParameterized.class)
public class TestBase implements SauceOnDemandSessionIdProvider {

    public static String username = System.getenv("SAUCE_USERNAME");
    public static String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    public static String seleniumURI;
    public static String buildTag;
    public static String applitoolsApiKey = System.getenv("APPLITOOLS_ACCESS_KEY");
    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    protected String browser;
    protected String os;
    protected String version;
    protected String deviceName;
    protected String deviceOrientation;
    protected String sessionId;
    protected String screenResolution;
    protected WebDriver driver;
    protected Eyes eyes;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param os
     * @param version
     * @param browser
     * @param deviceName
     * @param deviceOrientation
     */

    public TestBase(String os, String version, String browser, String deviceName, String deviceOrientation, String resolution ) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
        this.deviceName = deviceName;
        this.deviceOrientation = deviceOrientation;
        this.screenResolution = resolution;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();

        browsers.add(new String[]{"Windows 10", "14.14393", "MicrosoftEdge", null, null,"1280x1024"});
        browsers.add(new String[]{"Windows 10", "49.0", "firefox", null, null,"1280x1024"});
        browsers.add(new String[]{"Windows 7", "11.0", "internet explorer", null, null,"1280x1024"});
        browsers.add(new String[]{"OS X 10.10", "54.0", "chrome", null, null,"1280x1024"});
        return browsers;
    }
    
    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #browser},
     * {@link #version} and {@link #os} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("device-orientation", deviceOrientation);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("screenResolution", screenResolution);
        

        String methodName = name.getMethodName();
        capabilities.setCapability("name", methodName);

        //Getting the build name.
        //Using the Jenkins ENV var. You can use your own. If it is not set test will run without a build id.
        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }
        this.driver = new RemoteWebDriver(
                new URL("https://" + username+ ":" + accesskey + seleniumURI +"/wd/hub"),
                capabilities);

        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        
        setupApplitools();
    }

    private void setupApplitools() {
		this.eyes = new Eyes();
		this.eyes.setApiKey(this.applitoolsApiKey);
		this.eyes.setForceFullPageScreenshot(true);
		this.eyes.setStitchMode(StitchMode.CSS);
	}

	@After
    public void tearDown() throws Exception {
        driver.quit();
        eyes.abortIfNotClosed();
    }

    /**
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }

    @BeforeClass
    public static void setupClass() {
        //get the uri to send the commands to.
        seleniumURI = "@ondemand.saucelabs.com:443";
        //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
        //You can set this manually on manual runs.
        buildTag = System.getenv("BUILD_TAG");
    }
}

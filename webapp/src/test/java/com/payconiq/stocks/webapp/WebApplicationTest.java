package com.payconiq.stocks.webapp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple WebApplication.
 */
public class WebApplicationTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WebApplicationTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(WebApplicationTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}

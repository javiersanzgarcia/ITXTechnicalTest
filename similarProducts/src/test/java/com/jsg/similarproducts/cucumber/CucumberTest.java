package com.jsg.similarproducts.cucumber;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
    @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.jsg.similarproducts.cucumber.e2e"),
    @ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true"),
    @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = 
        "pretty, " +
        "html:target/cucumber/cucumber.html, " +
        "json:target/cucumber/cucumber.json, " +
        "junit:target/cucumber/cucumber.xml")
})
public class CucumberTest {
}

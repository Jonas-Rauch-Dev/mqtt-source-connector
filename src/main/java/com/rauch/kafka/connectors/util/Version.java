package com.rauch.kafka.connectors.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Version {

    private static final Logger logger = LoggerFactory.getLogger(Version.class);
    private static final String props_path = "/application.properties";
    private static String version = "?";

    static {
        try (InputStream stream = Version.class.getResourceAsStream(props_path)) {
            Properties props = new Properties();
            props.load(stream);
            version = props.getProperty("version", version).trim();
        } catch (Exception e) {
            logger.warn("Failed to retrieve project version: '{}'", e);
        }
    }

    public static String getVersion() {

        return "";
    }
}

package com.rauch.kafka.connectors.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Version {

    private static final Logger logger = LogManager.getLogger(Version.class);
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

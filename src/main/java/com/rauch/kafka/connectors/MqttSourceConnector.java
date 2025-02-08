package com.rauch.kafka.connectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MqttSourceConnector extends SourceConnector {

    private final static Logger logger = LogManager.getLogger(MqttSourceConnectorTask.class.getName());

    @Override
    public String version() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'version'");
    }

    @Override
    public ConfigDef config() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'config'");
    }

    @Override
    public void start(Map<String, String> props) {
        logger.info("Starting Connector...");
    }

    @Override
    public void stop() {
        logger.info("Stoping Connector...");
    }

    @Override
    public Class<? extends Task> taskClass() {
        return MqttSourceConnectorTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();

        return configs;
    }

}
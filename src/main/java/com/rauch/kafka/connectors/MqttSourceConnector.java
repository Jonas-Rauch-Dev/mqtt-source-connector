package com.rauch.kafka.connectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rauch.kafka.connectors.util.Version;

public class MqttSourceConnector extends SourceConnector {

    private final static Logger logger = LogManager.getLogger(MqttSourceConnectorTask.class.getName());
    private Map<String, String> props;

    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public ConfigDef config() {
        return MqttSourceConnectorConfig.DEFINITION;
    }

    @Override
    public void start(Map<String, String> props) {
        logger.info("Starting Connector...");
        this.props = props;
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
        configs.add(props);
        return configs;
    }
}
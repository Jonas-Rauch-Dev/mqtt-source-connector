package com.rauch.kafka.connectors;

import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MqttSourceConnectorTask extends SourceTask {

    private static final Logger logger = LogManager.getLogger(MqttSourceConnectorTask.class.getName());

    @Override
    public String version() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'version'");
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'poll'");
    }

    @Override
    public void start(Map<String, String> props) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public synchronized void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
}
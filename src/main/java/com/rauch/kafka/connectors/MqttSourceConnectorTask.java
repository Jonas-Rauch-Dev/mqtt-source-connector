package com.rauch.kafka.connectors;

import com.rauch.kafka.connectors.util.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttSourceConnectorTask extends SourceTask implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttSourceConnectorTask.class.getName());

    private MqttSourceConnectorConfig config;
    private MqttClient mqttClient;

    private String mqttBrokerTopic;
    private String kafkaTopic;

    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        return new ArrayList<>();
    }

    @Override
    public void start(Map<String, String> props) {
        config = new MqttSourceConnectorConfig(props);

        // Get the mqtt and kafka topics
        kafkaTopic = config.getString(MqttSourceConnectorConfig.KAFKA_TOPIC);
        mqttBrokerTopic = config.getString(MqttSourceConnectorConfig.MQTT_BROKER_TOPIC);

        // Get mqtt broker specific properties required for client init
        String mqttBrokerUri = config.getString(MqttSourceConnectorConfig.MQTT_BROKER_URI);
        String mqttBrokerClientId = config.getString(MqttSourceConnectorConfig.MQTT_BROKER_CLIENT_ID);
        String mqttBrokerUser = config.getString(MqttSourceConnectorConfig.MQTT_BROKER_USER);
        String mqttBrokerPassword = config.getString(MqttSourceConnectorConfig.MQTT_BROKER_PASSWORD);
        String mqttClientFilePersistanceDirectory = config
                .getString(MqttSourceConnectorConfig.MQTT_CLIENT_FILE_PERSISTENCE_DIRECTORY);
        int mqttClientQoS = config.getInt(MqttSourceConnectorConfig.MQTT_CLIENT_QOS);

        // Configure the mqtt connection options
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        mqttConnectOptions.setServerURIs(new String[] { mqttBrokerUri });
        mqttConnectOptions.setUserName(mqttBrokerUser);
        mqttConnectOptions.setPassword(mqttBrokerPassword.toCharArray());

        // Create the Mqtt Client
        try {
            mqttClient = new MqttClient(
                    mqttBrokerUri,
                    mqttBrokerClientId,
                    new MqttDefaultFilePersistence(mqttClientFilePersistanceDirectory));
        } catch (MqttException e) {
            logger.error("Failed to create MqttClient with error: {}", e);
            e.printStackTrace();
            return;
        }

        // Set the task itself as handler for mqtt callbacks
        mqttClient.setCallback(this);

        // Connect to the mqtt broker
        try {
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            logger.error("MqttClient failed to connect to the broker at '{}' with error: {}", mqttBrokerUri, e);
            e.printStackTrace();
            return;
        }

        // Check that the client is connected
        if (!mqttClient.isConnected()) {
            logger.error("MqttClient is not connected to the broker at '{}' with error: {}", mqttBrokerUri);
            return;
        }

        // Subscribe to the given mqtt topic
        try {
            mqttClient.subscribe(mqttBrokerTopic, mqttClientQoS);
        } catch (MqttException e) {
            logger.error("MqttClient failed to subscribe to topic '{}' with error: {}", mqttBrokerTopic, e);
            return;
        }

        logger.info("MqttClient Started Succesfully!");

    }

    @Override
    public synchronized void stop() {
        if (mqttClient != null) {
            // Try to disconnect if there is a connection
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                } catch (MqttException e) {
                    logger.warn("Failed to disconnect the MqttClient: {}", e);
                }
            }

            // Try to close the client
            try {
                mqttClient.close();
            } catch (MqttException e) {
                logger.warn("Failed to close the MqttClient: {}", e);
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.warn("MqttClient lost connection to the broker!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("MqttClient received event from the broker. {}: {}", topic, message.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("MqttClient deliveryComplete, token: {}", token);
    }
}
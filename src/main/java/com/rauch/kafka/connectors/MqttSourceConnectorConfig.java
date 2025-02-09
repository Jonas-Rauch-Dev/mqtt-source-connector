package com.rauch.kafka.connectors;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

public class MqttSourceConnectorConfig extends AbstractConfig {

        // MQTT CONFIG PROPERTIES
        public final static String MQTT_BROKER_URI = "mqtt.broker.uri";
        public final static String MQTT_BROKER_TOPIC = "mqtt.broker.topic";
        public final static String MQTT_BROKER_CLIENT_ID = "mqtt.broker.client.id";
        public final static String MQTT_BROKER_USER = "mqtt.broker.user";
        public final static String MQTT_BROKER_PASSWORD = "mqtt.broker.password";

        // MQTT CLIENT CONFIG PROPERTIES
        public final static String MQTT_CLIENT_FILE_PERSISTENCE_DIRECTORY = "mqtt.client.file.persistence.directory";

        // KAFKA CONFIG PROPERTIES
        public final static String KAFKA_TOPIC = "kafka.topic";

        public final static ConfigDef DEFINITION = new ConfigDef()
                        // MQTT BROKER DEFINITIONS
                        .define(
                                        MQTT_BROKER_URI,
                                        Type.STRING,
                                        "tcp://localhost:1883",
                                        Importance.HIGH,
                                        "The uri of the mqtt broker.")
                        .define(
                                        MQTT_BROKER_TOPIC,
                                        Type.STRING,
                                        "upstream/#",
                                        Importance.HIGH,
                                        "The mqtt topic to upstream into kafka.")
                        .define(
                                        MQTT_BROKER_CLIENT_ID,
                                        Type.STRING,
                                        "kafaka_mqtt_source_connector",
                                        Importance.HIGH,
                                        "The mqtt client id to use for this connector.")
                        .define(
                                        MQTT_BROKER_USER,
                                        Type.STRING,
                                        null,
                                        Importance.MEDIUM,
                                        "The mqtt user to use for authentication (only applicable for password based auth).")
                        .define(
                                        MQTT_BROKER_PASSWORD,
                                        Type.STRING,
                                        null,
                                        Importance.MEDIUM,
                                        "The password for the given mqtt user (only applicable for password based auth).")
                        // MQTT CLIENT DEFINITIONs
                        .define(
                                        MQTT_CLIENT_FILE_PERSISTENCE_DIRECTORY,
                                        Type.STRING,
                                        "/tmp/mqtt-file-persistance",
                                        Importance.LOW,
                                        "The directory in which the mqtt client stores pending messages to fullfill its quality of service (QoS) between failures and restarts. (Default: /tmp/mqtt-file-persistance)")
                        // KAFKA CONFIG DEFINITIONS
                        .define(
                                        KAFKA_TOPIC,
                                        Type.STRING,
                                        "upstream",
                                        Importance.MEDIUM,
                                        "Kafka topic to publish to.");

        public MqttSourceConnectorConfig(Map<String, String> props) {
                super(DEFINITION, props);
        }
}

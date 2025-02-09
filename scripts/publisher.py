import os
import time
import paho.mqtt.client as mqtt

print(f"Starting mqtt publisher")

# Konfiguration aus Umgebungsvariablen laden
MQTT_BROKER = os.getenv("MQTT_BROKER", "localhost")
MQTT_PORT = int(os.getenv("MQTT_PORT", 1883))
MQTT_TOPIC = os.getenv("MQTT_TOPIC", "sensor/data")
MQTT_USERNAME = os.getenv("MQTT_USER", None)
MQTT_PASSWORD = os.getenv("MQTT_PASSWORD", None)

print(f"MQTT_BROKER = {MQTT_BROKER}")
print(f"MQTT_PORT = {MQTT_PORT}")
print(f"MQTT_TOPIC = {MQTT_TOPIC}")
print(f"MQTT_USERNAME = {MQTT_USERNAME}")
print(f"MQTT_PASSWORD = {MQTT_PASSWORD}")

# MQTT-Client konfigurieren
client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)

# Falls Username und Passwort gesetzt sind, verwende sie für die Authentifizierung
if MQTT_USERNAME and MQTT_PASSWORD:
    client.username_pw_set(MQTT_USERNAME, MQTT_PASSWORD)

client.connect(MQTT_BROKER, MQTT_PORT)

print(f"Connected to MQTT-Broker {MQTT_BROKER}:{MQTT_PORT}, Topic: {MQTT_TOPIC}")

client.loop_start()

message = f"Hello MQTT at {time.time()}"

while True:
    unacked = client.publish(MQTT_TOPIC, message, qos=1)
    print(f"Send {MQTT_TOPIC} {message}")
    unacked.wait_for_publish()
    print(f"Received ack for {MQTT_TOPIC} {message}")

    time.sleep(5)

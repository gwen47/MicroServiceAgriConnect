import paho.mqtt.client as mqtt
import time
import json
import random

# Configuration du broker MQTT
BROKER = "localhost"
PORT = 1883
TOPIC_DATA = "sensor/data"
TOPIC_INTERVAL = "sensor/interval"
CLIENT_ID = f"sensor001"

# Variable globale pour l'intervalle de mesure
measurement_interval = 5  # Valeur initiale par défaut

# Fonction appelée lors de la connexion au broker
def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Connected to MQTT Broker!")
        # S'abonner au sujet pour les mises à jour d'intervalle
        client.subscribe(TOPIC_INTERVAL)
    else:
        print("Failed to connect, return code %d\n", rc)

# Fonction appelée lors de la publication d'un message
def on_publish(client, userdata, mid):
    print(f"Message {mid} published.")

# Fonction appelée lors de la réception d'un message
def on_message(client, userdata, msg):
    global measurement_interval
    try:
        payload = json.loads(msg.payload.decode())
        if "sensorCode" in payload and "interval" in payload:
            if payload["sensorCode"] == CLIENT_ID:
                measurement_interval = payload["interval"]
                print(f"Updated measurement interval to {measurement_interval} seconds")
    except Exception as e:
        print(f"Error processing message: {e}")

# Création du client MQTT
client = mqtt.Client(CLIENT_ID)
client.on_connect = on_connect
client.on_publish = on_publish
client.on_message = on_message

# Connexion au broker MQTT
client.connect(BROKER, PORT, 60)
client.loop_start()

# Simulation des données du capteur
def simulate_sensor_data():
    temperature = random.uniform(10.0, 30.0)
    humidity = random.uniform(30.0, 90.0)
    timestamp = time.strftime('%Y-%m-%d %H:%M:%S')
    return {
        "temperature": temperature,
        "humidity": humidity,
        "timestamp": timestamp,
        "sensorCode": CLIENT_ID
    }

try:
    while True:
        # Génération des données du capteur
        sensor_data = simulate_sensor_data()
        payload = json.dumps(sensor_data)
        
        # Publication des données sur le topic MQTT
        result = client.publish(TOPIC_DATA, payload)
        
        # Attente de la publication
        status = result[0]
        if status == 0:
            print(f"Sent `{payload}` to topic `{TOPIC_DATA}`")
        else:
            print(f"Failed to send message to topic {TOPIC_DATA}")
        
        # Attendre avant d'envoyer la prochaine donnée
        time.sleep(measurement_interval)
except KeyboardInterrupt:
    print("Simulation stopped.")
finally:
    client.loop_stop()
    client.disconnect()


---

# Apache Kafka & Zookeeper with Docker

This guide explains how to run **Apache Kafka** and **Zookeeper** using Docker, interact with Kafka using its CLI tools, and understand basic concepts like **topics**, **producers**, **consumers**, and **consumer lag**.

---

## 📦 Step 1: Run Zookeeper Container

Kafka requires Zookeeper to manage its brokers. First, we run a Zookeeper container:

```bash
docker run -d \
  --name zookeeper \
  -p 2181:2181 \
  -e ZOOKEEPER_CLIENT_PORT=2181 \
  -e ZOOKEEPER_TICK_TIME=2000 \
  confluentinc/cp-zookeeper:7.5.0
```

### 🔹 Description:

* `--name zookeeper`: Names the container.
* `-p 2181:2181`: Exposes the Zookeeper default port.
* `ZOOKEEPER_CLIENT_PORT`: Port for client connections.
* `ZOOKEEPER_TICK_TIME`: Basic time unit for Zookeeper.

---

## 🚀 Step 2: Run Kafka Container

After Zookeeper is running, start the Kafka broker:

```bash
docker run -d \
  --name kafka \
  -p 9092:9092 \
  -e KAFKA_BROKER_ID=1 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  --link zookeeper \
  confluentinc/cp-kafka:7.5.0
```

### 🔹 Description:

* `--name kafka`: Names the Kafka container.
* `-p 9092:9092`: Exposes Kafka's default port.
* `KAFKA_ZOOKEEPER_CONNECT`: Connects Kafka to Zookeeper.
* `KAFKA_ADVERTISED_LISTENERS`: How Kafka is advertised to clients.
* `KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR`: Used for internal topics like offsets.

---

## 🖥️ Step 3: Access Kafka Container Shell

To use Kafka CLI tools inside the container:

```bash
docker exec -it kafka bash
```

---

## 🧵 Step 4: Create a Topic

Once inside the Kafka container:

```bash
kafka-topics --create \
  --topic my-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```

> In real-world applications, topic names should reflect the domain (e.g., `order-events`, `user-signups`).

---

## 📋 Step 5: List All Topics

```bash
kafka-topics --list --bootstrap-server localhost:9092
```

---

## 🔍 Step 6: Describe a Topic

To view details about a topic:

```bash
kafka-topics --describe \
  --topic my-topic \
  --bootstrap-server localhost:9092
```

---

## 📝 Step 7: Start a Kafka Producer

To send messages to a topic in real-time (live mode):

```bash
kafka-console-producer \
  --topic my-topic \
  --bootstrap-server localhost:9092
```

Type messages directly into the terminal to publish them.

---

## 📥 Step 8: Start a Kafka Consumer

To receive messages from the beginning:

```bash
kafka-console-consumer \
  --topic my-topic \
  --bootstrap-server localhost:9092 \
  --from-beginning
```

---

## 🗝️ Step 9: Producer with Key-Value Messages

Use this command if your producer messages include keys:

```bash
kafka-console-producer \
  --topic my-topic \
  --bootstrap-server localhost:9092 \
  --property "parse.key=true" \
  --property "key.separator=:"
```

Example message format:

```
user1:Hello from user1
```

---

## ⏳ Step 10: Check Consumer Lag

### 📌 What is Lag?

**Lag** means messages have been **produced** but not yet **consumed**. High lag may indicate that the consumer is slow or not keeping up.

```bash
kafka-consumer-groups \
  --bootstrap-server localhost:9092 \
  --describe \
  --group my-group
```

> Replace `my-group` with the name of your consumer group.

---

## ✅ Summary

| Action            | Command Preview                                        |
| ----------------- | ------------------------------------------------------ |
| Start Zookeeper   | `docker run ... cp-zookeeper`                          |
| Start Kafka       | `docker run ... cp-kafka`                              |
| Enter Kafka shell | `docker exec -it kafka bash`                           |
| Create topic      | `kafka-topics --create ...`                            |
| List topics       | `kafka-topics --list`                                  |
| Describe topic    | `kafka-topics --describe ...`                          |
| Start producer    | `kafka-console-producer ...`                           |
| Start consumer    | `kafka-console-consumer ... --from-beginning`          |
| Producer with key | `kafka-console-producer ... --property parse.key=true` |
| Check lag         | `kafka-consumer-groups --describe --group my-group`    |

---

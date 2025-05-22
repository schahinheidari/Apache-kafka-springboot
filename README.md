
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


---

# 📘 Java Functional Interfaces & `@FunctionalInterface`

## 🔹 What is `@FunctionalInterface`?

A `@FunctionalInterface` is an interface in Java that contains **exactly one abstract method**. It is used primarily with **lambda expressions** and **method references** to pass behavior as arguments.

> ✅ While the annotation is optional, it's a good practice to include it — the compiler will ensure the interface remains functional (i.e., only one abstract method).

---

## 🔹 Common Built-in Functional Interfaces

Java provides a set of functional interfaces in the `java.util.function` package. Here are four of the most commonly used ones:

---

### 1. `Function<T, R>`

Represents a function that accepts one argument and returns a result.

* **Abstract method:** `R apply(T t)`

``` java
Function<String, Integer> lengthFunction = str -> str.length();
System.out.println(lengthFunction.apply("hello")); // Output: 5
```

---

### 2. `Consumer<T>`

Represents an operation that accepts a single input and returns no result.

* **Abstract method:** `void accept(T t)`

``` java
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Welcome to Java!"); // Output: Welcome to Java!
```

---

### 3. `Supplier<T>`

Represents a supplier of results with no input arguments.

* **Abstract method:** `T get()`

``` java
Supplier<Double> randomSupplier = () -> Math.random();
System.out.println(randomSupplier.get()); // Output: a random number
```

---

### 4. `Predicate<T>`

Represents a boolean-valued condition on a single input.

* **Abstract method:** `boolean test(T t)`

``` java
Predicate<Integer> isEven = num -> num % 2 == 0;
System.out.println(isEven.test(4)); // Output: true
```

---

## 🔹 Summary Table

| Interface        | Input Type | Output Type | Description                    |
| ---------------- | ---------- | ----------- | ------------------------------ |
| `Function<T, R>` | `T`        | `R`         | Transforms input into a result |
| `Consumer<T>`    | `T`        | `void`      | Performs action without result |
| `Supplier<T>`    | none       | `T`         | Supplies a value               |
| `Predicate<T>`   | `T`        | `boolean`   | Tests a condition on input     |

---

## 🧠 Why Use Functional Interfaces?

* Enables clean and expressive **lambda expressions**
* Encourages **functional programming** in Java
* Makes code more readable and concise
* Reduces boilerplate in operations like filtering, mapping, and iteration

---

---

# ☁️ Spring Cloud Function

## 🔹 What is Spring Cloud Function?

**Spring Cloud Function** is a project from the Spring ecosystem that promotes writing **business logic as functions**. These functions can be **deployed independently** of the underlying platform (e.g., AWS Lambda, Azure Functions, HTTP server).

> 🧩 It supports `Function<T, R>`, `Consumer<T>`, and `Supplier<T>` interfaces — the same functional interfaces provided by Java.

---

## 🔹 Key Features

* **Platform agnostic**: Write once, run anywhere (HTTP, messaging, serverless).
* **Function composition**: Combine multiple functions.
* **Routing**: Route to specific functions dynamically.
* **Spring Boot integration**: Use familiar configuration and dependency injection.
* **Support for reactive programming** via Project Reactor.

---

## 🔹 Functional Interfaces in Spring Cloud Function

Spring Cloud Function uses the same core Java interfaces:

| Interface        | Use Case                           |
| ---------------- | ---------------------------------- |
| `Function<T, R>` | Process input and return a result  |
| `Consumer<T>`    | Accept input and perform an action |
| `Supplier<T>`    | Provide values on demand           |

---

## 🔹 Example: Creating a Simple Function

### ✅ Step 1: Add Dependency (Maven)

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-function-web</artifactId>
</dependency>
```

---

### ✅ Step 2: Define the Function Bean

```java
@SpringBootApplication
public class FunctionApp {

    public static void main(String[] args) {
        SpringApplication.run(FunctionApp.class, args);
    }

    @Bean
    public Function<String, String> uppercase() {
        return value -> value.toUpperCase();
    }
}
```

---

### ✅ Step 3: Call the Function via HTTP

```bash
curl localhost:8080/uppercase -H "Content-Type: text/plain" -d "hello"
# Output: HELLO
```

---

## 🔹 Using `Consumer` and `Supplier`

### Consumer Example

```java
@Bean
public Consumer<String> logger() {
    return msg -> System.out.println("Received: " + msg);
}
```

### Supplier Example

```java
@Bean
public Supplier<String> greetings() {
    return () -> "Hello from Supplier!";
}
```

---

## 🔹 Function Composition

You can **chain multiple functions** using the `|` symbol.

```bash
curl localhost:8080/greetings|uppercase
```

This will call `greetings()` first and then pass its output to `uppercase()`.

---

## 🔹 Deployment Targets

Spring Cloud Function apps can be deployed to:

* Local HTTP server (Spring Boot)
* AWS Lambda
* Microsoft Azure
* Apache Kafka
* RabbitMQ
* Knative / Kubernetes

---

## 🔹 Summary

Spring Cloud Function empowers developers to:

* Write **clean, reusable**, and **testable** functions.
* Decouple business logic from infrastructure.
* Deploy to **cloud or on-prem** platforms effortlessly.

> It brings the power of **functional programming** into the **Spring ecosystem** with minimal boilerplate and maximum flexibility.

---

---

# 🔁 Spring Cloud Stream

## 🔹 What is Spring Cloud Stream?

**Spring Cloud Stream** is a framework for building **event-driven microservices** connected to shared messaging systems like **Apache Kafka**, **RabbitMQ**, and others.

It abstracts the low-level details of message brokers and allows developers to write **business logic** using Spring Boot and **bind it to messaging platforms** with simple configuration.

---

## 🎯 Key Features

* Simplifies integration with messaging middleware
* Supports **Kafka**, **RabbitMQ**, **AWS Kinesis**, etc.
* Allows **binding input/output** channels via `@Bean` functions
* Compatible with **Function**, **Consumer**, and **Supplier**
* Supports **reactive** and **non-blocking** message processing

---

## 🔧 Step 1: Add Maven Dependencies

Add the following to your `pom.xml` if using Kafka:

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-stream-kafka</artifactId>
</dependency>
```

---

## 🧩 Step 2: Create a Functional Bean

You can create a function that consumes messages:

```java
@SpringBootApplication
public class StreamApp {

    public static void main(String[] args) {
        SpringApplication.run(StreamApp.class, args);
    }

    @Bean
    public Consumer<String> processMessage() {
        return message -> System.out.println("Received: " + message);
    }
}
```

---

## 📝 Step 3: Configure `application.yml`

```yaml
spring:
  cloud:
    stream:
      bindings:
        processMessage-in-0:
          destination: my-topic
      kafka:
        binder:
          brokers: localhost:9092
```

### 🔹 Explanation:

* `processMessage-in-0`: Refers to the input binding of the `Consumer` named `processMessage`
* `destination`: Kafka topic name
* `brokers`: Address of Kafka broker

---

## ✉️ Optional: Sending Messages with Supplier

If you want to **produce messages**, you can use a `Supplier`:

```java
@Bean
public Supplier<String> messageProducer() {
    return () -> "Hello from Supplier!";
}
```

And configure:

```yaml
spring:
  cloud:
    stream:
      bindings:
        messageProducer-out-0:
          destination: my-topic
```

---

## 🔗 Binding Types & Naming Convention

| Functional Type  | Binding Name Format              | Direction |
| ---------------- | -------------------------------- | --------- |
| `Supplier<T>`    | `<function-name>-out-0`          | Output    |
| `Consumer<T>`    | `<function-name>-in-0`           | Input     |
| `Function<T, R>` | `<function-name>-in-0`, `-out-0` | In & Out  |

---

## 🚀 Run the Application

To run the application, simply execute:

```bash
./mvnw spring-boot:run
```

Make sure your **Kafka** and **Zookeeper** are running on `localhost:9092` and `localhost:2181`.

---

## 📌 Summary

| Feature                   | Benefit                                                 |
| ------------------------- | ------------------------------------------------------- |
| Abstracted messaging APIs | Write logic without dealing with Kafka APIs             |
| Functional programming    | Uses `Supplier`, `Function`, `Consumer` as entry points |
| Easy configuration        | Bind channels via `application.yml`                     |
| Broker support            | Kafka, RabbitMQ, AWS Kinesis, and more                  |
| Platform flexibility      | Works with microservices, cloud functions, containers   |

---

## 📘 Real-World Use Cases

* Event-driven microservices
* Asynchronous order processing
* Audit and logging pipelines
* Stream processing and real-time notifications

---
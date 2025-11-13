# **SimilarProducts API**

This is a backend application developed in **Spring Boot**, designed to obtain
similar products by specifying a reference product. The application uses mocked
data that serves as its database and provides a documented REST API using
Swagger.

Additionally, it implements behavioral tests (Mocks) using Cucumber.

## **Previous requisites**

Before you begin, make sure you have the following components installed on your
machine:

- **Java 21**
- **Maven 3.8+**

## **Instructions for building and running the application**

### **1. Project construction**

From the project root (similarProducts), run the following command in your
terminal:

```bash
mvn clean package
```

### **2. Start the application**

```bash
java -jar ./target/similarproducts-1.0.0.jar
```

or

```bash
mvn spring-boot:run
```

Remember that should be launched the external API service (simulado) to make the
application work.

```bash
docker-compose up -d simulado influxdb grafana  
```

### **3. Behavioral tests**

```bash
mvn verify
```

## **Enabled resources**

### **1. Swagger API documentation**

#### URL: http://localhost:8080/swagger-ui/index.html


# **E-commerce API**

Esta es una aplicación backend desarrollada en **Spring Boot**, diseñada para obtener
los productos similares. La aplicación utiliza unos datos mockeados que se utilizan como
base de datos y proporciona una API REST documentada con Swagger. 

Además, implementa pruebas de comportamiento (Mocks) mediante Cucumber.

## **Requisitos previos**

Antes de comenzar, asegúrate de tener los siguientes componentes instalados en tu máquina:

- **Java 21**
- **Maven 3.8+**

## **Instrucciones para construir y ejecutar la aplicación**

### **1. Construcción del proyecto**

Desde la raíz del proyecto, ejecuta el siguiente comando en tu terminal:

```bash
mvn clean package
```

### **2. Iniciar la aplicación**
```bash
java -jar ./target/similarproducts-1.0.0.jar
```

ó

```bash
mvn spring-boot:run
```

### **3. Pruebas de comportamiento**

```bash
mvn verify
```

## **Recursos habilitados**

### **1. Documentacion swagger de la API**

#### URL: http://localhost:8080/swagger-ui/index.html


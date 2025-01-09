# Proyecto de Gestión de Clientes - API RESTful

[![Build Status](https://img.shields.io/github/workflow/status/MiguelFOlivar/GestorClientes/CI)](https://github.com/MiguelFOlivar/GestorClientes/actions)
[![Test Coverage](https://img.shields.io/codecov/c/github/MiguelFOlivar/GestorClientes)](https://codecov.io/gh/MiguelFOlivar/GestorClientes)
![Static Badge](https://img.shields.io/badge/Java-%2017%2B-green?style=flat&logo=CoffeeScript&label=Java)
![Static Badge](https://img.shields.io/badge/Spring-%203.0%2B-brightgreen?style=flat&logo=Spring%20Boot)
![Oracle](https://img.shields.io/badge/Oracle-%2012c%2B-red?style=flat)
![JUnit](https://img.shields.io/badge/JUnit-5-orange?style=flat&logo=JUnit5)
![Maven](https://img.shields.io/badge/Maven-%203.8%2B-blue?style=flat&logo=Apache%20Maven)

Este es un proyecto de **Gestión de Clientes** que expone una API RESTful para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los clientes. La API está construida utilizando **Spring Boot**, **Swagger/OpenAPI** para la documentación y **SLF4J** para la gestión de logs.

## Descripción
El proyecto proporciona un conjunto de funcionalidades para gestionar los registros de clientes. Las principales características incluyen:

- **Operaciones CRUD**: Crear, leer, actualizar y eliminar clientes.
- **Documentación automática**: Utiliza Swagger/OpenAPI para generar y visualizar la documentación interactiva de la API.
- **Soporte para operaciones en batch**: Crear múltiples clientes en una sola solicitud.
- **Consulta avanzada**: Buscar clientes por nombre o correo electrónico.

La API está diseñada para ser utilizada en sistemas de gestión de clientes donde se necesita almacenar y consultar información como nombre, correo electrónico, y realizar actualizaciones en los registros.

## Tecnologías Utilizadas

| Tecnología        | Descripción                                    |
|-------------------|------------------------------------------------|
| **Spring Boot**    | Framework para crear aplicaciones Java basadas en microservicios. |
| **Java 17+**       | Lenguaje de programación para desarrollo backend. |
| **Oracle Database**| Base de datos relacional para almacenamiento de clientes. |
| **JUnit**          | Framework de pruebas unitarias para Java. |
| **Mockito**        | Biblioteca para pruebas unitarias (mocking). |
| **Swagger**        | Para la documentación interactiva de la API. |

---

## Base de Datos Oracle

Este proyecto utiliza una base de datos de Oracle para almacenar los datos de los clientes y los productos.
A continuación se listan los detalles de conexion y configuración.


  ## Endpoints de la API

### **Clientes Controller**

Este controlador gestiona las operaciones sobre los clientes.

#### **POST** `/api/clientes/create`

Crea un nuevo cliente.

##### Request Body:

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@mail.com"
}
```

## Configuración de Oracle

1. **Dependencias**: En el archivo `pom.xml`, incluye las dependencias necesarias para Oracle. Asegúrate de tener el driver adecuado en el tu proyecto

```xml
<dependency>
  <groupId>com.oracle.database.jdbc</groupId>
  <artifactId>ojdbc11</artifactId>
  <scope>runtime</scope>
</dependency>
```
2. **Configuración de la conexión**: configura el archivo `application.prperties` o `application.yml` para conectar la aplicación a la base de datos Oracle.
Asegúrate de tener la URL, usuario y contraseña correctos.

```properties
# URL de conexión a la base de datos Oracle
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/orcl
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# Configuración del driver Oracle
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.jpa.properties.hibernate.jdbc.time_zone=UTC

# create and drop tables
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true

#Swagger configuration
springdoc.swagger-ui.enabled=true
springdoc.api-docs.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html

server.port=8080 (puedes cambiar el puerto si lo requieres)

```







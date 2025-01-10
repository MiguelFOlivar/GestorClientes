# Proyecto de Gestión de Clientes - API RESTful

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


#### **GET** `/api/clientes/findall`

Consulta los clientes almacenados.

##### Request Body:

**Respuesta 200 OK en formato JSON**

```json
[
  { "nombre": "Juan Pérez", "email": "juan@mail.com" },
  { "nombre": "John Doe", "email": "jdoe@mail.com" }
]

```


#### **GET** `/api/clientes/getByEmail`

Consulta un cliente por su email.

##### Parámetros:
- `email` (Requerido) : El correo por el que se consultará el cliente. Debe ser un String
  
**Respuesta 200 OK en formato JSON**

```json
  { "nombre": "Juan Pérez", "email": "juan@mail.com" },
```

#### **POST** `/api/clientes/create`

Crea un nuevo cliente.

##### Request Body:

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@mail.com"
}
```

#### **POST** `/api/clientes/batch`

Crea una lista de clientes.

##### Request Body:

```json
[
    {
        "nombre": "Johny Bravo",
        "email": "Bravo@mail.com"
    },
    {
        "nombre": "Marge Simpson",
        "email": "marge@simpson.com"
    },
    {
        "nombre": "Homer Simpson",
        "email": "homer@simpson.com"
    },
    {
        "nombre": "Bart Simpson",
        "email": "bart@simpson.com"
    },
    {
        "nombre": "Lisa Simpson",
        "email": "lisa@simpson.com"
    }
]

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

## Swagger - Documentación Interactiva

La API está documentada automáticamente utilizando Swagger. 
Puedes acceder a la documentación interactiva y probar los endpoints directamente desde tu navegador.

**Swagger UI:**
```
http://localhost:8080/swagger-ui.html
```

## Instalación y Configuración
**Requisitos:**

- Java 17+
- Maven 3.8+
- IDE (Ejemplo: IntelliJ IDEA, Eclipse)
- Oracle Database (Instalado y configurado para usar en desarrollo)

## Pasos para ejecutar el proyecto

1. **Clona el repositorio**
   ```
   git clone https://github.com/tu-usuario/tu-repositorio.git
   
   ```
2. **Navega al directorio del proyecto**
   ```
   cd tu-repositorio

   ```
3. **Construye el proyecto usando Maven**
   ```
   mvn clean install
   
   ```
4. **Configura tu base de datos de Oracle:** Asegurate de que la base de datos esté configurada correctamente.
5. **Ejecuta el proyecto**
   ```
   mvn spring-boot:run

   ```
6. **La aplicación estará disponible en**
   ```
   http://localhost:8080
   
   ```

## Contribuciones

### **Si deseas contribuir al proyecto, sigue estos pasos:**
1. **Realiza un fork del repositorio**
2. **Crea una nueva rama( `git checkout -b feature-nueva-funcionalidad`).
3. Haz tus cambios y añade pruebas si es necesario
4. Realiza un commit (`git commit -am 'Añadir nueva funcionalidad'`).
5. Haz push a tu rama (`git push feature-nueva-funcionalidad`).
6. Abre un Pull Request.


   

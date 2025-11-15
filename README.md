# OrderSpring 🛍️

Sistema de gestión de pedidos desarrollado con Spring Boot que permite administrar clientes, productos y órdenes de compra.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Testing](#testing)
- [API Endpoints](#api-endpoints)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [CI/CD](#cicd)
- [Contribuir](#contribuir)

## ✨ Características

- **Gestión de Clientes**: CRUD completo para administrar información de clientes
- **Gestión de Productos**: Control de inventario con estados activo/inactivo
- **Gestión de Órdenes**: Sistema completo de pedidos con seguimiento de estados
- **API RESTful**: Endpoints bien documentados con Swagger/OpenAPI
- **Paginación**: Soporte para paginación en todas las consultas de listado
- **Manejo de Excepciones**: Sistema centralizado de manejo de errores
- **Tests Unitarios**: Cobertura de tests para controladores y servicios
- **CI/CD**: Integración continua con GitHub Actions

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.7**
    - Spring Data JPA
    - Spring Web
    - Spring Boot DevTools
- **PostgreSQL** (Producción)
- **H2 Database** (Tests)
- **Lombok**: Reducción de código boilerplate
- **Maven**: Gestión de dependencias
- **SpringDoc OpenAPI**: Documentación API
- **JUnit 5 & Mockito**: Testing

## 📦 Requisitos Previos

- Java 21 o superior
- Maven 3.6+ (o usar el wrapper incluido `./mvnw`)
- PostgreSQL 12+ (para entorno de producción)
- Git

## 🚀 Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/orderspring.git
cd orderspring
```

2. **Dar permisos al wrapper de Maven (Linux/Mac)**
```bash
chmod +x mvnw
```

## ⚙️ Configuración

### Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

```properties
DB_URL=jdbc:postgresql://localhost:5432/orderspring_db
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

### Base de Datos

1. **Crear la base de datos en PostgreSQL**
```sql
CREATE DATABASE orderspring_db;
CREATE SCHEMA RIAL;
```

2. **Configurar credenciales**

Las credenciales se cargan desde las variables de entorno definidas en el archivo `.env`.

## 🏃 Ejecución

### Modo Desarrollo

```bash
./mvnw spring-boot:run
```

### Compilar el proyecto

```bash
./mvnw clean install
```

### Generar JAR ejecutable

```bash
./mvnw clean package
java -jar target/orderspring-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:8080`

## 🧪 Testing

### Ejecutar todos los tests

```bash
./mvnw test
```

### Ejecutar tests con cobertura

```bash
./mvnw test jacoco:report
```

### Configuración de Tests

Los tests utilizan H2 en memoria. La configuración se encuentra en:
- `src/test/resources/application-test.properties`

## 📚 API Endpoints

La documentación completa de la API está disponible en Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

### Clientes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/clients/create` | Crear cliente |
| GET | `/api/clients` | Listar clientes (paginado) |
| GET | `/api/clients/get/id/{id}` | Obtener por ID |
| GET | `/api/clients/get/name/{name}` | Obtener por nombre |
| GET | `/api/clients/get/phone/{phone}` | Obtener por teléfono |
| GET | `/api/clients/get/email/{email}` | Obtener por email |
| PUT | `/api/clients/update/{id}` | Actualizar cliente |
| DELETE | `/api/clients/delete/{id}` | Eliminar cliente |

### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/products/create` | Crear producto |
| GET | `/api/products` | Listar productos (paginado) |
| GET | `/api/products/get/id/{id}` | Obtener por ID |
| GET | `/api/products/get/name/{name}` | Obtener por nombre |
| GET | `/api/products/get/state/{state}` | Obtener por estado |
| PUT | `/api/products/update/{id}` | Actualizar producto |
| DELETE | `/api/products/delete/{id}` | Eliminar producto |

### Órdenes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/orders/create` | Crear orden |
| GET | `/api/orders` | Listar órdenes (paginado) |
| GET | `/api/orders/get/id/{id}` | Obtener por ID |
| GET | `/api/orders/get/orderDate/{date}` | Obtener por fecha de orden |
| GET | `/api/orders/get/deliveryDate/{date}` | Obtener por fecha de entrega |
| GET | `/api/orders/get/orderState/{state}` | Obtener por estado |
| GET | `/api/orders/get/client/{clientId}` | Obtener por cliente |

### Estados Disponibles

**ProductState:**
- `ACTIVE`
- `INACTIVE`

**OrderState:**
- `ORDERED`
- `IN_PROGRESS`
- `DELIVERED`

**PaymentState:**
- `PAID`
- `UNPAID`

## 📁 Estructura del Proyecto

```
orderspring/
├── src/
│   ├── main/
│   │   ├── java/com/rial/orderspring/
│   │   │   ├── constants/         # Constantes de la aplicación
│   │   │   ├── controller/        # Controladores REST
│   │   │   ├── enums/             # Enumeraciones
│   │   │   ├── exception/         # Manejo de excepciones
│   │   │   ├── model/             # Entidades JPA
│   │   │   ├── repository/        # Repositorios Spring Data
│   │   │   ├── service/           # Interfaces de servicios
│   │   │   │   └── impl/          # Implementaciones de servicios
│   │   │   └── OrderspringApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/rial/orderspring/
│       │   ├── controller/        # Tests de controladores
│       │   └── service/           # Tests de servicios
│       └── resources/
│           └── application-test.properties
├── .github/
│   └── workflows/
│       ├── maven.yml              # CI Pipeline
│       └── maven-publish.yml      # Publish Pipeline
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🔄 CI/CD

El proyecto incluye dos workflows de GitHub Actions:

### 1. CI Pipeline (`maven.yml`)
- Se ejecuta en push/PR a `main` y `develop`
- Compila el proyecto
- Ejecuta los tests
- Genera reportes de tests

### 2. Publish Pipeline (`maven-publish.yml`)
- Se ejecuta en releases o manualmente
- Compila y empaqueta la aplicación
- Publica en GitHub Packages

### Configurar GitHub Packages

En `pom.xml`, actualiza la URL del repositorio:

```xml
<distributionManagement>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/TU_USUARIO/TU_REPOSITORIO</url>
    </repository>
</distributionManagement>
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Estándares de Código

- Seguir las convenciones de Java
- Escribir tests para nuevas funcionalidades
- Documentar endpoints en Swagger
- Mantener cobertura de tests > 80%

## 📝 Notas Adicionales

### Hot Reload

El proyecto incluye Spring Boot DevTools para hot reload durante el desarrollo. Los cambios en el código se reflejarán automáticamente sin necesidad de reiniciar.

### Profiles

- `default`: Usa PostgreSQL (producción)
- `test`: Usa H2 en memoria (tests)

### Swagger UI

Accede a la documentación interactiva de la API:
```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia que determines.

## 👥 Autores

- **Sergio Ricart** - *Desarrollo* - [Tu GitHub](https://github.com/sergioricart)
---

⭐ **Si te gusta este proyecto, dale una estrella en GitHub!** ⭐
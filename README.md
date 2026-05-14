# Cliente API - Java Spring Boot

Esta es una API RESTful profesional diseñada para la gestión eficiente de clientes. 
El proyecto no solo se enfoca en las operaciones CRUD, sino que implementa patrones de diseño avanzados para garantizar seguridad, 
escalabilidad y un código limpio (**Clean Code**).

## Stack Tecnológico
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL
* **Documentación:** Swagger / OpenAPI 3
* **Gestor de Dependencias:** Maven

## Arquitectura y Patrones de Diseño
El proyecto sigue una arquitectura por capas para separar responsabilidades:

1. **Controller:** Capa de exposición de endpoints REST.
2. **Service:** Capa de lógica de negocio y validaciones.
3. **Repository:** Abstracción de acceso a datos.
4. **DTO (Data Transfer Object):** Se implementó este patrón para desacoplar las entidades de la base de datos de la capa de presentación, evitando vulnerabilidades de exposición de datos.

##  Seguridad y Robustez
* **Validaciones de Entrada:** Uso de `@Valid` para asegurar la integridad de los datos.
* **Manejo Global de Excepciones:** Centralizado con `@ControllerAdvice` para retornar respuestas estandarizadas y evitar fugas de información técnica.
* **Idempotencia:** Diseñado para que las operaciones de actualización y eliminación sean seguras ante reintentos de red.

---

## 📖 Documentación de la API
Una vez que el proyecto esté corriendo, puedes acceder a la interfaz interactiva de Swagger en:
 `http://localhost:8080/swagger-ui/index.html`

### Endpoints Principales:
| Método | Endpoint | Acción |
| :--- | :--- | :--- |
| `GET` | `/api/v1/tasks` | Lista todas las tareas |
| `POST` | `/api/v1/tasks` | Crea una nueva tarea |
| `GET` | `/api/v1/tasks/{id}` | Obtiene detalle de una tarea |
| `PUT` | `/api/v1/tasks/{id}` | Actualiza una tarea existente (Idempotente) |
| `DELETE` | `/api/v1/tasks/{id}` | Elimina una tarea |

---

## Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/task-management-api.git](https://github.com/tu-usuario/task-management-api.git)
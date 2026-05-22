# Cliente API - Java Spring Boot

Esta es una API RESTful profesional diseñada para la gestión eficiente de clientes. 
El proyecto no solo se enfoca en las operaciones CRUD, sino que implementa patrones de diseño avanzados y mecanismos de seguridad de nivel empresarial para garantizar la protección de datos, la escalabilidad y un código limpio (**Clean Code**).

## Stack Tecnológico
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **Seguridad:** Spring Security 6 & JSON Web Tokens (JWT)
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL
* **Documentación:** Swagger / OpenAPI 3
* **Gestor de Dependencias:** Maven

## Arquitectura y Patrones de Diseño
El proyecto sigue una arquitectura por capas para separar responsabilidades de manera clara:

1. **Controller:** Capa de exposición de endpoints REST protegidos.
2. **Security & Filters:** Interceptores personalizados (`OncePerRequestFilter`) para la validación stateless de tokens en cada petición HTTP.
3. **Service:** Capa de lógica de negocio, validaciones y procesamiento de JWT.
4. **Repository:** Abstracción de acceso a datos de forma segura.
5. **DTO (Data Transfer Object):** Implementación para desacoplar las entidades de la base de datos de la capa de presentación, evitando la exposición directa del modelo de datos.

## 🔒 Seguridad y Robustez
* **Autenticación Stateless con JWT:** Implementación de tokens firmados digitalmente mediante el algoritmo HMAC-SHA256 con claves codificadas en Base64. El servidor no mantiene sesión en memoria, optimizando el rendimiento para arquitecturas de microservicios.
* **Filtros Personalizados:** Intercepción de cabeceras HTTP mediante un esquema `Bearer Token` inyectado directamente en el `SecurityContextHolder` de Spring.
* **Validaciones de Entrada:** Uso de `@Valid` para asegurar la integridad de los datos en el payload.
* **Manejo Global de Excepciones:** Centralizado con `@ControllerAdvice` para retornar respuestas estandarizadas y evitar fugas de información técnica ante errores o tokens expirados.
* **Idempotencia:** Diseñado para que las operaciones de actualización y eliminación sean seguras ante reintentos de red.

---

## 📖 Documentación de la API
La documentación de la API está completamente integrada y protegida. Puedes acceder a la interfaz interactiva de Swagger de forma pública en:
`http://localhost:8080/swagger-ui/index.html`

> 💡 **Nota de Uso en Swagger:** Para probar los endpoints protegidos desde la interfaz de Swagger, primero debes generar un token válido en el endpoint de login, hacer clic en el botón verde **"Authorize"** en la parte superior derecha de la pantalla y pegar el token con formato JWT.

### Endpoints Principales:

#### 🔐 Autenticación (Público)
| Método | Endpoint | Acción |
| :--- | :--- | :--- |
| `POST` | `/api/v1/auth/login` | Recibe credenciales y genera el token JWT (Bearer) |

#### 👥 Gestión de Clientes (Protegidos por JWT)
| Método | Endpoint | Acción |
| :--- | :--- | :--- |
| `GET` | `/api/v1/clientes` | Lista todos los clientes registrados |
| `POST` | `/api/v1/clientes` | Crea un nuevo cliente |
| `GET` | `/api/v1/clientes/{id}` | Obtiene el detalle de un cliente específico |
| `PUT` | `/api/v1/clientes/{id}` | Actualiza un cliente existente (Idempotente) |
| `DELETE` | `/api/v1/clientes/{id}` | Elimina un cliente del sistema |

---

## Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/cliente-api-rest.git](https://github.com/tu-usuario/cliente-api-rest.git)
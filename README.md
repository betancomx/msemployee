============================================================
Raven Employee API

Solución de backend desarrollada en Spring Boot para la gestión de empleados, empaquetada y orquestada con Docker para su ejecución inmediata.

============================================================
Requisitos

Docker y Docker Compose instalados.

Puertos 8080 y 3306 disponibles en la máquina local.

============================================================
Instrucciones de Ejecución

Descargar o clonar el repositorio.

Abrir una terminal en la carpeta raíz del proyecto.

Ejecutar el comando:
docker-compose up --build -d

Validar que los contenedores "db-employee" y "ms-employee-app" estén corriendo.

============================================================
Tecnologías Utilizadas

Java 17 / Spring Boot 3.5.

MySQL 8.0 como motor de base de datos.

SpringDoc OpenAPI 2.8.6 para documentación.

Docker y Docker Compose para contenedores.

============================================================
Consideraciones de Lógica

Borrado Lógico: Los empleados no se eliminan físicamente de la base de datos; se deshabilitan mediante la columna 'is_active'.

Consistencia: El sistema bloquea automáticamente consultas, actualizaciones y búsquedas sobre registros que han sido borrados lógicamente (is_active = 0).

============================================================
Documentación y Pruebas

Para interactuar con la API y revisar los endpoints, acceder a:
http://localhost:8080/swagger-ui/index.html

Las evidencias adicionales de ejecución (capturas de pantalla) se encuentran en el archivo PDF adjunto en la entrega.
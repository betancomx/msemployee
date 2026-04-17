package com.jbe.msemployee.commons;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("Clase de constantes, no se puede instanciar");
    }

    // Formatos de Fecha
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    // Mensajes de Validación (DTOs)
    public static final String MSG_VALID_FIRST_NAME = "El primer nombre no puede ir vacio";
    public static final String MSG_VALID_LAST_NAME = "El apellido paterno es obligatorio";
    public static final String MSG_VALID_SECOND_LAST_NAME = "El apellido materno es obligatorio";
    public static final String MSG_VALID_GENDER = "El sexo es obligatorio";
    public static final String MSG_VALID_BIRTH_DATE = "La fecha de nacimiento es obligatoria";
    public static final String MSG_VALID_BIRTH_DATE_PAST = "La fecha de nacimiento no puede ser mayor al día actual";
    public static final String MSG_VALID_PUESTO = "El empleado debe tener un puesto";

    // Mensajes de Excepción
    public static final String MSG_EMP_NOT_FOUND = "Empleado no encontrado con ID: %s";

    // Controller para el swagger
    public static final String SWAGGER_TAG_NAME = "Gestión de Empleados";
    public static final String SWAGGER_TAG_DESC = "Endpoints para el CRUD de empleados de Raven";
    public static final String SWAGGER_OP_CREATE = "Crear empleado(s)";
    public static final String SWAGGER_OP_CREATE_DESC = "Registra uno o varios empleados en el sistema en una sola petición";
    public static final String SWAGGER_OP_GET_ALL = "Listar todos";
    public static final String SWAGGER_OP_GET_ALL_DESC = "Obtiene la lista completa de empleados registrados";
    public static final String SWAGGER_OP_GET_ID = "Buscar por ID";
    public static final String SWAGGER_OP_SEARCH = "Búsqueda parcial por nombre";
    public static final String SWAGGER_OP_PUT = "Actualización total (PUT)";
    public static final String SWAGGER_OP_PUT_DESC = "Reemplaza todos los campos del empleado";
    public static final String SWAGGER_OP_PATCH = "Actualización parcial (PATCH)";
    public static final String SWAGGER_OP_PATCH_DESC = "Actualiza solo los campos enviados en el cuerpo";
    public static final String SWAGGER_OP_DELETE = "Borrado lógico";
    public static final String SWAGGER_OP_DELETE_DESC = "Desactiva al empleado sin eliminarlo de la base de datos";

    // Mensajes para el GlobalExceptionHandler
    public static final String RESPONSE_ERROR_KEY = "error";
    public static final String MSG_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado en el servidor";
    public static final String MSG_INVALID_ENUM_VALUE = "El valor proporcionado no existe en nuestro catálogo válido.";
    public static final String MSG_INVALID_JSON_FORMAT = "El formato del cuerpo de la petición (JSON) es inválido.";
}
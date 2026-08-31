###  Sistema de evaluación de Créditos de Aventa Bank  ###

Servicio backend desarrollado con Spring Boot y frontend en Angular para recibir y evaluar solicitudes de crédito de consumo mediante un conjunto de validaciones de negocio 
y una integración con un servicio externo de buró de crédito. Con persistencia de las solicitudes de clientes, permitiendo ver el historial y creación de solicitudes.


## 1. Descripción ##

El sistema permite:

Frontend:
Generar solicitud de crédito y ver su resultado
Consultar el historial de solicitudes con información de las validaciones: validación de identidad, Score, capacidad de pago y reporte negativo.

Backend:
Recibir solicitudes de crédito.
Validar la identidad del solicitante.
Consultar información del buró de crédito.
Validar score crediticio.
Validar reportes negativos.
Validar capacidad de pago.
Detectar documentos bloqueados.
Determinar el resultado de la solicitud: APROBADO, PREAPROBADO, RECHAZADO, RECHAZADO_FRAUDE, PENDIENTE_REVISION
Persistir las solicitudes y sus resultados.
Consultar las solicitudes asociadas a un tipo y número de documento.



## 2. Ejecución

### Backend

cd backend
./mvnw.cmd spring-boot:run

Backend: http://localhost:8080

### Tests

cd backend
./mvnw.cmd test

### Backend mock-buro

cd mock-buro
./mvnw.cmd spring-boot:run

Backend: http://localhost:8081

### Frontend

cd frontend
npm install
npm start

Frontend: http://localhost:4200




## 3. Reglas de decisión ##

Las principales reglas utilizadas son:

#Aprobado
score >= 700
y
montoSolicitado <= ingresosMensuales * 8

Resultado:
APROBADO

#Preaprobado
score >= 600
y
montoSolicitado <= ingresosMensuales * 5

Resultado:
PREAPROBADO

#Rechazado
Una solicitud puede ser rechazada cuando:

El score es menor a 600.
Existe un reporte negativo.
No cumple las condiciones de capacidad de pago.

Resultado:
RECHAZADO

#Rechazado por fraude

Si el documento se encuentra bloqueado:
RECHAZADO_FRAUDE

Esta condición tiene prioridad sobre las demás validaciones.

#Pendiente de revisión
Si el servicio externo de buró no está disponible o supera el timeout configurado:
PENDIENTE_REVISION


## 4. Integración con el buró ##

La integración se realiza mediante el puerto:
BureauClient

y su implementación:
BureauHttpClient

La aplicación utiliza WebClient para realizar la comunicación HTTP.

El cliente externo tiene configurado un timeout de:
4 segundos

Cuando ocurre un error de comunicación o timeout, se encapsula en:
BureauUnavailableException

De esta manera, la lógica de negocio no depende directamente de WebClient.

## 5. Persistencia ##

Las solicitudes evaluadas se almacenan en una base de datos H2.

La entidad utilizada es:
SolicitudesEntity

y el acceso a datos se realiza mediante:
SolicitudesRepository

Entre los datos almacenados se encuentran:

idSolicitud
fechaCreacion
tipoDocumento
numeroDocumento
nombresApellidos
correoElectronico
telefonoCelular
montoSolicitado
plazoMeses
estado
tasaEstimada
scoreBureau
validaciones
siguientePaso

Las validaciones se almacenan como información JSON.


## 6. Manejo de errores ##

El sistema contempla principalmente:

Error de integración con buró
BureauUnavailableException
        │
        ▼
PENDIENTE_REVISION

La solicitud no se considera rechazada simplemente porque el servicio externo no esté disponible.

Timeout

La consulta al buró tiene un límite de 4 segundos.

Si se supera:

Timeout
   │
   ▼
BureauUnavailableException
   │
   ▼
PENDIENTE_REVISION


## 7. Pruebas ##

El proyecto incluye pruebas unitarias para los componentes principales.

Se cubren especialmente:
DecisionEngine
BureauHttpClient
Comportamiento de las validaciones.
Manejo de BureauUnavailableException.
Escenarios de aprobación y rechazo.

Las pruebas pueden ejecutarse mediante Maven:
mvnw.cmd test


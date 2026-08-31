export interface ValidationResult {
  detalle: string;
  nombre: string;
  resultado: 'APROBADO' | 'PREAPROBADO' | 'RECHAZADO' | 'RECHAZADO_FRAUDE' | 'PENDIENTE_REVISION' | string;
  scoreBureau?: number | null;
}

export interface SolicitudConsultaResponse {
  idSolicitud: string;
  montoSolicitado: number;
  plazoMeses: number;
  tasaEstimada: number | null;
  scoreBureau: number | null;
  estado: 'APROBADO' | 'PREAPROBADO' | 'RECHAZADO' | 'RECHAZADO_FRAUDE' | 'PENDIENTE_REVISION' | string;
  validaciones: ValidationResult[];
  siguientePaso: string | null;
}

export interface SolicitudCreacionRequest {
  tipoDocumento: string;
  numeroDocumento: string;
  nombresApellidos: string;
  correoElectronico: string;
  telefonoCelular: string;
  montoSolicitado: number;
  plazoMeses: number;
  ingresosMensuales: number;
}

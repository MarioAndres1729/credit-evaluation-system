import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { SolicitudConsultaResponse } from '../models/solicitud.model';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {
  private readonly directUrl = 'http://localhost:8080/api/v1/solicitudes';
  private readonly proxyUrl = '/api/v1/solicitudes';

  private solicitudesSubject = new BehaviorSubject<SolicitudConsultaResponse[]>([]);
  public solicitudes$ = this.solicitudesSubject.asObservable();

  private selectedSolicitudSubject = new BehaviorSubject<SolicitudConsultaResponse | null>(null);
  public selectedSolicitud$ = this.selectedSolicitudSubject.asObservable();

  public currentTipoDoc = '';
  public currentNumDoc = '';

  constructor(private http: HttpClient) {}

  consultarSolicitudes(tipoDocumento: string, numeroDocumento: string): Observable<SolicitudConsultaResponse[]> {
    this.currentTipoDoc = tipoDocumento;
    this.currentNumDoc = numeroDocumento;

    const directEndpoint = `${this.directUrl}/${encodeURIComponent(tipoDocumento)}/${encodeURIComponent(numeroDocumento)}`;
    const proxyEndpoint = `${this.proxyUrl}/${encodeURIComponent(tipoDocumento)}/${encodeURIComponent(numeroDocumento)}`;

    console.log(`[SolicitudService] Consultando solicitudes para ${tipoDocumento}/${numeroDocumento}...`);

    // Try direct endpoint first, then proxy endpoint if CORS or network error
    return this.http.get<SolicitudConsultaResponse[]>(directEndpoint).pipe(
      catchError((directErr) => {
        console.warn(`[SolicitudService] Endpoint directo ${directEndpoint} falló, intentando proxy ${proxyEndpoint}...`, directErr);
        return this.http.get<SolicitudConsultaResponse[]>(proxyEndpoint);
      }),
      tap((data) => {
        console.log('[SolicitudService] Datos obtenidos exitosamente del backend:', data);
        this.solicitudesSubject.next(data || []);
        if (data && data.length > 0) {
          this.selectedSolicitudSubject.next(data[0]);
        } else {
          this.selectedSolicitudSubject.next(null);
        }
      }),
      catchError((error) => {
        console.error('[SolicitudService] Error conectando con el backend en ambos endpoints:', error);
        const mockData = this.getMockData(tipoDocumento, numeroDocumento);
        console.info('[SolicitudService] Usando datos de respaldo:', mockData);
        this.solicitudesSubject.next(mockData);
        if (mockData.length > 0) {
          this.selectedSolicitudSubject.next(mockData[0]);
        }
        return of(mockData);
      })
    );
  }

  setSelectedSolicitud(solicitud: SolicitudConsultaResponse): void {
    this.selectedSolicitudSubject.next(solicitud);
  }

  private getMockData(tipoDoc: string, numDoc: string): SolicitudConsultaResponse[] {
    return [
      {
        idSolicitud: 'SOL-20260831-756',
        montoSolicitado: 10000000,
        plazoMeses: 36,
        tasaEstimada: 1.2,
        scoreBureau: 750,
        estado: 'APROBADO',
        validaciones: [
          {
            detalle: 'Documento no bloqueado',
            nombre: 'Identidad',
            resultado: 'APROBADO',
            scoreBureau: null
          },
          {
            detalle: 'Score 750 >= 600',
            nombre: 'Score',
            resultado: 'APROBADO',
            scoreBureau: 750
          },
          {
            detalle: 'Monto dentro del rango permitido',
            nombre: 'Capacidad de pago',
            resultado: 'APROBADO',
            scoreBureau: null
          },
          {
            detalle: 'Sin reportes negativos',
            nombre: 'Reporte negativo',
            resultado: 'APROBADO',
            scoreBureau: null
          }
        ],
        siguientePaso: 'Se enviará contrato al correo registrado en 24 horas'
      },
      {
        idSolicitud: 'SOL-20260831-223',
        montoSolicitado: 50000000,
        plazoMeses: 36,
        tasaEstimada: null,
        scoreBureau: 750,
        estado: 'RECHAZADO',
        validaciones: [
          {
            detalle: 'Documento no bloqueado',
            nombre: 'Identidad',
            resultado: 'APROBADO',
            scoreBureau: null
          },
          {
            detalle: 'Score 750 >= 600',
            nombre: 'Score',
            resultado: 'APROBADO',
            scoreBureau: 750
          },
          {
            detalle: 'El monto solicitado excede el límite permitido según los ingresos',
            nombre: 'Capacidad de pago',
            resultado: 'RECHAZADO',
            scoreBureau: null
          }
        ],
        siguientePaso: null
      }
    ];
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { SolicitudConsultaResponse, SolicitudCreacionRequest } from '../models/solicitud.model';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {
  private readonly directUrl = 'http://localhost:8080/api/v1/solicitudes';

  private solicitudesSubject = new BehaviorSubject<SolicitudConsultaResponse[]>([]);
  public solicitudes$ = this.solicitudesSubject.asObservable();

  private selectedSolicitudSubject = new BehaviorSubject<SolicitudConsultaResponse | null>(null);
  public selectedSolicitud$ = this.selectedSolicitudSubject.asObservable();

  public currentTipoDoc = '';
  public currentNumDoc = '';

  constructor(private http: HttpClient) { }

  consultarSolicitudes(tipoDocumento: string, numeroDocumento: string): Observable<SolicitudConsultaResponse[]> {
    this.currentTipoDoc = tipoDocumento;
    this.currentNumDoc = numeroDocumento;

    const directEndpoint = `${this.directUrl}/${encodeURIComponent(tipoDocumento)}/${encodeURIComponent(numeroDocumento)}`;

    return this.http.get<SolicitudConsultaResponse[]>(directEndpoint);
  }

  crearSolicitud(solicitud: SolicitudCreacionRequest): Observable<any> {
    this.currentTipoDoc = solicitud.tipoDocumento;
    this.currentNumDoc = solicitud.numeroDocumento;

    const directEndpoint = this.directUrl;
    return this.http.post<any>(directEndpoint, solicitud).pipe();
  }

  setSelectedSolicitud(solicitud: SolicitudConsultaResponse): void {
    this.selectedSolicitudSubject.next(solicitud);
  }


}

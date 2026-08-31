import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { SolicitudService } from '../../services/solicitud.service';
import { SolicitudConsultaResponse, ValidationResult } from '../../models/solicitud.model';

@Component({
  selector: 'app-resultado-solicitudes',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './resultado-solicitudes.html',
  styleUrl: './resultado-solicitudes.css'
})
export class ResultadoSolicitudesComponent implements OnInit, OnDestroy {
  solicitudes: SolicitudConsultaResponse[] = [];
  selectedSolicitud: SolicitudConsultaResponse | null = null;
  isLoading = true;
  tipoDocumento = '';
  numeroDocumento = '';

  private subs = new Subscription();

  constructor(
    private solicitudService: SolicitudService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.subs.add(
      this.route.queryParams.subscribe((params) => {
        const tipoDoc = params['tipoDoc'] || this.solicitudService.currentTipoDoc;
        const numDoc = params['numDoc'] || this.solicitudService.currentNumDoc;
        const selectedId = params['selectedId'];

        this.tipoDocumento = tipoDoc || '';
        this.numeroDocumento = numDoc || '';

        if (tipoDoc && numDoc) {
          this.consultar(tipoDoc, numDoc, selectedId);
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }

  consultar(tipoDoc: string, numDoc: string, selectedId?: string): void {
    this.isLoading = true;
    this.cdr.detectChanges();

    this.solicitudService.consultarSolicitudes(tipoDoc, numDoc).subscribe({
      next: (data) => {
        this.solicitudes = data || [];
        if (this.solicitudes.length > 0) {
          if (selectedId) {
            const found = this.solicitudes.find(s => s.idSolicitud === selectedId);
            this.selectedSolicitud = found || this.getLastSolicitud();
          } else {
            this.selectedSolicitud = this.getLastSolicitud();
          }
        } else {
          this.selectedSolicitud = null;
        }
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('[ResultadoSolicitudes] Error al consultar:', err);
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  getLastSolicitud(): SolicitudConsultaResponse | null {
    if (!this.solicitudes || this.solicitudes.length === 0) return null;
    if (this.solicitudes.length === 1) return this.solicitudes[0];

    // Ordena en orden descendente por secuencia de ID para encontrar la última solicitud.
    const copy = [...this.solicitudes];
    copy.sort((a, b) => {
      const idA = a.idSolicitud || '';
      const idB = b.idSolicitud || '';
      return idB.localeCompare(idA, undefined, { numeric: true, sensitivity: 'base' });
    });
    return copy[0];
  }

  isLastSolicitud(solicitud: SolicitudConsultaResponse | null): boolean {
    if (!solicitud) return false;
    const last = this.getLastSolicitud();
    return last?.idSolicitud === solicitud.idSolicitud;
  }

  onSelectSolicitud(solicitud: SolicitudConsultaResponse): void {
    this.selectedSolicitud = solicitud;
    this.solicitudService.setSelectedSolicitud(solicitud);
    this.cdr.detectChanges();
  }

  onNuevaSolicitud(): void {
    this.router.navigate(['/solicitud-credito']);
  }

  onVolverConsulta(): void {
    this.router.navigate(['/consulta-solicitudes']);
  }

  getStatusClass(estado: string): string {
    const norm = (estado || '').toUpperCase();
    if (norm.includes('PREAPROB')) return 'status-preaprobado';
    if (norm.includes('APROB')) return 'status-aprobado';
    if (norm.includes('RECHAZ')) return 'status-rechazado';
    if (norm.includes('ESTUDIO') || norm.includes('PENDIENTE') || norm.includes('REVISION')) return 'status-pendiente';
    return 'status-default';
  }

  getStatusLabel(estado: string): string {
    return (estado || 'PENDIENTE').toUpperCase();
  }

  getValidationStatusClass(resultado: string): string {
    const norm = (resultado || '').toUpperCase();
    if (norm.includes('APROB') || norm.includes('OK') || norm === 'PASSED') return 'val-aprobado';
    if (norm.includes('RECHAZ') || norm === 'FAILED') return 'val-rechazado';
    return 'val-pendiente';
  }

  getValidationIcon(resultado: string): string {
    const norm = (resultado || '').toUpperCase();
    if (norm.includes('APROB') || norm.includes('OK')) return '✓';
    if (norm.includes('RECHAZ')) return '✗';
    return '⏱';
  }
}

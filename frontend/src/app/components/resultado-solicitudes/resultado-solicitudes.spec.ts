import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, ActivatedRoute } from '@angular/router';
import { of, BehaviorSubject } from 'rxjs';
import { ResultadoSolicitudesComponent } from './resultado-solicitudes';
import { SolicitudService } from '../../services/solicitud.service';
import { SolicitudConsultaResponse } from '../../models/solicitud.model';

describe('ResultadoSolicitudesComponent', () => {
  let component: ResultadoSolicitudesComponent;
  let fixture: ComponentFixture<ResultadoSolicitudesComponent>;

  const mockSolicitudes: SolicitudConsultaResponse[] = [
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
      validaciones: [],
      siguientePaso: null
    }
  ];

  let mockSolicitudService: any;

  beforeEach(async () => {
    mockSolicitudService = {
      solicitudes$: new BehaviorSubject<SolicitudConsultaResponse[]>(mockSolicitudes),
      selectedSolicitud$: new BehaviorSubject<SolicitudConsultaResponse | null>(mockSolicitudes[0]),
      currentTipoDoc: 'PA',
      currentNumDoc: '12345678930',
      consultarSolicitudes: vi.fn().mockReturnValue(of(mockSolicitudes)),
      setSelectedSolicitud: vi.fn()
    };

    await TestBed.configureTestingModule({
      imports: [ResultadoSolicitudesComponent],
      providers: [
        provideRouter([]),
        { provide: SolicitudService, useValue: mockSolicitudService },
        {
          provide: ActivatedRoute,
          useValue: {
            queryParams: of({ tipoDoc: 'PA', numDoc: '12345678930' })
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ResultadoSolicitudesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load initial default or mock solicitudes', () => {
    expect(component.solicitudes.length).toBe(2);
    expect(component.selectedSolicitud).not.toBeNull();
    expect(component.selectedSolicitud?.idSolicitud).toBe('SOL-20260831-756');
  });

  it('should correctly determine status classes', () => {
    expect(component.getStatusClass('APROBADO')).toBe('status-aprobado');
    expect(component.getStatusClass('RECHAZADO')).toBe('status-rechazado');
    expect(component.getStatusClass('EN_ESTUDIO')).toBe('status-pendiente');
  });

  it('should update selectedSolicitud when onSelectSolicitud is called', () => {
    const secondItem = mockSolicitudes[1];
    component.onSelectSolicitud(secondItem);
    expect(component.selectedSolicitud?.idSolicitud).toBe('SOL-20260831-223');
    expect(mockSolicitudService.setSelectedSolicitud).toHaveBeenCalledWith(secondItem);
  });
});

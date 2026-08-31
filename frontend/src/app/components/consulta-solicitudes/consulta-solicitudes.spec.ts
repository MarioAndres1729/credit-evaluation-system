import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { ConsultaSolicitudesComponent } from './consulta-solicitudes';

describe('ConsultaSolicitudesComponent', () => {
  let component: ConsultaSolicitudesComponent;
  let fixture: ComponentFixture<ConsultaSolicitudesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultaSolicitudesComponent],
      providers: [provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(ConsultaSolicitudesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with empty values and invalid status', () => {
    expect(component.consultaForm).toBeDefined();
    expect(component.consultaForm.valid).toBe(false);
  });

  it('should validate valid document number pattern', () => {
    component.consultaForm.patchValue({
      documentType: 'CC',
      documentNumber: '1012345678'
    });
    expect(component.consultaForm.valid).toBe(true);
  });

  it('should invalidate incorrect document number', () => {
    component.consultaForm.patchValue({
      documentType: 'CC',
      documentNumber: 'abc123'
    });
    expect(component.consultaForm.valid).toBe(false);
  });
});

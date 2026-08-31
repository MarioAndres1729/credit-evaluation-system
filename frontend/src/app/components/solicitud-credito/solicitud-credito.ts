import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { SolicitudService } from '../../services/solicitud.service';
import { SolicitudCreacionRequest } from '../../models/solicitud.model';

@Component({
  selector: 'app-solicitud-credito',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './solicitud-credito.html',
  styleUrl: './solicitud-credito.css'
})
export class SolicitudCreditoComponent implements OnInit {

  creditForm!: FormGroup;
  isSubmitted = false;
  isSubmitting = false;
  errorMessage = '';

  documentTypes = [
    { code: 'CC', label: 'Cédula de Ciudadanía' },
    { code: 'CE', label: 'Cédula de Extranjería' },
    { code: 'PA', label: 'Pasaporte' }
  ];

  termOptions = [12, 24, 36, 48];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private solicitudService: SolicitudService
  ) { }

  ngOnInit(): void {
    this.creditForm = this.fb.group({
      documentType: ['', [Validators.required]],
      documentNumber: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{6,12}$')
      ]],
      fullName: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')
      ]],
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      phone: ['', [
        Validators.required,
        Validators.pattern('^3[0-9]{9}$')
      ]],
      requestedAmount: [null, [
        Validators.required,
        Validators.min(1000000),
        Validators.max(50000000)
      ]],
      termMonths: ['', [Validators.required]],
      monthlyIncome: [null, [
        Validators.required,
        Validators.min(1)
      ]]
    });
  }

  get f() {
    return this.creditForm.controls;
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.creditForm.get(fieldName);
    return !!(field && field.invalid && (field.touched || this.isSubmitted));
  }

  onSubmit(): void {
    this.isSubmitted = true;
    this.errorMessage = '';

    if (this.creditForm.invalid) {
      this.creditForm.markAllAsTouched();
      return;
    }

    const formVal = this.creditForm.value;
    const request: SolicitudCreacionRequest = {
      tipoDocumento: formVal.documentType,
      numeroDocumento: formVal.documentNumber,
      nombresApellidos: formVal.fullName,
      correoElectronico: formVal.email,
      telefonoCelular: formVal.phone,
      montoSolicitado: Number(formVal.requestedAmount),
      plazoMeses: Number(formVal.termMonths),
      ingresosMensuales: Number(formVal.monthlyIncome)
    };

    this.isSubmitting = true;
    this.solicitudService.crearSolicitud(request).subscribe({
      next: (response) => {
        this.isSubmitting = false;
        const createdId = response?.idSolicitud || response?.id;
        this.router.navigate(['/resultado-solicitudes'], {
          queryParams: {
            tipoDoc: request.tipoDocumento,
            numDoc: request.numeroDocumento,
            ...(createdId ? { selectedId: createdId } : {})
          }
        });
      },
      error: (error) => {
        this.isSubmitting = false;
        console.error('[SolicitudCredito] Error al enviar solicitud:', error);
        this.router.navigate(['/resultado-solicitudes'], {
          queryParams: {
            tipoDoc: request.tipoDocumento,
            numDoc: request.numeroDocumento
          }
        });
      }
    });
  }

  onReset(): void {
    this.isSubmitted = false;
    this.isSubmitting = false;
    this.errorMessage = '';
    this.creditForm.reset();
  }

  onVolver(): void {
    this.router.navigate(['/consulta-solicitudes']);
  }
}
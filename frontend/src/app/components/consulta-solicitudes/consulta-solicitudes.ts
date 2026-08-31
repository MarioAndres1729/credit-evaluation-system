import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-consulta-solicitudes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './consulta-solicitudes.html',
  styleUrl: './consulta-solicitudes.css'
})
export class ConsultaSolicitudesComponent implements OnInit {
  consultaForm!: FormGroup;
  isSubmitted = false;
  hasSearched = false;
  isSearching = false;

  documentTypes = [
    { code: 'CC', label: 'Cédula de Ciudadanía' },
    { code: 'CE', label: 'Cédula de Extranjería' },
    { code: 'PA', label: 'Pasaporte' }
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.consultaForm = this.fb.group({
      documentType: ['', [Validators.required]],
      documentNumber: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{6,12}$')
      ]]
    });
  }

  get f() {
    return this.consultaForm.controls;
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.consultaForm.get(fieldName);
    return !!(field && field.invalid && (field.touched || this.isSubmitted));
  }

  onConsultar(): void {
    this.isSubmitted = true;
    if (this.consultaForm.invalid) {
      this.consultaForm.markAllAsTouched();
      return;
    }

    this.isSearching = true;
    this.hasSearched = false;

    // Simulate search action (ready to be hooked to API)
    setTimeout(() => {
      this.isSearching = false;
      this.hasSearched = true;
      console.log('Consultando solicitudes para:', this.consultaForm.value);
    }, 600);
  }

  onNuevaSolicitud(): void {
    this.router.navigate(['/solicitud-credito']);
  }
}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-solicitud-credito',
  standalone: true, // Required so app.ts can import it
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './solicitud-credito.html',
  styleUrl: './solicitud-credito.css'
})
export class SolicitudCreditoComponent implements OnInit {

  creditForm!: FormGroup;
  isSubmitted = false;

  documentTypes = [
    { code: 'CC', label: 'Cédula de Ciudadanía' },
    { code: 'CE', label: 'Cédula de Extranjería' },
    { code: 'PA', label: 'Pasaporte' }
  ];

  termOptions = [12, 24, 36, 48];

  constructor(private fb: FormBuilder) {}

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
    if (this.creditForm.invalid) {
      this.creditForm.markAllAsTouched();
      return;
    }
    console.log('Form submission:', this.creditForm.value);
  }

  onReset(): void {
    this.isSubmitted = false;
    this.creditForm.reset();
  }
}
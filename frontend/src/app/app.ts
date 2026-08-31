import { Component } from '@angular/core';
import { SolicitudCreditoComponent } from './components/solicitud-credito/solicitud-credito';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [SolicitudCreditoComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'Aventa Bank';
}
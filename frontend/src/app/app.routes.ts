import { Routes } from '@angular/router';
import { ConsultaSolicitudesComponent } from './components/consulta-solicitudes/consulta-solicitudes';
import { SolicitudCreditoComponent } from './components/solicitud-credito/solicitud-credito';

export const routes: Routes = [
  { path: '', redirectTo: 'consulta-solicitudes', pathMatch: 'full' },
  { path: 'consulta-solicitudes', component: ConsultaSolicitudesComponent },
  { path: 'solicitud-credito', component: SolicitudCreditoComponent },
  { path: '**', redirectTo: 'consulta-solicitudes' }
];

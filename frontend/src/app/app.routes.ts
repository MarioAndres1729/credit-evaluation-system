import { Routes } from '@angular/router';
import { ConsultaSolicitudesComponent } from './components/consulta-solicitudes/consulta-solicitudes';
import { SolicitudCreditoComponent } from './components/solicitud-credito/solicitud-credito';
import { ResultadoSolicitudesComponent } from './components/resultado-solicitudes/resultado-solicitudes';

export const routes: Routes = [
  { path: '', redirectTo: 'consulta-solicitudes', pathMatch: 'full' },
  { path: 'consulta-solicitudes', component: ConsultaSolicitudesComponent },
  { path: 'solicitud-credito', component: SolicitudCreditoComponent },
  { path: 'resultado-solicitudes', component: ResultadoSolicitudesComponent },
  { path: '**', redirectTo: 'consulta-solicitudes' }
];


import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config'; // <-- Importe o appConfig

bootstrapApplication(AppComponent, appConfig) // <-- Use o appConfig completo aqui
  .catch((err) => console.error(err));
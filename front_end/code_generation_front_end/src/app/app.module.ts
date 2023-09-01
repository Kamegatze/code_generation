import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthUsersComponent } from './components/auth-users/auth-users.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { RegistrationComponent } from './components/registration/registration.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { AuthInterceptor } from './services/_helpers/auth-intreceptor';


@NgModule({
  declarations: [
    AppComponent,
    AuthUsersComponent,
    RegistrationComponent,
    FooterComponent,
    HeaderComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        ReactiveFormsModule,
        HttpClientModule,
        FormsModule,
    ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor , multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SwitchPasswordRoutingModule } from './switch-password-routing.module';
import { SwitchPasswordComponent } from './switch-password.component';
import { InputLoginComponent } from './input-login/input-login.component';
import { InputCodeComponent } from './input-code/input-code.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    SwitchPasswordComponent,
    InputLoginComponent,
    InputCodeComponent,
    ChangePasswordComponent
  ],
  imports: [
    CommonModule,
    SwitchPasswordRoutingModule,
    ReactiveFormsModule
  ]
})
export class SwitchPasswordModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SwitchPasswordComponent} from "./switch-password.component";
import {InputLoginComponent} from "./input-login/input-login.component";
import {InputCodeComponent} from "./input-code/input-code.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";

const routes: Routes = [
  {path: '', component:SwitchPasswordComponent,
    children: [
      {path: "input-login", component:InputLoginComponent},
      {path:"", redirectTo:"input-login", pathMatch:"full"},
      {path:"input-code", component:InputCodeComponent},
      {path:"change-password", component:ChangePasswordComponent},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SwitchPasswordRoutingModule { }

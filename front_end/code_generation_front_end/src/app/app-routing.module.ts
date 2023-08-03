import {NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthUsersComponent} from "./components/auth-users/auth-users.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {SwitchPasswordComponent} from "./components/switch-password/switch-password.component";

const routes: Routes = [
  {path:"auth", component: AuthUsersComponent},

  {path: "", redirectTo:"main", pathMatch:'full'},
  {path:"sign_up", component:RegistrationComponent},
  {path:"switch-password", component:SwitchPasswordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule{


}

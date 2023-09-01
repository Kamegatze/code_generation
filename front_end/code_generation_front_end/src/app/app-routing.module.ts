import {NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthUsersComponent} from "./components/auth-users/auth-users.component";
import {RegistrationComponent} from "./components/registration/registration.component";

const routes: Routes = [
  {path:"auth", component: AuthUsersComponent},

  {path: "", redirectTo:"main", pathMatch:'full'},
  {path:"sign_up", component:RegistrationComponent},
  {
    path:"switch-password",
    loadChildren: () => import('./components/switch-password/switch-password.module')
      .then((module) => module.SwitchPasswordModule)
  },
  {
    path:"project", loadChildren: () => import("./components/projects/project.module")
      .then((module) => module.ProjectModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule{


}

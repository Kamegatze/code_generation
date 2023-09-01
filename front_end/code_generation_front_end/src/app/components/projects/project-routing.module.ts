import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectComponent } from './project.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { AdditionFieldsComponent } from './addition-fields/addition-fields.component';

const routes: Routes = [
  {path: "", component: ProjectComponent, 
  children: [
    {path:"project-dashboard", component:DashboardComponent},
    {path: "", redirectTo:"project-dashboard", pathMatch:"full"},
    {path:":projectId", component:ProjectDetailsComponent},
    {path:':projectId/fields/:entityId', component:AdditionFieldsComponent},
  ]
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectRoutingModule { }

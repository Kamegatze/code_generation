import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectRoutingModule } from './project-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProjectComponent } from './project.component';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdditionFieldsComponent } from './addition-fields/addition-fields.component';


@NgModule({
  declarations: [
    DashboardComponent,
    ProjectComponent,
    ProjectDetailsComponent,
    AdditionFieldsComponent
  ],
  imports: [
    CommonModule,
    ProjectRoutingModule,
    ReactiveFormsModule
  ]
})
export class ProjectModule { }

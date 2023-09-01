import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from 'src/app/model/project';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{
  protected projects$!:Observable<Project[]>;
  
  ngOnInit(): void {
    this.projects$ = this.projectService.getProject();
  }

  constructor(
    private projectService: ProjectService
  ) {}
}

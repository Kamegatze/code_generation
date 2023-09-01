import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigurationServer } from './configuration-server';
import { Observable } from 'rxjs';
import { Project } from '../model/project';
import { AuthService } from './auth.service';
import { ResponseServerAfterLogin } from '../model/response-server-after-login';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(
    private http: HttpClient,
    private config: ConfigurationServer,
    private auth: AuthService
  ) { }

  public getProject() : Observable<Project[]> {
    
    const response: ResponseServerAfterLogin = this.auth.getHoldUserData();

    return this.http.get<Project[]>(
      `${this.config.getProjectUrl()}/get_project/`);
  }

}

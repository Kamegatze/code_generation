import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseAfterRegistration} from "../model/response-after-registration";
import {ConfigurationServer} from "./configuration-server";
import {Registration} from "../model/registration";


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(
      private http: HttpClient,
      private configServer: ConfigurationServer
  ) { }

  public sign_up(registration:Registration):Observable<ResponseAfterRegistration> {
    return this.http.post<ResponseAfterRegistration>(`${this.configServer.getAuthServiceUrl()}/registration`, registration);
  }
}

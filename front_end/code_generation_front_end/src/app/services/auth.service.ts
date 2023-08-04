import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {ResponseServerAfterLogin} from "../model/response-server-after-login";
import {Login} from "../model/login-interface";
import {ConfigurationServer} from "./configuration-server";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private router: Router,
    private http: HttpClient,
    private configServer: ConfigurationServer
  ) { }

  public sign_in(login:Login) : Observable<ResponseServerAfterLogin> {
    return this.http.post<ResponseServerAfterLogin>
    (`${this.configServer.authServiceUrl}/sign_in`, login)
        .pipe(
          tap(() => this.router.navigate(['']))
        );
  }

  public setHoldUserData(authInfo:ResponseServerAfterLogin) : void {
    localStorage.setItem('authInfo', JSON.stringify(authInfo));
  }

  public getHoldUserData() : ResponseServerAfterLogin {
    // @ts-ignore
    return JSON.parse(localStorage.getItem('authInfo'));
  }

  public isAuthorization() : boolean {
    return this.getHoldUserData() != null;
  }

  public logout() {
    localStorage.removeItem('authInfo');
    this.router.navigate(['auth']);
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormGroup} from "@angular/forms";
import {SwitchPasswordResponse} from "../model/switch-password-response";
import {catchError, Observable, tap} from "rxjs";
import {ConfigurationServer} from "./configuration-server";
import {Router} from "@angular/router";
import {ChangePassword} from "../model/change-password.model";


@Injectable({
  providedIn: 'root'
})
export class SwitchPasswordService {

  constructor(
    private http:HttpClient,
    private configService: ConfigurationServer,
    private router:Router
  ) { }

  public sendLogin(switch_password:FormGroup) : Observable<SwitchPasswordResponse> {

    return this.http.post<SwitchPasswordResponse>
    (`${this.configService.getAuthServiceUrl()}/switch_password`, switch_password.value)
      .pipe(
        tap(() => {
          this.router.navigate(['switch-password/input-code'])
        })
      );

  }

  public setCode(code:string) {
    localStorage.setItem("code", code);
  }

  public getCode() : string {
    // @ts-ignore
    return localStorage.getItem("code");
  }

  public removeCode() {
    localStorage.removeItem("code");
  }

  public checkCode(code: string):Observable<SwitchPasswordResponse> {
    return this.http.get<SwitchPasswordResponse>(`${this.configService.getAuthServiceUrl()}/check_code?code=${code}`)
      .pipe(
        tap(() => this.router.navigate(['switch-password/change-password']))
      );
  }

  public changePassword(changePassword:FormGroup<ChangePassword>) {
    return this.http.post(`${this.configService.getAuthServiceUrl()}/change_password`, changePassword.value)
      .pipe(
        tap(() => {
          this.removeCode();
          this.router.navigate(["auth"])
        }),
        catchError((err) => {

          const message = err.error.message;

          for (let i = 0; i < message.length; i++) {
            console.error(message[i]?.defaultMessage)
          }

          if(message[0].defaultMessage.indexOf('null') !== -1 || message[0].defaultMessage.indexOf('empty') !== -1) {
            alert("Сейчас вы перейдете на страницу /switch-password/input-login для смена пароля следуйте дальнейшей инструкции");
            this.router.navigate(['switch-password'])
          }

          return [];
        })
      ).subscribe()
  }

}

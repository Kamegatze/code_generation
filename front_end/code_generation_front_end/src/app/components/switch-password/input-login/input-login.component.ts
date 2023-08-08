import {Component, OnDestroy, OnInit} from '@angular/core';
import {SwitchPasswordResponse} from "../../../model/switch-password-response";
import {SwitchPasswordService} from "../../../services/switch-password.service";
import {catchError, Subscription, tap} from "rxjs";
import {SwitchPassword} from "../../../model/switch-password";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-login',
  templateUrl: './input-login.component.html',
  styleUrls: ['./input-login.component.scss']
})
export class InputLoginComponent implements OnInit, OnDestroy{

  protected switch_password_form!:FormGroup

  protected switch_password_response!:SwitchPasswordResponse;

  private removeRequest!:Subscription;

  constructor(
      private switchPasswordService:SwitchPasswordService
  ) {
  }

  ngOnInit(): void {
    this.switch_password_form = new FormGroup<SwitchPassword>({
      login: new FormControl("", [
        Validators.required,
        Validators.minLength(2)
      ])
    });

    this.switch_password_response = {
      exist:true,
      message:""
    };
  }

  ngOnDestroy(): void {
    this.removeRequest?.unsubscribe();
  }

  public submit() {
    this.removeRequest = this.switchPasswordService.sendLogin(this.switch_password_form)
        .pipe(
            catchError((err) => {

              this.switch_password_response = err.error;
              console.error(err.error.message)
              return [];
            })
        )
        .subscribe(value => {
          this.switch_password_response = value
        })
  }

}

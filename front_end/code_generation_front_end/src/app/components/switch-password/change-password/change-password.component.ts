import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ChangePassword} from "../../../model/change-password.model";
import {SwitchPasswordService} from "../../../services/switch-password.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit, OnDestroy{
  protected change_password_form!:FormGroup;

  private removeRequest!:Subscription;

  constructor(
    private switchPasswordService: SwitchPasswordService
  ) {
  }

  ngOnInit(): void {

    this.change_password_form = new FormGroup<ChangePassword>({
      newPassword:new FormControl("", [
        Validators.required,
        Validators.minLength(8)
      ]),
      retryPassword:new FormControl("", [
        Validators.required,
        Validators.minLength(8)
      ]),
      code:new FormControl(this.switchPasswordService.getCode())
    })
  }

  ngOnDestroy(): void {
    this.removeRequest?.unsubscribe();
  }

  protected passwordIsEquals() {
    return this.change_password_form.get('newPassword')?.value === this.change_password_form.get('retryPassword')?.value;
  }
  public submit() {
    if(this.change_password_form.get('newPassword')?.value === this.change_password_form.get('retryPassword')?.value) {
      this.removeRequest = this.switchPasswordService.changePassword(this.change_password_form);
    }
  }

}

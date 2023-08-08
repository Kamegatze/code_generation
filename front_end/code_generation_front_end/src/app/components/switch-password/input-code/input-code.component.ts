import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CodeEmail} from "../../../model/code-email.model";
import {catchError, Subscription} from "rxjs";
import {SwitchPasswordService} from "../../../services/switch-password.service";
import {SwitchPasswordResponse} from "../../../model/switch-password-response";

@Component({
  selector: 'app-input-code',
  templateUrl: './input-code.component.html',
  styleUrls: ['./input-code.component.scss']
})
export class InputCodeComponent implements OnInit, OnDestroy{

  protected code_email!:FormGroup;

  protected code!:SwitchPasswordResponse

  private removeRequest!:Subscription;

  constructor(
      private switchPasswordService:SwitchPasswordService
  ) {
  }

  ngOnInit(): void {
    this.code_email = new FormGroup<CodeEmail>({
      onePart:new FormControl('',
          [
              Validators.required,
              Validators.minLength(3),
              Validators.maxLength(3)
          ]),
      twoPart:new FormControl('',
          [
            Validators.required,
            Validators.maxLength(3),
            Validators.minLength(3),
          ])
    });

    this.code = {
      exist:true,
      message:""
    }
  }

  public correction() {
    return (this.code_email.get('onePart')?.invalid && this.code_email.get('onePart')?.touched)
    || (this.code_email.get('twoPart')?.invalid && this.code_email.get('twoPart')?.touched);
  }

  ngOnDestroy(): void {
    this.removeRequest?.unsubscribe();
  }

  public submit() {
    const code_email = this.code_email.value;
    const code:string = `${code_email.onePart}-${code_email.twoPart}`
    this.removeRequest = this.switchPasswordService.checkCode(code)
        .pipe(
            catchError(err => {

                this.code = err.error;

                console.error(err.error.message);

                return [];
            })
        )
        .subscribe(value => {

          this.switchPasswordService.setCode(code)

          this.code = value
        });
  }

}

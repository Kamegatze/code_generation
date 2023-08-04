import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Registration} from "../../model/registration";
import {Role} from "../../model/role";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RegistrationService} from "../../services/registration.service";
import {AuthService} from "../../services/auth.service";
import {Login} from "../../model/login-interface";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit, OnDestroy{
  protected registration!:FormGroup;

  protected correctPassword!:string;

  private removeSubscriptionRegistration!:Subscription;

  private removeSubscriptionAuth!:Subscription;

  constructor(
    private registrationService:RegistrationService,
    private authService: AuthService
  ) {
  }

  ngOnDestroy(): void {
    this.removeSubscriptionRegistration?.unsubscribe();
    this.removeSubscriptionAuth?.unsubscribe();
  }


  ngOnInit(): void {
    this.registration = new FormGroup<Registration>({
      nickname: new FormControl('',[
        Validators.required,
        Validators.minLength(2)
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8)
      ]),
      role: new FormControl<Role>(Role.ROLE_USER)
    });

    this.correctPassword = '';
  }

    public isCorrectPassword(onePassword:string, twoPassword:string):boolean {
        return onePassword === twoPassword;
    }

    public submit() {
      const response$ = this.registrationService.sign_up(this.registration.value);

      this.removeSubscriptionRegistration = response$.subscribe(
        value => {
          /*
          * Create model login for authorization in system
          * */
          const login:FormGroup = new FormGroup<Login>({
            login: new FormControl(value.nickname),
            password: new FormControl(value.password)
          });

          this.removeSubscriptionAuth = this.authService.sign_in(login.value).subscribe(
            value1 => this.authService.setHoldUserData(value1)
          );
        }
      );
  }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Login} from "../../model/login-interface";
import {AuthService} from "../../services/auth.service";
import {ResponseServerAfterLogin} from "../../model/response-server-after-login";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-auth-users',
  templateUrl: './auth-users.component.html',
  styleUrls: ['./auth-users.component.scss']
})
export class AuthUsersComponent implements OnInit, OnDestroy{
  public formData!: FormGroup;

  private authInfo$!:Observable<ResponseServerAfterLogin>;

  private removeAuthInfo!:Subscription;

  ngOnInit(): void {
    this.formData = new FormGroup<Login>({
      login: new FormControl(),
      password: new FormControl()
    })
  }

  ngOnDestroy(): void {
    this.removeAuthInfo?.unsubscribe();
  }

  constructor(
    private authService: AuthService,
  ) {
  }

  protected submit() {
    this.authInfo$ = this.authService.sign_in(this.formData.value);

    this.removeAuthInfo = this.authInfo$
        .subscribe(
            value => {this.authService.setHoldUserData(value)}
        )
  }

}

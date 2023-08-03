import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Login} from "../../model/login-interface";

@Component({
  selector: 'app-auth-users',
  templateUrl: './auth-users.component.html',
  styleUrls: ['./auth-users.component.scss']
})
export class AuthUsersComponent implements OnInit{
  public formData!: FormGroup;

  protected submit() {
    console.log(this.formData.value);
  }

  ngOnInit(): void {
    this.formData = new FormGroup<Login>({
      login: new FormControl(),
      password: new FormControl()
    })
  }

}

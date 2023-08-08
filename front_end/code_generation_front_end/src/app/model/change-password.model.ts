import {FormControl} from "@angular/forms";

export interface ChangePassword {
  newPassword:FormControl,
  retryPassword:FormControl,
  code:FormControl
}

import {Role} from "./role";
import {FormControl} from "@angular/forms";
export interface Registration{
  nickname:FormControl,
  email:FormControl,
  password:FormControl,
  role:FormControl
}

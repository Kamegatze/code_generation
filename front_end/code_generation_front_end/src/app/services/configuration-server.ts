import {Injectable} from "@angular/core";

@Injectable({
    providedIn:'root'
  })
export class ConfigurationServer {
  private url = "http://localhost:8080/api"
  public authServiceUrl = this.url + '/auth/service';
}

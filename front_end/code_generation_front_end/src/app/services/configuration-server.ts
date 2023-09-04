import {Injectable} from "@angular/core";

@Injectable({
    providedIn:'root'
  })
export class ConfigurationServer {
  private url = "http://localhost:8080/api"
  private authServiceUrl = `${this.url}/auth/service`;
  private projectUrl = `${this.url}/project`;
  private entityUrl = `${this.url}/entity`;
  private fieldsUrl = `${this.url}/fields`
  private typeStandardUrl = `${this.url}/type_standard`;

  public getAuthServiceUrl() {
    return this.authServiceUrl;
  }

  public getProjectUrl() {
    return this.projectUrl;
  }

  public getEntityUrl() {
    return this.entityUrl;
  }

  public getFieldsUrl() {
    return this.fieldsUrl;
  }

  public getTypeStandardUrl() {
    return this.typeStandardUrl;
  }
}

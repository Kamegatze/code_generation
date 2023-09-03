import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigurationServer } from './configuration-server';
import { Observable } from 'rxjs';
import { Fields } from '../model/fields';

@Injectable({
  providedIn: 'root'
})
export class FieldsService {

  constructor(
    private http: HttpClient,
    private config: ConfigurationServer
  ) { }

  public getFieldsByEntityId(entityId: number) : Observable<Fields[]> {
    return this.http.get<Fields[]>(`${this.config.getFieldsUrl()}/${entityId}`);
  }
}

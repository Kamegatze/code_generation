import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigurationServer } from './configuration-server';
import { Observable, from } from 'rxjs';
import { TypeStandard } from '../model/type-standard';

@Injectable({
  providedIn: 'root'
})
export class TypeStandardService {

  constructor(
    private http: HttpClient,
    private config: ConfigurationServer,
  ) { }

  public handleGetTypeStandard() {
    return this.http.get<TypeStandard[]>(`${this.config.getTypeStandardUrl()}`)
  }
}

import { Injectable } from '@angular/core';
import { ConfigurationServer } from './configuration-server';
import { Observable, delay } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Entity } from '../model/entity';
import { TransferEntity } from '../model/transfer-entity';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  constructor(
    private config: ConfigurationServer,
    private http: HttpClient
    ) { }

  public getEntityByProjectId(id: number) : Observable<Entity[]> {
    return this.http.get<Entity[]>(
      `${this.config.getEntityUrl()}/get_types_by_project_id/${id}`)
      .pipe(delay(200));
  }

  public createEntity(entity: TransferEntity) : Observable<any> {
    return this.http
    .post<any>(`${this.config.getEntityUrl()}/create_entity`, entity, {observe: 'response'});
  }

  public getEntityById(id: number) : Observable<Entity> {
    return this.http.get<Entity>(`${this.config.getEntityUrl()}/${id}`);
  }

  public deleteEntityById(id:number) : Observable<any> {
    return this.http.get<any>(`${this.config.getEntityUrl()}/remove/${id}`);
  }
}

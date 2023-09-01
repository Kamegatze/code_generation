import { HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription, delay, of } from 'rxjs';
import { Entity } from 'src/app/model/entity';
import { FormEntity } from 'src/app/model/form-entity';
import { TransferEntity } from 'src/app/model/transfer-entity';
import { EntityService } from 'src/app/services/entity.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.scss']
})
export class ProjectDetailsComponent implements OnInit, OnDestroy{
  
  protected entity$!:Observable<Entity[]>;

  private removeReote!:Subscription;

  private removeCreate!:Subscription;

  protected isMoreZero:boolean = false;

  protected removeEntity!:Subscription;

  protected form!:FormGroup<FormEntity>;

  private projectId!:number;



  constructor(
    private entityService: EntityService,
    private route: ActivatedRoute
  ) {}


  ngOnDestroy(): void {
    this.removeReote?.unsubscribe();
    this.removeEntity?.unsubscribe();
    this.removeCreate?.unsubscribe();
  }

  ngOnInit(): void {

    this.form = new FormGroup<FormEntity>(<FormEntity>{
      nameClass: new FormControl<string>("")
    });

    this.removeReote = this.route.params.subscribe(params => {
      this.projectId = params['id'];
      this.entity$ = this.entityService.getEntityByProjectId(this.projectId);
    });

    this.removeEntity = this.entity$
    .subscribe(entity => {
      this.isMoreZero = entity.length > 0
    });
  }

  protected create() {
    const enity:TransferEntity = <TransferEntity>{
      nameClass: this.form.value.nameClass,
      projectId: this.projectId,
      fields: {}
    }

    this.removeCreate?.unsubscribe();

    this.removeCreate = this.entityService.createEntity(enity)
    .subscribe(
      (resp:HttpResponse<object>) => {
        const url = resp.headers.get("Location")?.split("/");
        
        const id = Number(url?.[url.length - 1]);
        
        this.entityService.getEntityById(id).subscribe(
          enity => {

            this.removeEntity?.unsubscribe();

            this.removeEntity = this.entity$.subscribe(
              entities => {
                entities.push(enity);
                this.entity$ = of<Entity[]>(entities);
              }
            )
          }
        )
      });

  }

}

import { HttpResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription, delay, map, of} from 'rxjs';
import { Entity } from 'src/app/model/entity';
import { FormEntity } from 'src/app/model/form-entity';
import { TransferEntity } from 'src/app/model/transfer-entity';
import { EntityService } from 'src/app/services/entity.service';

@Component({
  changeDetection: ChangeDetectionStrategy.Default,
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.scss']
})
export class ProjectDetailsComponent implements OnInit, OnDestroy{
  
  protected entity$!:Observable<Entity[]>;

  private removeReote!:Subscription;

  private removeCreate!:Subscription;

  private removeDelete!:Subscription;

  protected isMoreZero:boolean = false;

  protected removeEntity!:Subscription;

  protected form!:FormGroup<FormEntity>;

  private projectId!:number;



  constructor(
    private entityService: EntityService,
    private route: ActivatedRoute,
    private changeDetector: ChangeDetectorRef
  ) {}


  ngOnDestroy(): void {
    this.removeReote?.unsubscribe();
    this.removeEntity?.unsubscribe();
    this.removeCreate?.unsubscribe();
    this.removeDelete?.unsubscribe();
  }

  ngOnInit(): void {

    this.form = new FormGroup<FormEntity>(<FormEntity>{
      nameClass: new FormControl<string>("")
    });

    this.removeReote = this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
      this.entity$ = this.entityService.getEntityByProjectId(this.projectId);
    });

    this.removeEntity = this.entity$
    .subscribe(entity => {
      this.isMoreZero = entity.length > 0;
    });
  }

  protected remove(id: number) : void {

    this.removeDelete?.unsubscribe();

    this.removeDelete = this.entityService.deleteEntityById(id).subscribe();

    this.removeEntity?.unsubscribe();

    this.removeEntity = this.entity$
      .pipe(
        map(entities => entities.filter(entity => entity.id !== id))
      ).subscribe(entities => {
        this.entity$ = of<Entity[]>(entities);

        this.isMoreZero = entities.length > 0;
      });
  }
  protected create() : void {
    const entity:TransferEntity = <TransferEntity>{
      nameClass: this.form.value.nameClass,
      projectId: this.projectId,
      fields: {}
    }

    let entityById!:Entity;

    this.removeCreate?.unsubscribe();

    this.removeCreate = this.entityService.createEntity(entity)
    .subscribe(
      (resp:HttpResponse<object>) => {
        const url = resp.headers.get("Location")?.split("/");
        
        const id = Number(url?.[url.length - 1]);
        
        this.entityService.getEntityById(id).subscribe(
          entityFromServer => {
            entityById = entityFromServer;
          }
        )
      }
    );
    
    

    this.entity$.pipe(delay(200)).subscribe(entities => {
      const index = entities.findIndex(element => element.nameClass === entity.nameClass);

      if(index < 0) {
        entities = [entityById, ...entities];
      }

      this.isMoreZero = entities.length > 0;

      this.entity$ = of<Entity[]>(entities);
    })
    
  }

}

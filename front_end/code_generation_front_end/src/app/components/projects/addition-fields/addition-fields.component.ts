import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Fields } from 'src/app/model/fields';
import { FieldsService } from 'src/app/services/fields.service';

@Component({
  selector: 'app-addition-fields',
  templateUrl: './addition-fields.component.html',
  styleUrls: ['./addition-fields.component.scss']
})
export class AdditionFieldsComponent implements OnInit{
  
  protected fields$!:Observable<Fields[]>;

  private entityId!:number;

  constructor(
    private fieldsService:FieldsService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {

    this.route.params.subscribe(
      params => {
        this.entityId = params['entityId'];
        this.fields$ = this.fieldsService.getFieldsByEntityId(this.entityId);
      }
    );
  }
}

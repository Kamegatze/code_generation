import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Observer, of } from 'rxjs';
import { Fields } from 'src/app/model/fields';
import { TypeStandard } from 'src/app/model/type-standard';
import { FieldsService } from 'src/app/services/fields.service';
import { TypeStandardService } from 'src/app/services/type-standard.service';

@Component({
  selector: 'app-addition-fields',
  templateUrl: './addition-fields.component.html',
  styleUrls: ['./addition-fields.component.scss']
})
export class AdditionFieldsComponent implements OnInit{
  
  protected fields$!:Observable<Fields[]>;

  protected typeStandards$!:Observable<TypeStandard[]>;

  private entityId!:number;

  constructor(
    private fieldsService:FieldsService,
    private route: ActivatedRoute,
    private typeStandardService:TypeStandardService,
  ) {}

  ngOnInit(): void {

    this.route.params.subscribe(
      params => {
        this.entityId = params['entityId'];
        this.fields$ = this.fieldsService.getFieldsByEntityId(this.entityId);
      }
    );

    this.typeStandards$ = this.typeStandardService.handleGetTypeStandard();
  }
}

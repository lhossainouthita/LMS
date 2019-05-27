/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { SituationDetailComponent } from 'app/entities/situation/situation-detail.component';
import { Situation } from 'app/shared/model/situation.model';

describe('Component Tests', () => {
  describe('Situation Management Detail Component', () => {
    let comp: SituationDetailComponent;
    let fixture: ComponentFixture<SituationDetailComponent>;
    const route = ({ data: of({ situation: new Situation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [SituationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SituationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SituationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.situation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

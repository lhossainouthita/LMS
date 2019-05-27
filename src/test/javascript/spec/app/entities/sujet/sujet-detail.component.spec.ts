/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { SujetDetailComponent } from 'app/entities/sujet/sujet-detail.component';
import { Sujet } from 'app/shared/model/sujet.model';

describe('Component Tests', () => {
  describe('Sujet Management Detail Component', () => {
    let comp: SujetDetailComponent;
    let fixture: ComponentFixture<SujetDetailComponent>;
    const route = ({ data: of({ sujet: new Sujet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [SujetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SujetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SujetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sujet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

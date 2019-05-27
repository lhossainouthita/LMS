/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { ParcourDetailComponent } from 'app/entities/parcour/parcour-detail.component';
import { Parcour } from 'app/shared/model/parcour.model';

describe('Component Tests', () => {
  describe('Parcour Management Detail Component', () => {
    let comp: ParcourDetailComponent;
    let fixture: ComponentFixture<ParcourDetailComponent>;
    const route = ({ data: of({ parcour: new Parcour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ParcourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParcourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParcourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parcour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

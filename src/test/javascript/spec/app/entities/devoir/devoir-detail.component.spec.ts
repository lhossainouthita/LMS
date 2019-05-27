/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { DevoirDetailComponent } from 'app/entities/devoir/devoir-detail.component';
import { Devoir } from 'app/shared/model/devoir.model';

describe('Component Tests', () => {
  describe('Devoir Management Detail Component', () => {
    let comp: DevoirDetailComponent;
    let fixture: ComponentFixture<DevoirDetailComponent>;
    const route = ({ data: of({ devoir: new Devoir(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [DevoirDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DevoirDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DevoirDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.devoir).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { RessourceDetailComponent } from 'app/entities/ressource/ressource-detail.component';
import { Ressource } from 'app/shared/model/ressource.model';

describe('Component Tests', () => {
  describe('Ressource Management Detail Component', () => {
    let comp: RessourceDetailComponent;
    let fixture: ComponentFixture<RessourceDetailComponent>;
    const route = ({ data: of({ ressource: new Ressource(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [RessourceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RessourceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RessourceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ressource).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { ModulePedagogiqueDetailComponent } from 'app/entities/module-pedagogique/module-pedagogique-detail.component';
import { ModulePedagogique } from 'app/shared/model/module-pedagogique.model';

describe('Component Tests', () => {
  describe('ModulePedagogique Management Detail Component', () => {
    let comp: ModulePedagogiqueDetailComponent;
    let fixture: ComponentFixture<ModulePedagogiqueDetailComponent>;
    const route = ({ data: of({ modulePedagogique: new ModulePedagogique(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ModulePedagogiqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ModulePedagogiqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModulePedagogiqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modulePedagogique).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

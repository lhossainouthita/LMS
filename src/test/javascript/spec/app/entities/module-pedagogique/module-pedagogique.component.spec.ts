/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { ModulePedagogiqueComponent } from 'app/entities/module-pedagogique/module-pedagogique.component';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique/module-pedagogique.service';
import { ModulePedagogique } from 'app/shared/model/module-pedagogique.model';

describe('Component Tests', () => {
  describe('ModulePedagogique Management Component', () => {
    let comp: ModulePedagogiqueComponent;
    let fixture: ComponentFixture<ModulePedagogiqueComponent>;
    let service: ModulePedagogiqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ModulePedagogiqueComponent],
        providers: []
      })
        .overrideTemplate(ModulePedagogiqueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModulePedagogiqueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModulePedagogiqueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ModulePedagogique(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.modulePedagogiques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

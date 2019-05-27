/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { SujetComponent } from 'app/entities/sujet/sujet.component';
import { SujetService } from 'app/entities/sujet/sujet.service';
import { Sujet } from 'app/shared/model/sujet.model';

describe('Component Tests', () => {
  describe('Sujet Management Component', () => {
    let comp: SujetComponent;
    let fixture: ComponentFixture<SujetComponent>;
    let service: SujetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [SujetComponent],
        providers: []
      })
        .overrideTemplate(SujetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SujetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SujetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sujet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sujets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

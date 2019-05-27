/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { PeriodeComponent } from 'app/entities/periode/periode.component';
import { PeriodeService } from 'app/entities/periode/periode.service';
import { Periode } from 'app/shared/model/periode.model';

describe('Component Tests', () => {
  describe('Periode Management Component', () => {
    let comp: PeriodeComponent;
    let fixture: ComponentFixture<PeriodeComponent>;
    let service: PeriodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [PeriodeComponent],
        providers: []
      })
        .overrideTemplate(PeriodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Periode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.periodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

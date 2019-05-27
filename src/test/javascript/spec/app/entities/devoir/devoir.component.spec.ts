/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { DevoirComponent } from 'app/entities/devoir/devoir.component';
import { DevoirService } from 'app/entities/devoir/devoir.service';
import { Devoir } from 'app/shared/model/devoir.model';

describe('Component Tests', () => {
  describe('Devoir Management Component', () => {
    let comp: DevoirComponent;
    let fixture: ComponentFixture<DevoirComponent>;
    let service: DevoirService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [DevoirComponent],
        providers: []
      })
        .overrideTemplate(DevoirComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DevoirComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DevoirService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Devoir(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.devoirs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

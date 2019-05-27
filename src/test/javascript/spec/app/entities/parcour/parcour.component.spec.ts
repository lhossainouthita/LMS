/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { ParcourComponent } from 'app/entities/parcour/parcour.component';
import { ParcourService } from 'app/entities/parcour/parcour.service';
import { Parcour } from 'app/shared/model/parcour.model';

describe('Component Tests', () => {
  describe('Parcour Management Component', () => {
    let comp: ParcourComponent;
    let fixture: ComponentFixture<ParcourComponent>;
    let service: ParcourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ParcourComponent],
        providers: []
      })
        .overrideTemplate(ParcourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParcourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Parcour(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.parcours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

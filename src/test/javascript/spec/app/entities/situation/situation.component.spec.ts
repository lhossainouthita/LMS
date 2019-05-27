/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { SituationComponent } from 'app/entities/situation/situation.component';
import { SituationService } from 'app/entities/situation/situation.service';
import { Situation } from 'app/shared/model/situation.model';

describe('Component Tests', () => {
  describe('Situation Management Component', () => {
    let comp: SituationComponent;
    let fixture: ComponentFixture<SituationComponent>;
    let service: SituationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [SituationComponent],
        providers: []
      })
        .overrideTemplate(SituationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SituationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SituationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Situation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.situations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

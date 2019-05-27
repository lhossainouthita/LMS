/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { CompetenceComponent } from 'app/entities/competence/competence.component';
import { CompetenceService } from 'app/entities/competence/competence.service';
import { Competence } from 'app/shared/model/competence.model';

describe('Component Tests', () => {
  describe('Competence Management Component', () => {
    let comp: CompetenceComponent;
    let fixture: ComponentFixture<CompetenceComponent>;
    let service: CompetenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [CompetenceComponent],
        providers: []
      })
        .overrideTemplate(CompetenceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetenceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetenceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Competence(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

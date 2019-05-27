/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { ExerciceComponent } from 'app/entities/exercice/exercice.component';
import { ExerciceService } from 'app/entities/exercice/exercice.service';
import { Exercice } from 'app/shared/model/exercice.model';

describe('Component Tests', () => {
  describe('Exercice Management Component', () => {
    let comp: ExerciceComponent;
    let fixture: ComponentFixture<ExerciceComponent>;
    let service: ExerciceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ExerciceComponent],
        providers: []
      })
        .overrideTemplate(ExerciceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExerciceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExerciceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Exercice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.exercices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

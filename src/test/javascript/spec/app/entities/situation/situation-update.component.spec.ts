/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { SituationUpdateComponent } from 'app/entities/situation/situation-update.component';
import { SituationService } from 'app/entities/situation/situation.service';
import { Situation } from 'app/shared/model/situation.model';

describe('Component Tests', () => {
  describe('Situation Management Update Component', () => {
    let comp: SituationUpdateComponent;
    let fixture: ComponentFixture<SituationUpdateComponent>;
    let service: SituationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [SituationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SituationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SituationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SituationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Situation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Situation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

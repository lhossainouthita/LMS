/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { SujetUpdateComponent } from 'app/entities/sujet/sujet-update.component';
import { SujetService } from 'app/entities/sujet/sujet.service';
import { Sujet } from 'app/shared/model/sujet.model';

describe('Component Tests', () => {
  describe('Sujet Management Update Component', () => {
    let comp: SujetUpdateComponent;
    let fixture: ComponentFixture<SujetUpdateComponent>;
    let service: SujetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [SujetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SujetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SujetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SujetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sujet(123);
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
        const entity = new Sujet();
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

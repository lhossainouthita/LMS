/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { ParcourUpdateComponent } from 'app/entities/parcour/parcour-update.component';
import { ParcourService } from 'app/entities/parcour/parcour.service';
import { Parcour } from 'app/shared/model/parcour.model';

describe('Component Tests', () => {
  describe('Parcour Management Update Component', () => {
    let comp: ParcourUpdateComponent;
    let fixture: ComponentFixture<ParcourUpdateComponent>;
    let service: ParcourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ParcourUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParcourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParcourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Parcour(123);
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
        const entity = new Parcour();
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

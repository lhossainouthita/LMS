/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { RessourceUpdateComponent } from 'app/entities/ressource/ressource-update.component';
import { RessourceService } from 'app/entities/ressource/ressource.service';
import { Ressource } from 'app/shared/model/ressource.model';

describe('Component Tests', () => {
  describe('Ressource Management Update Component', () => {
    let comp: RessourceUpdateComponent;
    let fixture: ComponentFixture<RessourceUpdateComponent>;
    let service: RessourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [RessourceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RessourceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RessourceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RessourceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ressource(123);
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
        const entity = new Ressource();
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

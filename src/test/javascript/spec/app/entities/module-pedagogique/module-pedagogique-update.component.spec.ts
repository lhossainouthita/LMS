/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { ModulePedagogiqueUpdateComponent } from 'app/entities/module-pedagogique/module-pedagogique-update.component';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique/module-pedagogique.service';
import { ModulePedagogique } from 'app/shared/model/module-pedagogique.model';

describe('Component Tests', () => {
  describe('ModulePedagogique Management Update Component', () => {
    let comp: ModulePedagogiqueUpdateComponent;
    let fixture: ComponentFixture<ModulePedagogiqueUpdateComponent>;
    let service: ModulePedagogiqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ModulePedagogiqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ModulePedagogiqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModulePedagogiqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModulePedagogiqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModulePedagogique(123);
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
        const entity = new ModulePedagogique();
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

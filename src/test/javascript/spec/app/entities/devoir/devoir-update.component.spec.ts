/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LmsTestModule } from '../../../test.module';
import { DevoirUpdateComponent } from 'app/entities/devoir/devoir-update.component';
import { DevoirService } from 'app/entities/devoir/devoir.service';
import { Devoir } from 'app/shared/model/devoir.model';

describe('Component Tests', () => {
  describe('Devoir Management Update Component', () => {
    let comp: DevoirUpdateComponent;
    let fixture: ComponentFixture<DevoirUpdateComponent>;
    let service: DevoirService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [DevoirUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DevoirUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DevoirUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DevoirService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Devoir(123);
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
        const entity = new Devoir();
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

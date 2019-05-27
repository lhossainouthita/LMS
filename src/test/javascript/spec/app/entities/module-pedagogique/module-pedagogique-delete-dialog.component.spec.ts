/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsTestModule } from '../../../test.module';
import { ModulePedagogiqueDeleteDialogComponent } from 'app/entities/module-pedagogique/module-pedagogique-delete-dialog.component';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique/module-pedagogique.service';

describe('Component Tests', () => {
  describe('ModulePedagogique Management Delete Component', () => {
    let comp: ModulePedagogiqueDeleteDialogComponent;
    let fixture: ComponentFixture<ModulePedagogiqueDeleteDialogComponent>;
    let service: ModulePedagogiqueService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ModulePedagogiqueDeleteDialogComponent]
      })
        .overrideTemplate(ModulePedagogiqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModulePedagogiqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModulePedagogiqueService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

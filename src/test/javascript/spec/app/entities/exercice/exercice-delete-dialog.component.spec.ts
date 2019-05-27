/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsTestModule } from '../../../test.module';
import { ExerciceDeleteDialogComponent } from 'app/entities/exercice/exercice-delete-dialog.component';
import { ExerciceService } from 'app/entities/exercice/exercice.service';

describe('Component Tests', () => {
  describe('Exercice Management Delete Component', () => {
    let comp: ExerciceDeleteDialogComponent;
    let fixture: ComponentFixture<ExerciceDeleteDialogComponent>;
    let service: ExerciceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [ExerciceDeleteDialogComponent]
      })
        .overrideTemplate(ExerciceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExerciceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExerciceService);
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

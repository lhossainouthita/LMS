import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriode } from 'app/shared/model/periode.model';
import { PeriodeService } from './periode.service';

@Component({
  selector: 'jhi-periode-delete-dialog',
  templateUrl: './periode-delete-dialog.component.html'
})
export class PeriodeDeleteDialogComponent {
  periode: IPeriode;

  constructor(protected periodeService: PeriodeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.periodeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'periodeListModification',
        content: 'Deleted an periode'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-periode-delete-popup',
  template: ''
})
export class PeriodeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ periode }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PeriodeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.periode = periode;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/periode', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/periode', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

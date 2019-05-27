import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevoir } from 'app/shared/model/devoir.model';
import { DevoirService } from './devoir.service';

@Component({
  selector: 'jhi-devoir-delete-dialog',
  templateUrl: './devoir-delete-dialog.component.html'
})
export class DevoirDeleteDialogComponent {
  devoir: IDevoir;

  constructor(protected devoirService: DevoirService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.devoirService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'devoirListModification',
        content: 'Deleted an devoir'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-devoir-delete-popup',
  template: ''
})
export class DevoirDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ devoir }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DevoirDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.devoir = devoir;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/devoir', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/devoir', { outlets: { popup: null } }]);
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

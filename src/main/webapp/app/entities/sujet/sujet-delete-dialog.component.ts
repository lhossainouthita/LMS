import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISujet } from 'app/shared/model/sujet.model';
import { SujetService } from './sujet.service';

@Component({
  selector: 'jhi-sujet-delete-dialog',
  templateUrl: './sujet-delete-dialog.component.html'
})
export class SujetDeleteDialogComponent {
  sujet: ISujet;

  constructor(protected sujetService: SujetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sujetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sujetListModification',
        content: 'Deleted an sujet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sujet-delete-popup',
  template: ''
})
export class SujetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sujet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SujetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sujet = sujet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sujet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sujet', { outlets: { popup: null } }]);
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

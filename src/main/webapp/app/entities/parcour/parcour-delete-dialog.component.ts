import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParcour } from 'app/shared/model/parcour.model';
import { ParcourService } from './parcour.service';

@Component({
  selector: 'jhi-parcour-delete-dialog',
  templateUrl: './parcour-delete-dialog.component.html'
})
export class ParcourDeleteDialogComponent {
  parcour: IParcour;

  constructor(protected parcourService: ParcourService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.parcourService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'parcourListModification',
        content: 'Deleted an parcour'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-parcour-delete-popup',
  template: ''
})
export class ParcourDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parcour }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ParcourDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.parcour = parcour;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/parcour', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/parcour', { outlets: { popup: null } }]);
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

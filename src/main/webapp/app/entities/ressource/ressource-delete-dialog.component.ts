import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRessource } from 'app/shared/model/ressource.model';
import { RessourceService } from './ressource.service';

@Component({
  selector: 'jhi-ressource-delete-dialog',
  templateUrl: './ressource-delete-dialog.component.html'
})
export class RessourceDeleteDialogComponent {
  ressource: IRessource;

  constructor(protected ressourceService: RessourceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ressourceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ressourceListModification',
        content: 'Deleted an ressource'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ressource-delete-popup',
  template: ''
})
export class RessourceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ressource }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RessourceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ressource = ressource;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ressource', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ressource', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISituation } from 'app/shared/model/situation.model';
import { SituationService } from './situation.service';

@Component({
  selector: 'jhi-situation-delete-dialog',
  templateUrl: './situation-delete-dialog.component.html'
})
export class SituationDeleteDialogComponent {
  situation: ISituation;

  constructor(protected situationService: SituationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.situationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'situationListModification',
        content: 'Deleted an situation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-situation-delete-popup',
  template: ''
})
export class SituationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ situation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SituationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.situation = situation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/situation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/situation', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetence } from 'app/shared/model/competence.model';
import { CompetenceService } from './competence.service';

@Component({
  selector: 'jhi-competence-delete-dialog',
  templateUrl: './competence-delete-dialog.component.html'
})
export class CompetenceDeleteDialogComponent {
  competence: ICompetence;

  constructor(
    protected competenceService: CompetenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.competenceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'competenceListModification',
        content: 'Deleted an competence'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-competence-delete-popup',
  template: ''
})
export class CompetenceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ competence }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompetenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.competence = competence;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/competence', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/competence', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from './module-pedagogique.service';

@Component({
  selector: 'jhi-module-pedagogique-delete-dialog',
  templateUrl: './module-pedagogique-delete-dialog.component.html'
})
export class ModulePedagogiqueDeleteDialogComponent {
  modulePedagogique: IModulePedagogique;

  constructor(
    protected modulePedagogiqueService: ModulePedagogiqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.modulePedagogiqueService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'modulePedagogiqueListModification',
        content: 'Deleted an modulePedagogique'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-module-pedagogique-delete-popup',
  template: ''
})
export class ModulePedagogiqueDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modulePedagogique }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ModulePedagogiqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.modulePedagogique = modulePedagogique;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/module-pedagogique', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/module-pedagogique', { outlets: { popup: null } }]);
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

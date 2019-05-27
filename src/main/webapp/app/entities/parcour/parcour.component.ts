import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParcour } from 'app/shared/model/parcour.model';
import { AccountService } from 'app/core';
import { ParcourService } from './parcour.service';

@Component({
  selector: 'jhi-parcour',
  templateUrl: './parcour.component.html'
})
export class ParcourComponent implements OnInit, OnDestroy {
  parcours: IParcour[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected parcourService: ParcourService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.parcourService
      .query()
      .pipe(
        filter((res: HttpResponse<IParcour[]>) => res.ok),
        map((res: HttpResponse<IParcour[]>) => res.body)
      )
      .subscribe(
        (res: IParcour[]) => {
          this.parcours = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInParcours();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IParcour) {
    return item.id;
  }

  registerChangeInParcours() {
    this.eventSubscriber = this.eventManager.subscribe('parcourListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

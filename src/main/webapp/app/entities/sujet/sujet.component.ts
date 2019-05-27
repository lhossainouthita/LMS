import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISujet } from 'app/shared/model/sujet.model';
import { AccountService } from 'app/core';
import { SujetService } from './sujet.service';

@Component({
  selector: 'jhi-sujet',
  templateUrl: './sujet.component.html'
})
export class SujetComponent implements OnInit, OnDestroy {
  sujets: ISujet[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sujetService: SujetService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sujetService
      .query()
      .pipe(
        filter((res: HttpResponse<ISujet[]>) => res.ok),
        map((res: HttpResponse<ISujet[]>) => res.body)
      )
      .subscribe(
        (res: ISujet[]) => {
          this.sujets = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSujets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISujet) {
    return item.id;
  }

  registerChangeInSujets() {
    this.eventSubscriber = this.eventManager.subscribe('sujetListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

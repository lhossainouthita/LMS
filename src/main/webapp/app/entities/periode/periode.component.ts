import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPeriode } from 'app/shared/model/periode.model';
import { AccountService } from 'app/core';
import { PeriodeService } from './periode.service';

@Component({
  selector: 'jhi-periode',
  templateUrl: './periode.component.html'
})
export class PeriodeComponent implements OnInit, OnDestroy {
  periodes: IPeriode[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected periodeService: PeriodeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.periodeService
      .query()
      .pipe(
        filter((res: HttpResponse<IPeriode[]>) => res.ok),
        map((res: HttpResponse<IPeriode[]>) => res.body)
      )
      .subscribe(
        (res: IPeriode[]) => {
          this.periodes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPeriodes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPeriode) {
    return item.id;
  }

  registerChangeInPeriodes() {
    this.eventSubscriber = this.eventManager.subscribe('periodeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

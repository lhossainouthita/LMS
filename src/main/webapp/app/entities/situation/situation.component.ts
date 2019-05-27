import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISituation } from 'app/shared/model/situation.model';
import { AccountService } from 'app/core';
import { SituationService } from './situation.service';

@Component({
  selector: 'jhi-situation',
  templateUrl: './situation.component.html'
})
export class SituationComponent implements OnInit, OnDestroy {
  situations: ISituation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected situationService: SituationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.situationService
      .query()
      .pipe(
        filter((res: HttpResponse<ISituation[]>) => res.ok),
        map((res: HttpResponse<ISituation[]>) => res.body)
      )
      .subscribe(
        (res: ISituation[]) => {
          this.situations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSituations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISituation) {
    return item.id;
  }

  registerChangeInSituations() {
    this.eventSubscriber = this.eventManager.subscribe('situationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

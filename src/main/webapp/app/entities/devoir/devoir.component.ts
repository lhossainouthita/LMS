import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDevoir } from 'app/shared/model/devoir.model';
import { AccountService } from 'app/core';
import { DevoirService } from './devoir.service';

@Component({
  selector: 'jhi-devoir',
  templateUrl: './devoir.component.html'
})
export class DevoirComponent implements OnInit, OnDestroy {
  devoirs: IDevoir[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected devoirService: DevoirService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.devoirService
      .query()
      .pipe(
        filter((res: HttpResponse<IDevoir[]>) => res.ok),
        map((res: HttpResponse<IDevoir[]>) => res.body)
      )
      .subscribe(
        (res: IDevoir[]) => {
          this.devoirs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDevoirs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDevoir) {
    return item.id;
  }

  registerChangeInDevoirs() {
    this.eventSubscriber = this.eventManager.subscribe('devoirListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

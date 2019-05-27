import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { AccountService } from 'app/core';
import { ModulePedagogiqueService } from './module-pedagogique.service';

@Component({
  selector: 'jhi-module-pedagogique',
  templateUrl: './module-pedagogique.component.html'
})
export class ModulePedagogiqueComponent implements OnInit, OnDestroy {
  modulePedagogiques: IModulePedagogique[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected modulePedagogiqueService: ModulePedagogiqueService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.modulePedagogiqueService
      .query()
      .pipe(
        filter((res: HttpResponse<IModulePedagogique[]>) => res.ok),
        map((res: HttpResponse<IModulePedagogique[]>) => res.body)
      )
      .subscribe(
        (res: IModulePedagogique[]) => {
          this.modulePedagogiques = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInModulePedagogiques();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IModulePedagogique) {
    return item.id;
  }

  registerChangeInModulePedagogiques() {
    this.eventSubscriber = this.eventManager.subscribe('modulePedagogiqueListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRessource } from 'app/shared/model/ressource.model';
import { AccountService } from 'app/core';
import { RessourceService } from './ressource.service';

@Component({
  selector: 'jhi-ressource',
  templateUrl: './ressource.component.html'
})
export class RessourceComponent implements OnInit, OnDestroy {
  ressources: IRessource[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected ressourceService: RessourceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.ressourceService
      .query()
      .pipe(
        filter((res: HttpResponse<IRessource[]>) => res.ok),
        map((res: HttpResponse<IRessource[]>) => res.body)
      )
      .subscribe(
        (res: IRessource[]) => {
          this.ressources = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRessources();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRessource) {
    return item.id;
  }

  registerChangeInRessources() {
    this.eventSubscriber = this.eventManager.subscribe('ressourceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

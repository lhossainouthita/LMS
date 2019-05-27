import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompetence } from 'app/shared/model/competence.model';
import { AccountService } from 'app/core';
import { CompetenceService } from './competence.service';

@Component({
  selector: 'jhi-competence',
  templateUrl: './competence.component.html'
})
export class CompetenceComponent implements OnInit, OnDestroy {
  competences: ICompetence[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected competenceService: CompetenceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.competenceService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompetence[]>) => res.ok),
        map((res: HttpResponse<ICompetence[]>) => res.body)
      )
      .subscribe(
        (res: ICompetence[]) => {
          this.competences = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompetences();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompetence) {
    return item.id;
  }

  registerChangeInCompetences() {
    this.eventSubscriber = this.eventManager.subscribe('competenceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

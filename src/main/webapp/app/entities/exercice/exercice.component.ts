import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExercice } from 'app/shared/model/exercice.model';
import { AccountService } from 'app/core';
import { ExerciceService } from './exercice.service';

@Component({
  selector: 'jhi-exercice',
  templateUrl: './exercice.component.html'
})
export class ExerciceComponent implements OnInit, OnDestroy {
  exercices: IExercice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected exerciceService: ExerciceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.exerciceService
      .query()
      .pipe(
        filter((res: HttpResponse<IExercice[]>) => res.ok),
        map((res: HttpResponse<IExercice[]>) => res.body)
      )
      .subscribe(
        (res: IExercice[]) => {
          this.exercices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInExercices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IExercice) {
    return item.id;
  }

  registerChangeInExercices() {
    this.eventSubscriber = this.eventManager.subscribe('exerciceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPeriode, Periode } from 'app/shared/model/periode.model';
import { PeriodeService } from './periode.service';
import { ISujet } from 'app/shared/model/sujet.model';
import { SujetService } from 'app/entities/sujet';

@Component({
  selector: 'jhi-periode-update',
  templateUrl: './periode-update.component.html'
})
export class PeriodeUpdateComponent implements OnInit {
  periode: IPeriode;
  isSaving: boolean;

  sujets: ISujet[];
  dateDebutDp: any;
  dateFinDp: any;

  editForm = this.fb.group({
    id: [],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    sujetId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected periodeService: PeriodeService,
    protected sujetService: SujetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ periode }) => {
      this.updateForm(periode);
      this.periode = periode;
    });
    this.sujetService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISujet[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISujet[]>) => response.body)
      )
      .subscribe((res: ISujet[]) => (this.sujets = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(periode: IPeriode) {
    this.editForm.patchValue({
      id: periode.id,
      dateDebut: periode.dateDebut,
      dateFin: periode.dateFin,
      sujetId: periode.sujetId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const periode = this.createFromForm();
    if (periode.id !== undefined) {
      this.subscribeToSaveResponse(this.periodeService.update(periode));
    } else {
      this.subscribeToSaveResponse(this.periodeService.create(periode));
    }
  }

  private createFromForm(): IPeriode {
    const entity = {
      ...new Periode(),
      id: this.editForm.get(['id']).value,
      dateDebut: this.editForm.get(['dateDebut']).value,
      dateFin: this.editForm.get(['dateFin']).value,
      sujetId: this.editForm.get(['sujetId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriode>>) {
    result.subscribe((res: HttpResponse<IPeriode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSujetById(index: number, item: ISujet) {
    return item.id;
  }
}

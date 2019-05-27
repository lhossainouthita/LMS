import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISituation, Situation } from 'app/shared/model/situation.model';
import { SituationService } from './situation.service';
import { IExercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from 'app/entities/exercice';
import { ICours } from 'app/shared/model/cours.model';
import { CoursService } from 'app/entities/cours';

@Component({
  selector: 'jhi-situation-update',
  templateUrl: './situation-update.component.html'
})
export class SituationUpdateComponent implements OnInit {
  situation: ISituation;
  isSaving: boolean;

  exercices: IExercice[];

  cours: ICours[];

  editForm = this.fb.group({
    id: [],
    titreSituation: [null, [Validators.required]],
    contenuSituation: [null, [Validators.required]],
    exerciceId: [],
    coursId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected situationService: SituationService,
    protected exerciceService: ExerciceService,
    protected coursService: CoursService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ situation }) => {
      this.updateForm(situation);
      this.situation = situation;
    });
    this.exerciceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IExercice[]>) => mayBeOk.ok),
        map((response: HttpResponse<IExercice[]>) => response.body)
      )
      .subscribe((res: IExercice[]) => (this.exercices = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.coursService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICours[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICours[]>) => response.body)
      )
      .subscribe((res: ICours[]) => (this.cours = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(situation: ISituation) {
    this.editForm.patchValue({
      id: situation.id,
      titreSituation: situation.titreSituation,
      contenuSituation: situation.contenuSituation,
      exerciceId: situation.exerciceId,
      coursId: situation.coursId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const situation = this.createFromForm();
    if (situation.id !== undefined) {
      this.subscribeToSaveResponse(this.situationService.update(situation));
    } else {
      this.subscribeToSaveResponse(this.situationService.create(situation));
    }
  }

  private createFromForm(): ISituation {
    const entity = {
      ...new Situation(),
      id: this.editForm.get(['id']).value,
      titreSituation: this.editForm.get(['titreSituation']).value,
      contenuSituation: this.editForm.get(['contenuSituation']).value,
      exerciceId: this.editForm.get(['exerciceId']).value,
      coursId: this.editForm.get(['coursId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituation>>) {
    result.subscribe((res: HttpResponse<ISituation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackExerciceById(index: number, item: IExercice) {
    return item.id;
  }

  trackCoursById(index: number, item: ICours) {
    return item.id;
  }
}

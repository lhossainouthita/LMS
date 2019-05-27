import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExercice, Exercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from './exercice.service';
import { IParcour } from 'app/shared/model/parcour.model';
import { ParcourService } from 'app/entities/parcour';

@Component({
  selector: 'jhi-exercice-update',
  templateUrl: './exercice-update.component.html'
})
export class ExerciceUpdateComponent implements OnInit {
  exercice: IExercice;
  isSaving: boolean;

  parcours: IParcour[];

  editForm = this.fb.group({
    id: [],
    titreExercice: [null, [Validators.required]],
    contenuExercice: [null, [Validators.required]],
    parcourId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected exerciceService: ExerciceService,
    protected parcourService: ParcourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ exercice }) => {
      this.updateForm(exercice);
      this.exercice = exercice;
    });
    this.parcourService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IParcour[]>) => mayBeOk.ok),
        map((response: HttpResponse<IParcour[]>) => response.body)
      )
      .subscribe((res: IParcour[]) => (this.parcours = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(exercice: IExercice) {
    this.editForm.patchValue({
      id: exercice.id,
      titreExercice: exercice.titreExercice,
      contenuExercice: exercice.contenuExercice,
      parcourId: exercice.parcourId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const exercice = this.createFromForm();
    if (exercice.id !== undefined) {
      this.subscribeToSaveResponse(this.exerciceService.update(exercice));
    } else {
      this.subscribeToSaveResponse(this.exerciceService.create(exercice));
    }
  }

  private createFromForm(): IExercice {
    const entity = {
      ...new Exercice(),
      id: this.editForm.get(['id']).value,
      titreExercice: this.editForm.get(['titreExercice']).value,
      contenuExercice: this.editForm.get(['contenuExercice']).value,
      parcourId: this.editForm.get(['parcourId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExercice>>) {
    result.subscribe((res: HttpResponse<IExercice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackParcourById(index: number, item: IParcour) {
    return item.id;
  }
}

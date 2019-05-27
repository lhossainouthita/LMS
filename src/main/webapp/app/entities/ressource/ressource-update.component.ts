import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRessource, Ressource } from 'app/shared/model/ressource.model';
import { RessourceService } from './ressource.service';
import { IExercice } from 'app/shared/model/exercice.model';
import { ExerciceService } from 'app/entities/exercice';
import { ICours } from 'app/shared/model/cours.model';
import { CoursService } from 'app/entities/cours';

@Component({
  selector: 'jhi-ressource-update',
  templateUrl: './ressource-update.component.html'
})
export class RessourceUpdateComponent implements OnInit {
  ressource: IRessource;
  isSaving: boolean;

  exercices: IExercice[];

  cours: ICours[];

  editForm = this.fb.group({
    id: [],
    titreRessource: [null, [Validators.required]],
    cheminRessource: [null, [Validators.required]],
    typeRessource: [],
    exerciceId: [],
    coursId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ressourceService: RessourceService,
    protected exerciceService: ExerciceService,
    protected coursService: CoursService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ressource }) => {
      this.updateForm(ressource);
      this.ressource = ressource;
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

  updateForm(ressource: IRessource) {
    this.editForm.patchValue({
      id: ressource.id,
      titreRessource: ressource.titreRessource,
      cheminRessource: ressource.cheminRessource,
      typeRessource: ressource.typeRessource,
      exerciceId: ressource.exerciceId,
      coursId: ressource.coursId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ressource = this.createFromForm();
    if (ressource.id !== undefined) {
      this.subscribeToSaveResponse(this.ressourceService.update(ressource));
    } else {
      this.subscribeToSaveResponse(this.ressourceService.create(ressource));
    }
  }

  private createFromForm(): IRessource {
    const entity = {
      ...new Ressource(),
      id: this.editForm.get(['id']).value,
      titreRessource: this.editForm.get(['titreRessource']).value,
      cheminRessource: this.editForm.get(['cheminRessource']).value,
      typeRessource: this.editForm.get(['typeRessource']).value,
      exerciceId: this.editForm.get(['exerciceId']).value,
      coursId: this.editForm.get(['coursId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRessource>>) {
    result.subscribe((res: HttpResponse<IRessource>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

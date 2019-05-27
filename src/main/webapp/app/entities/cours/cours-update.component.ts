import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICours, Cours } from 'app/shared/model/cours.model';
import { CoursService } from './cours.service';
import { IParcour } from 'app/shared/model/parcour.model';
import { ParcourService } from 'app/entities/parcour';

@Component({
  selector: 'jhi-cours-update',
  templateUrl: './cours-update.component.html'
})
export class CoursUpdateComponent implements OnInit {
  cours: ICours;
  isSaving: boolean;

  parcours: IParcour[];

  editForm = this.fb.group({
    id: [],
    titreCours: [null, [Validators.required]],
    contenuCours: [null, [Validators.required]],
    parcourId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected coursService: CoursService,
    protected parcourService: ParcourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cours }) => {
      this.updateForm(cours);
      this.cours = cours;
    });
    this.parcourService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IParcour[]>) => mayBeOk.ok),
        map((response: HttpResponse<IParcour[]>) => response.body)
      )
      .subscribe((res: IParcour[]) => (this.parcours = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cours: ICours) {
    this.editForm.patchValue({
      id: cours.id,
      titreCours: cours.titreCours,
      contenuCours: cours.contenuCours,
      parcourId: cours.parcourId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cours = this.createFromForm();
    if (cours.id !== undefined) {
      this.subscribeToSaveResponse(this.coursService.update(cours));
    } else {
      this.subscribeToSaveResponse(this.coursService.create(cours));
    }
  }

  private createFromForm(): ICours {
    const entity = {
      ...new Cours(),
      id: this.editForm.get(['id']).value,
      titreCours: this.editForm.get(['titreCours']).value,
      contenuCours: this.editForm.get(['contenuCours']).value,
      parcourId: this.editForm.get(['parcourId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICours>>) {
    result.subscribe((res: HttpResponse<ICours>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

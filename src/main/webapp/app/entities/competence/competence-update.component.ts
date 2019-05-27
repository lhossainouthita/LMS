import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompetence, Competence } from 'app/shared/model/competence.model';
import { CompetenceService } from './competence.service';
import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique';

@Component({
  selector: 'jhi-competence-update',
  templateUrl: './competence-update.component.html'
})
export class CompetenceUpdateComponent implements OnInit {
  competence: ICompetence;
  isSaving: boolean;

  modulepedagogiques: IModulePedagogique[];

  editForm = this.fb.group({
    id: [],
    codeCompetence: [null, [Validators.required]],
    intituleCompetence: [null, [Validators.required]],
    descriptionCompetence: [],
    modulePedagogiqueId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected competenceService: CompetenceService,
    protected modulePedagogiqueService: ModulePedagogiqueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ competence }) => {
      this.updateForm(competence);
      this.competence = competence;
    });
    this.modulePedagogiqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModulePedagogique[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModulePedagogique[]>) => response.body)
      )
      .subscribe((res: IModulePedagogique[]) => (this.modulepedagogiques = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(competence: ICompetence) {
    this.editForm.patchValue({
      id: competence.id,
      codeCompetence: competence.codeCompetence,
      intituleCompetence: competence.intituleCompetence,
      descriptionCompetence: competence.descriptionCompetence,
      modulePedagogiqueId: competence.modulePedagogiqueId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const competence = this.createFromForm();
    if (competence.id !== undefined) {
      this.subscribeToSaveResponse(this.competenceService.update(competence));
    } else {
      this.subscribeToSaveResponse(this.competenceService.create(competence));
    }
  }

  private createFromForm(): ICompetence {
    const entity = {
      ...new Competence(),
      id: this.editForm.get(['id']).value,
      codeCompetence: this.editForm.get(['codeCompetence']).value,
      intituleCompetence: this.editForm.get(['intituleCompetence']).value,
      descriptionCompetence: this.editForm.get(['descriptionCompetence']).value,
      modulePedagogiqueId: this.editForm.get(['modulePedagogiqueId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetence>>) {
    result.subscribe((res: HttpResponse<ICompetence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackModulePedagogiqueById(index: number, item: IModulePedagogique) {
    return item.id;
  }
}

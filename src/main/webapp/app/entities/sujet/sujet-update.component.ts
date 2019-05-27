import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISujet, Sujet } from 'app/shared/model/sujet.model';
import { SujetService } from './sujet.service';
import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique';
import { ICompetence } from 'app/shared/model/competence.model';
import { CompetenceService } from 'app/entities/competence';

@Component({
  selector: 'jhi-sujet-update',
  templateUrl: './sujet-update.component.html'
})
export class SujetUpdateComponent implements OnInit {
  sujet: ISujet;
  isSaving: boolean;

  modulepedagogiques: IModulePedagogique[];

  competences: ICompetence[];

  editForm = this.fb.group({
    id: [],
    intituleSujet: [null, [Validators.required]],
    descriptionSujet: [],
    modulePedagogiqueId: [],
    competenceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sujetService: SujetService,
    protected modulePedagogiqueService: ModulePedagogiqueService,
    protected competenceService: CompetenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sujet }) => {
      this.updateForm(sujet);
      this.sujet = sujet;
    });
    this.modulePedagogiqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModulePedagogique[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModulePedagogique[]>) => response.body)
      )
      .subscribe((res: IModulePedagogique[]) => (this.modulepedagogiques = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.competenceService
      .query({ filter: 'sujet-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICompetence[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompetence[]>) => response.body)
      )
      .subscribe(
        (res: ICompetence[]) => {
          if (!this.sujet.competenceId) {
            this.competences = res;
          } else {
            this.competenceService
              .find(this.sujet.competenceId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICompetence>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICompetence>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICompetence) => (this.competences = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(sujet: ISujet) {
    this.editForm.patchValue({
      id: sujet.id,
      intituleSujet: sujet.intituleSujet,
      descriptionSujet: sujet.descriptionSujet,
      modulePedagogiqueId: sujet.modulePedagogiqueId,
      competenceId: sujet.competenceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sujet = this.createFromForm();
    if (sujet.id !== undefined) {
      this.subscribeToSaveResponse(this.sujetService.update(sujet));
    } else {
      this.subscribeToSaveResponse(this.sujetService.create(sujet));
    }
  }

  private createFromForm(): ISujet {
    const entity = {
      ...new Sujet(),
      id: this.editForm.get(['id']).value,
      intituleSujet: this.editForm.get(['intituleSujet']).value,
      descriptionSujet: this.editForm.get(['descriptionSujet']).value,
      modulePedagogiqueId: this.editForm.get(['modulePedagogiqueId']).value,
      competenceId: this.editForm.get(['competenceId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISujet>>) {
    result.subscribe((res: HttpResponse<ISujet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCompetenceById(index: number, item: ICompetence) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IParcour, Parcour } from 'app/shared/model/parcour.model';
import { ParcourService } from './parcour.service';
import { IUser, UserService } from 'app/core';
import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique';

@Component({
  selector: 'jhi-parcour-update',
  templateUrl: './parcour-update.component.html'
})
export class ParcourUpdateComponent implements OnInit {
  parcour: IParcour;
  isSaving: boolean;

  users: IUser[];

  modulepedagogiques: IModulePedagogique[];

  editForm = this.fb.group({
    id: [],
    titreParcour: [null, [Validators.required]],
    niveauParcour: [null, [Validators.required]],
    tuteurId: [],
    modulePedagogiqueId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected parcourService: ParcourService,
    protected userService: UserService,
    protected modulePedagogiqueService: ModulePedagogiqueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ parcour }) => {
      this.updateForm(parcour);
      this.parcour = parcour;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.modulePedagogiqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModulePedagogique[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModulePedagogique[]>) => response.body)
      )
      .subscribe((res: IModulePedagogique[]) => (this.modulepedagogiques = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(parcour: IParcour) {
    this.editForm.patchValue({
      id: parcour.id,
      titreParcour: parcour.titreParcour,
      niveauParcour: parcour.niveauParcour,
      tuteurId: parcour.tuteurId,
      modulePedagogiqueId: parcour.modulePedagogiqueId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const parcour = this.createFromForm();
    if (parcour.id !== undefined) {
      this.subscribeToSaveResponse(this.parcourService.update(parcour));
    } else {
      this.subscribeToSaveResponse(this.parcourService.create(parcour));
    }
  }

  private createFromForm(): IParcour {
    const entity = {
      ...new Parcour(),
      id: this.editForm.get(['id']).value,
      titreParcour: this.editForm.get(['titreParcour']).value,
      niveauParcour: this.editForm.get(['niveauParcour']).value,
      tuteurId: this.editForm.get(['tuteurId']).value,
      modulePedagogiqueId: this.editForm.get(['modulePedagogiqueId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParcour>>) {
    result.subscribe((res: HttpResponse<IParcour>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackModulePedagogiqueById(index: number, item: IModulePedagogique) {
    return item.id;
  }
}

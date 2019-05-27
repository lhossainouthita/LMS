import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDevoir, Devoir } from 'app/shared/model/devoir.model';
import { DevoirService } from './devoir.service';
import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from 'app/entities/module-pedagogique';

@Component({
  selector: 'jhi-devoir-update',
  templateUrl: './devoir-update.component.html'
})
export class DevoirUpdateComponent implements OnInit {
  devoir: IDevoir;
  isSaving: boolean;

  modulepedagogiques: IModulePedagogique[];

  editForm = this.fb.group({
    id: [],
    titreDevoir: [null, [Validators.required]],
    cheminDevoir: [null, [Validators.required]],
    modulePedagogiqueId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected devoirService: DevoirService,
    protected modulePedagogiqueService: ModulePedagogiqueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ devoir }) => {
      this.updateForm(devoir);
      this.devoir = devoir;
    });
    this.modulePedagogiqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModulePedagogique[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModulePedagogique[]>) => response.body)
      )
      .subscribe((res: IModulePedagogique[]) => (this.modulepedagogiques = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(devoir: IDevoir) {
    this.editForm.patchValue({
      id: devoir.id,
      titreDevoir: devoir.titreDevoir,
      cheminDevoir: devoir.cheminDevoir,
      modulePedagogiqueId: devoir.modulePedagogiqueId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const devoir = this.createFromForm();
    if (devoir.id !== undefined) {
      this.subscribeToSaveResponse(this.devoirService.update(devoir));
    } else {
      this.subscribeToSaveResponse(this.devoirService.create(devoir));
    }
  }

  private createFromForm(): IDevoir {
    const entity = {
      ...new Devoir(),
      id: this.editForm.get(['id']).value,
      titreDevoir: this.editForm.get(['titreDevoir']).value,
      cheminDevoir: this.editForm.get(['cheminDevoir']).value,
      modulePedagogiqueId: this.editForm.get(['modulePedagogiqueId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevoir>>) {
    result.subscribe((res: HttpResponse<IDevoir>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

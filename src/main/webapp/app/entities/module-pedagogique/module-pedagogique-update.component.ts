import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModulePedagogique, ModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from './module-pedagogique.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-module-pedagogique-update',
  templateUrl: './module-pedagogique-update.component.html'
})
export class ModulePedagogiqueUpdateComponent implements OnInit {
  modulePedagogique: IModulePedagogique;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    codeModule: [null, [Validators.required]],
    intituleModule: [null, [Validators.required]],
    descriptionModule: [],
    adminId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected modulePedagogiqueService: ModulePedagogiqueService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ modulePedagogique }) => {
      this.updateForm(modulePedagogique);
      this.modulePedagogique = modulePedagogique;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(modulePedagogique: IModulePedagogique) {
    this.editForm.patchValue({
      id: modulePedagogique.id,
      codeModule: modulePedagogique.codeModule,
      intituleModule: modulePedagogique.intituleModule,
      descriptionModule: modulePedagogique.descriptionModule,
      adminId: modulePedagogique.adminId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const modulePedagogique = this.createFromForm();
    if (modulePedagogique.id !== undefined) {
      this.subscribeToSaveResponse(this.modulePedagogiqueService.update(modulePedagogique));
    } else {
      this.subscribeToSaveResponse(this.modulePedagogiqueService.create(modulePedagogique));
    }
  }

  private createFromForm(): IModulePedagogique {
    const entity = {
      ...new ModulePedagogique(),
      id: this.editForm.get(['id']).value,
      codeModule: this.editForm.get(['codeModule']).value,
      intituleModule: this.editForm.get(['intituleModule']).value,
      descriptionModule: this.editForm.get(['descriptionModule']).value,
      adminId: this.editForm.get(['adminId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModulePedagogique>>) {
    result.subscribe((res: HttpResponse<IModulePedagogique>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}

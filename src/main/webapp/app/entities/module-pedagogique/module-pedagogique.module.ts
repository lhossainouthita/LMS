import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  ModulePedagogiqueComponent,
  ModulePedagogiqueDetailComponent,
  ModulePedagogiqueUpdateComponent,
  ModulePedagogiqueDeletePopupComponent,
  ModulePedagogiqueDeleteDialogComponent,
  modulePedagogiqueRoute,
  modulePedagogiquePopupRoute
} from './';

const ENTITY_STATES = [...modulePedagogiqueRoute, ...modulePedagogiquePopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ModulePedagogiqueComponent,
    ModulePedagogiqueDetailComponent,
    ModulePedagogiqueUpdateComponent,
    ModulePedagogiqueDeleteDialogComponent,
    ModulePedagogiqueDeletePopupComponent
  ],
  entryComponents: [
    ModulePedagogiqueComponent,
    ModulePedagogiqueUpdateComponent,
    ModulePedagogiqueDeleteDialogComponent,
    ModulePedagogiqueDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsModulePedagogiqueModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  ParcourComponent,
  ParcourDetailComponent,
  ParcourUpdateComponent,
  ParcourDeletePopupComponent,
  ParcourDeleteDialogComponent,
  parcourRoute,
  parcourPopupRoute
} from './';

const ENTITY_STATES = [...parcourRoute, ...parcourPopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ParcourComponent,
    ParcourDetailComponent,
    ParcourUpdateComponent,
    ParcourDeleteDialogComponent,
    ParcourDeletePopupComponent
  ],
  entryComponents: [ParcourComponent, ParcourUpdateComponent, ParcourDeleteDialogComponent, ParcourDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsParcourModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

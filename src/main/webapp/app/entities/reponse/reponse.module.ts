import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  ReponseComponent,
  ReponseDetailComponent,
  ReponseUpdateComponent,
  ReponseDeletePopupComponent,
  ReponseDeleteDialogComponent,
  reponseRoute,
  reponsePopupRoute
} from './';

const ENTITY_STATES = [...reponseRoute, ...reponsePopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReponseComponent,
    ReponseDetailComponent,
    ReponseUpdateComponent,
    ReponseDeleteDialogComponent,
    ReponseDeletePopupComponent
  ],
  entryComponents: [ReponseComponent, ReponseUpdateComponent, ReponseDeleteDialogComponent, ReponseDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsReponseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

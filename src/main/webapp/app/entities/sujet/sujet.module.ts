import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  SujetComponent,
  SujetDetailComponent,
  SujetUpdateComponent,
  SujetDeletePopupComponent,
  SujetDeleteDialogComponent,
  sujetRoute,
  sujetPopupRoute
} from './';

const ENTITY_STATES = [...sujetRoute, ...sujetPopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SujetComponent, SujetDetailComponent, SujetUpdateComponent, SujetDeleteDialogComponent, SujetDeletePopupComponent],
  entryComponents: [SujetComponent, SujetUpdateComponent, SujetDeleteDialogComponent, SujetDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsSujetModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

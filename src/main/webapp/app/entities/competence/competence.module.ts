import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  CompetenceComponent,
  CompetenceDetailComponent,
  CompetenceUpdateComponent,
  CompetenceDeletePopupComponent,
  CompetenceDeleteDialogComponent,
  competenceRoute,
  competencePopupRoute
} from './';

const ENTITY_STATES = [...competenceRoute, ...competencePopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompetenceComponent,
    CompetenceDetailComponent,
    CompetenceUpdateComponent,
    CompetenceDeleteDialogComponent,
    CompetenceDeletePopupComponent
  ],
  entryComponents: [CompetenceComponent, CompetenceUpdateComponent, CompetenceDeleteDialogComponent, CompetenceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsCompetenceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

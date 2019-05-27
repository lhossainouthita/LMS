import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  SituationComponent,
  SituationDetailComponent,
  SituationUpdateComponent,
  SituationDeletePopupComponent,
  SituationDeleteDialogComponent,
  situationRoute,
  situationPopupRoute
} from './';

const ENTITY_STATES = [...situationRoute, ...situationPopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SituationComponent,
    SituationDetailComponent,
    SituationUpdateComponent,
    SituationDeleteDialogComponent,
    SituationDeletePopupComponent
  ],
  entryComponents: [SituationComponent, SituationUpdateComponent, SituationDeleteDialogComponent, SituationDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsSituationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

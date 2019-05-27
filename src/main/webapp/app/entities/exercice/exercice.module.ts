import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  ExerciceComponent,
  ExerciceDetailComponent,
  ExerciceUpdateComponent,
  ExerciceDeletePopupComponent,
  ExerciceDeleteDialogComponent,
  exerciceRoute,
  exercicePopupRoute
} from './';

const ENTITY_STATES = [...exerciceRoute, ...exercicePopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExerciceComponent,
    ExerciceDetailComponent,
    ExerciceUpdateComponent,
    ExerciceDeleteDialogComponent,
    ExerciceDeletePopupComponent
  ],
  entryComponents: [ExerciceComponent, ExerciceUpdateComponent, ExerciceDeleteDialogComponent, ExerciceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsExerciceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  PeriodeComponent,
  PeriodeDetailComponent,
  PeriodeUpdateComponent,
  PeriodeDeletePopupComponent,
  PeriodeDeleteDialogComponent,
  periodeRoute,
  periodePopupRoute
} from './';

const ENTITY_STATES = [...periodeRoute, ...periodePopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PeriodeComponent,
    PeriodeDetailComponent,
    PeriodeUpdateComponent,
    PeriodeDeleteDialogComponent,
    PeriodeDeletePopupComponent
  ],
  entryComponents: [PeriodeComponent, PeriodeUpdateComponent, PeriodeDeleteDialogComponent, PeriodeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsPeriodeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  DevoirComponent,
  DevoirDetailComponent,
  DevoirUpdateComponent,
  DevoirDeletePopupComponent,
  DevoirDeleteDialogComponent,
  devoirRoute,
  devoirPopupRoute
} from './';

const ENTITY_STATES = [...devoirRoute, ...devoirPopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DevoirComponent, DevoirDetailComponent, DevoirUpdateComponent, DevoirDeleteDialogComponent, DevoirDeletePopupComponent],
  entryComponents: [DevoirComponent, DevoirUpdateComponent, DevoirDeleteDialogComponent, DevoirDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsDevoirModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

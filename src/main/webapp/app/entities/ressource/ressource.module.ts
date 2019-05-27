import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LmsSharedModule } from 'app/shared';
import {
  RessourceComponent,
  RessourceDetailComponent,
  RessourceUpdateComponent,
  RessourceDeletePopupComponent,
  RessourceDeleteDialogComponent,
  ressourceRoute,
  ressourcePopupRoute
} from './';

const ENTITY_STATES = [...ressourceRoute, ...ressourcePopupRoute];

@NgModule({
  imports: [LmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RessourceComponent,
    RessourceDetailComponent,
    RessourceUpdateComponent,
    RessourceDeleteDialogComponent,
    RessourceDeletePopupComponent
  ],
  entryComponents: [RessourceComponent, RessourceUpdateComponent, RessourceDeleteDialogComponent, RessourceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsRessourceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

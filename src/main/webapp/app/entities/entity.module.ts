import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'module-pedagogique',
        loadChildren: './module-pedagogique/module-pedagogique.module#LmsModulePedagogiqueModule'
      },
      {
        path: 'parcour',
        loadChildren: './parcour/parcour.module#LmsParcourModule'
      },
      {
        path: 'exercice',
        loadChildren: './exercice/exercice.module#LmsExerciceModule'
      },
      {
        path: 'cours',
        loadChildren: './cours/cours.module#LmsCoursModule'
      },
      {
        path: 'module-pedagogique',
        loadChildren: './module-pedagogique/module-pedagogique.module#LmsModulePedagogiqueModule'
      },
      {
        path: 'parcour',
        loadChildren: './parcour/parcour.module#LmsParcourModule'
      },
      {
        path: 'exercice',
        loadChildren: './exercice/exercice.module#LmsExerciceModule'
      },
      {
        path: 'cours',
        loadChildren: './cours/cours.module#LmsCoursModule'
      },
      {
        path: 'module-pedagogique',
        loadChildren: './module-pedagogique/module-pedagogique.module#LmsModulePedagogiqueModule'
      },
      {
        path: 'parcour',
        loadChildren: './parcour/parcour.module#LmsParcourModule'
      },
      {
        path: 'parcour',
        loadChildren: './parcour/parcour.module#LmsParcourModule'
      },
      {
        path: 'exercice',
        loadChildren: './exercice/exercice.module#LmsExerciceModule'
      },
      {
        path: 'cours',
        loadChildren: './cours/cours.module#LmsCoursModule'
      },
      {
        path: 'module-pedagogique',
        loadChildren: './module-pedagogique/module-pedagogique.module#LmsModulePedagogiqueModule'
      },
      {
        path: 'module-pedagogique',
        loadChildren: './module-pedagogique/module-pedagogique.module#LmsModulePedagogiqueModule'
      },
      {
        path: 'devoir',
        loadChildren: './devoir/devoir.module#LmsDevoirModule'
      },
      {
        path: 'sujet',
        loadChildren: './sujet/sujet.module#LmsSujetModule'
      },
      {
        path: 'periode',
        loadChildren: './periode/periode.module#LmsPeriodeModule'
      },
      {
        path: 'competence',
        loadChildren: './competence/competence.module#LmsCompetenceModule'
      },
      {
        path: 'parcour',
        loadChildren: './parcour/parcour.module#LmsParcourModule'
      },
      {
        path: 'exercice',
        loadChildren: './exercice/exercice.module#LmsExerciceModule'
      },
      {
        path: 'cours',
        loadChildren: './cours/cours.module#LmsCoursModule'
      },
      {
        path: 'situation',
        loadChildren: './situation/situation.module#LmsSituationModule'
      },
      {
        path: 'question',
        loadChildren: './question/question.module#LmsQuestionModule'
      },
      {
        path: 'reponse',
        loadChildren: './reponse/reponse.module#LmsReponseModule'
      },
      {
        path: 'ressource',
        loadChildren: './ressource/ressource.module#LmsRessourceModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsEntityModule {}

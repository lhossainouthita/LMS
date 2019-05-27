import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Situation } from 'app/shared/model/situation.model';
import { SituationService } from './situation.service';
import { SituationComponent } from './situation.component';
import { SituationDetailComponent } from './situation-detail.component';
import { SituationUpdateComponent } from './situation-update.component';
import { SituationDeletePopupComponent } from './situation-delete-dialog.component';
import { ISituation } from 'app/shared/model/situation.model';

@Injectable({ providedIn: 'root' })
export class SituationResolve implements Resolve<ISituation> {
  constructor(private service: SituationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISituation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Situation>) => response.ok),
        map((situation: HttpResponse<Situation>) => situation.body)
      );
    }
    return of(new Situation());
  }
}

export const situationRoute: Routes = [
  {
    path: '',
    component: SituationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.situation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SituationDetailComponent,
    resolve: {
      situation: SituationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.situation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SituationUpdateComponent,
    resolve: {
      situation: SituationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.situation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SituationUpdateComponent,
    resolve: {
      situation: SituationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.situation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const situationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SituationDeletePopupComponent,
    resolve: {
      situation: SituationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.situation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

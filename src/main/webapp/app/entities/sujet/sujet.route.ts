import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sujet } from 'app/shared/model/sujet.model';
import { SujetService } from './sujet.service';
import { SujetComponent } from './sujet.component';
import { SujetDetailComponent } from './sujet-detail.component';
import { SujetUpdateComponent } from './sujet-update.component';
import { SujetDeletePopupComponent } from './sujet-delete-dialog.component';
import { ISujet } from 'app/shared/model/sujet.model';

@Injectable({ providedIn: 'root' })
export class SujetResolve implements Resolve<ISujet> {
  constructor(private service: SujetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISujet> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Sujet>) => response.ok),
        map((sujet: HttpResponse<Sujet>) => sujet.body)
      );
    }
    return of(new Sujet());
  }
}

export const sujetRoute: Routes = [
  {
    path: '',
    component: SujetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.sujet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SujetDetailComponent,
    resolve: {
      sujet: SujetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.sujet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SujetUpdateComponent,
    resolve: {
      sujet: SujetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.sujet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SujetUpdateComponent,
    resolve: {
      sujet: SujetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.sujet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sujetPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SujetDeletePopupComponent,
    resolve: {
      sujet: SujetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.sujet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

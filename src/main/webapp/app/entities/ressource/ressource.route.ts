import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ressource } from 'app/shared/model/ressource.model';
import { RessourceService } from './ressource.service';
import { RessourceComponent } from './ressource.component';
import { RessourceDetailComponent } from './ressource-detail.component';
import { RessourceUpdateComponent } from './ressource-update.component';
import { RessourceDeletePopupComponent } from './ressource-delete-dialog.component';
import { IRessource } from 'app/shared/model/ressource.model';

@Injectable({ providedIn: 'root' })
export class RessourceResolve implements Resolve<IRessource> {
  constructor(private service: RessourceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRessource> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Ressource>) => response.ok),
        map((ressource: HttpResponse<Ressource>) => ressource.body)
      );
    }
    return of(new Ressource());
  }
}

export const ressourceRoute: Routes = [
  {
    path: '',
    component: RessourceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.ressource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RessourceDetailComponent,
    resolve: {
      ressource: RessourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.ressource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RessourceUpdateComponent,
    resolve: {
      ressource: RessourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.ressource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RessourceUpdateComponent,
    resolve: {
      ressource: RessourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.ressource.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ressourcePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RessourceDeletePopupComponent,
    resolve: {
      ressource: RessourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.ressource.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

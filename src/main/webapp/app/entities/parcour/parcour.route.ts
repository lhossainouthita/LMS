import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Parcour } from 'app/shared/model/parcour.model';
import { ParcourService } from './parcour.service';
import { ParcourComponent } from './parcour.component';
import { ParcourDetailComponent } from './parcour-detail.component';
import { ParcourUpdateComponent } from './parcour-update.component';
import { ParcourDeletePopupComponent } from './parcour-delete-dialog.component';
import { IParcour } from 'app/shared/model/parcour.model';

@Injectable({ providedIn: 'root' })
export class ParcourResolve implements Resolve<IParcour> {
  constructor(private service: ParcourService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParcour> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Parcour>) => response.ok),
        map((parcour: HttpResponse<Parcour>) => parcour.body)
      );
    }
    return of(new Parcour());
  }
}

export const parcourRoute: Routes = [
  {
    path: '',
    component: ParcourComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.parcour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParcourDetailComponent,
    resolve: {
      parcour: ParcourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.parcour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParcourUpdateComponent,
    resolve: {
      parcour: ParcourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.parcour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParcourUpdateComponent,
    resolve: {
      parcour: ParcourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.parcour.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const parcourPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParcourDeletePopupComponent,
    resolve: {
      parcour: ParcourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.parcour.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

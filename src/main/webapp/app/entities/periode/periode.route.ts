import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Periode } from 'app/shared/model/periode.model';
import { PeriodeService } from './periode.service';
import { PeriodeComponent } from './periode.component';
import { PeriodeDetailComponent } from './periode-detail.component';
import { PeriodeUpdateComponent } from './periode-update.component';
import { PeriodeDeletePopupComponent } from './periode-delete-dialog.component';
import { IPeriode } from 'app/shared/model/periode.model';

@Injectable({ providedIn: 'root' })
export class PeriodeResolve implements Resolve<IPeriode> {
  constructor(private service: PeriodeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPeriode> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Periode>) => response.ok),
        map((periode: HttpResponse<Periode>) => periode.body)
      );
    }
    return of(new Periode());
  }
}

export const periodeRoute: Routes = [
  {
    path: '',
    component: PeriodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.periode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PeriodeDetailComponent,
    resolve: {
      periode: PeriodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.periode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PeriodeUpdateComponent,
    resolve: {
      periode: PeriodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.periode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PeriodeUpdateComponent,
    resolve: {
      periode: PeriodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.periode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const periodePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PeriodeDeletePopupComponent,
    resolve: {
      periode: PeriodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.periode.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

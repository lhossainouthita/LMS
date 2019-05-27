import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Devoir } from 'app/shared/model/devoir.model';
import { DevoirService } from './devoir.service';
import { DevoirComponent } from './devoir.component';
import { DevoirDetailComponent } from './devoir-detail.component';
import { DevoirUpdateComponent } from './devoir-update.component';
import { DevoirDeletePopupComponent } from './devoir-delete-dialog.component';
import { IDevoir } from 'app/shared/model/devoir.model';

@Injectable({ providedIn: 'root' })
export class DevoirResolve implements Resolve<IDevoir> {
  constructor(private service: DevoirService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDevoir> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Devoir>) => response.ok),
        map((devoir: HttpResponse<Devoir>) => devoir.body)
      );
    }
    return of(new Devoir());
  }
}

export const devoirRoute: Routes = [
  {
    path: '',
    component: DevoirComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.devoir.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DevoirDetailComponent,
    resolve: {
      devoir: DevoirResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.devoir.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DevoirUpdateComponent,
    resolve: {
      devoir: DevoirResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.devoir.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DevoirUpdateComponent,
    resolve: {
      devoir: DevoirResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.devoir.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const devoirPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DevoirDeletePopupComponent,
    resolve: {
      devoir: DevoirResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lmsApp.devoir.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ModulePedagogique } from 'app/shared/model/module-pedagogique.model';
import { ModulePedagogiqueService } from './module-pedagogique.service';
import { ModulePedagogiqueComponent } from './module-pedagogique.component';
import { ModulePedagogiqueDetailComponent } from './module-pedagogique-detail.component';
import { ModulePedagogiqueUpdateComponent } from './module-pedagogique-update.component';
import { ModulePedagogiqueDeletePopupComponent } from './module-pedagogique-delete-dialog.component';
import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';

@Injectable({ providedIn: 'root' })
export class ModulePedagogiqueResolve implements Resolve<IModulePedagogique> {
  constructor(private service: ModulePedagogiqueService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModulePedagogique> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ModulePedagogique>) => response.ok),
        map((modulePedagogique: HttpResponse<ModulePedagogique>) => modulePedagogique.body)
      );
    }
    return of(new ModulePedagogique());
  }
}

export const modulePedagogiqueRoute: Routes = [
  {
    path: '',
    component: ModulePedagogiqueComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'lmsApp.modulePedagogique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModulePedagogiqueDetailComponent,
    resolve: {
      modulePedagogique: ModulePedagogiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'lmsApp.modulePedagogique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModulePedagogiqueUpdateComponent,
    resolve: {
      modulePedagogique: ModulePedagogiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'lmsApp.modulePedagogique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModulePedagogiqueUpdateComponent,
    resolve: {
      modulePedagogique: ModulePedagogiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'lmsApp.modulePedagogique.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const modulePedagogiquePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ModulePedagogiqueDeletePopupComponent,
    resolve: {
      modulePedagogique: ModulePedagogiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'lmsApp.modulePedagogique.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

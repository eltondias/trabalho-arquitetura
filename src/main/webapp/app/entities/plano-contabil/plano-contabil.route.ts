import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PlanoContabil } from 'app/shared/model/plano-contabil.model';
import { PlanoContabilService } from './plano-contabil.service';
import { PlanoContabilComponent } from './plano-contabil.component';
import { PlanoContabilDetailComponent } from './plano-contabil-detail.component';
import { PlanoContabilUpdateComponent } from './plano-contabil-update.component';
import { PlanoContabilDeletePopupComponent } from './plano-contabil-delete-dialog.component';
import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';

@Injectable({ providedIn: 'root' })
export class PlanoContabilResolve implements Resolve<IPlanoContabil> {
    constructor(private service: PlanoContabilService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((planoContabil: HttpResponse<PlanoContabil>) => planoContabil.body));
        }
        return of(new PlanoContabil());
    }
}

export const planoContabilRoute: Routes = [
    {
        path: 'plano-contabil',
        component: PlanoContabilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-contabil/:id/view',
        component: PlanoContabilDetailComponent,
        resolve: {
            planoContabil: PlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-contabil/new',
        component: PlanoContabilUpdateComponent,
        resolve: {
            planoContabil: PlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-contabil/:id/edit',
        component: PlanoContabilUpdateComponent,
        resolve: {
            planoContabil: PlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planoContabilPopupRoute: Routes = [
    {
        path: 'plano-contabil/:id/delete',
        component: PlanoContabilDeletePopupComponent,
        resolve: {
            planoContabil: PlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContabils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

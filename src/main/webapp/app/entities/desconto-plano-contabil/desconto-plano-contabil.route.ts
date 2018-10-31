import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from './desconto-plano-contabil.service';
import { DescontoPlanoContabilComponent } from './desconto-plano-contabil.component';
import { DescontoPlanoContabilDetailComponent } from './desconto-plano-contabil-detail.component';
import { DescontoPlanoContabilUpdateComponent } from './desconto-plano-contabil-update.component';
import { DescontoPlanoContabilDeletePopupComponent } from './desconto-plano-contabil-delete-dialog.component';
import { IDescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';

@Injectable({ providedIn: 'root' })
export class DescontoPlanoContabilResolve implements Resolve<IDescontoPlanoContabil> {
    constructor(private service: DescontoPlanoContabilService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((descontoPlanoContabil: HttpResponse<DescontoPlanoContabil>) => descontoPlanoContabil.body));
        }
        return of(new DescontoPlanoContabil());
    }
}

export const descontoPlanoContabilRoute: Routes = [
    {
        path: 'desconto-plano-contabil',
        component: DescontoPlanoContabilComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoPlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'desconto-plano-contabil/:id/view',
        component: DescontoPlanoContabilDetailComponent,
        resolve: {
            descontoPlanoContabil: DescontoPlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoPlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'desconto-plano-contabil/new',
        component: DescontoPlanoContabilUpdateComponent,
        resolve: {
            descontoPlanoContabil: DescontoPlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoPlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'desconto-plano-contabil/:id/edit',
        component: DescontoPlanoContabilUpdateComponent,
        resolve: {
            descontoPlanoContabil: DescontoPlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoPlanoContabils'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const descontoPlanoContabilPopupRoute: Routes = [
    {
        path: 'desconto-plano-contabil/:id/delete',
        component: DescontoPlanoContabilDeletePopupComponent,
        resolve: {
            descontoPlanoContabil: DescontoPlanoContabilResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoPlanoContabils'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

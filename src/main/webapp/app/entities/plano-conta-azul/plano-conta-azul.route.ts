import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';
import { PlanoContaAzulService } from './plano-conta-azul.service';
import { PlanoContaAzulComponent } from './plano-conta-azul.component';
import { PlanoContaAzulDetailComponent } from './plano-conta-azul-detail.component';
import { PlanoContaAzulUpdateComponent } from './plano-conta-azul-update.component';
import { PlanoContaAzulDeletePopupComponent } from './plano-conta-azul-delete-dialog.component';
import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';

@Injectable({ providedIn: 'root' })
export class PlanoContaAzulResolve implements Resolve<IPlanoContaAzul> {
    constructor(private service: PlanoContaAzulService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((planoContaAzul: HttpResponse<PlanoContaAzul>) => planoContaAzul.body));
        }
        return of(new PlanoContaAzul());
    }
}

export const planoContaAzulRoute: Routes = [
    {
        path: 'plano-conta-azul',
        component: PlanoContaAzulComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-conta-azul/:id/view',
        component: PlanoContaAzulDetailComponent,
        resolve: {
            planoContaAzul: PlanoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-conta-azul/new',
        component: PlanoContaAzulUpdateComponent,
        resolve: {
            planoContaAzul: PlanoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-conta-azul/:id/edit',
        component: PlanoContaAzulUpdateComponent,
        resolve: {
            planoContaAzul: PlanoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planoContaAzulPopupRoute: Routes = [
    {
        path: 'plano-conta-azul/:id/delete',
        component: PlanoContaAzulDeletePopupComponent,
        resolve: {
            planoContaAzul: PlanoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoContaAzuls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

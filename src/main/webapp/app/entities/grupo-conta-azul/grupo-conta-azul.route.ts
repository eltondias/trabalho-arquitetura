import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';
import { GrupoContaAzulService } from './grupo-conta-azul.service';
import { GrupoContaAzulComponent } from './grupo-conta-azul.component';
import { GrupoContaAzulDetailComponent } from './grupo-conta-azul-detail.component';
import { GrupoContaAzulUpdateComponent } from './grupo-conta-azul-update.component';
import { GrupoContaAzulDeletePopupComponent } from './grupo-conta-azul-delete-dialog.component';
import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';

@Injectable({ providedIn: 'root' })
export class GrupoContaAzulResolve implements Resolve<IGrupoContaAzul> {
    constructor(private service: GrupoContaAzulService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((grupoContaAzul: HttpResponse<GrupoContaAzul>) => grupoContaAzul.body));
        }
        return of(new GrupoContaAzul());
    }
}

export const grupoContaAzulRoute: Routes = [
    {
        path: 'grupo-conta-azul',
        component: GrupoContaAzulComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grupo-conta-azul/:id/view',
        component: GrupoContaAzulDetailComponent,
        resolve: {
            grupoContaAzul: GrupoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grupo-conta-azul/new',
        component: GrupoContaAzulUpdateComponent,
        resolve: {
            grupoContaAzul: GrupoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grupo-conta-azul/:id/edit',
        component: GrupoContaAzulUpdateComponent,
        resolve: {
            grupoContaAzul: GrupoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupoContaAzuls'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grupoContaAzulPopupRoute: Routes = [
    {
        path: 'grupo-conta-azul/:id/delete',
        component: GrupoContaAzulDeletePopupComponent,
        resolve: {
            grupoContaAzul: GrupoContaAzulResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupoContaAzuls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

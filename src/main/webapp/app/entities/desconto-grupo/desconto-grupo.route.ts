import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DescontoGrupo } from 'app/shared/model/desconto-grupo.model';
import { DescontoGrupoService } from './desconto-grupo.service';
import { DescontoGrupoComponent } from './desconto-grupo.component';
import { DescontoGrupoDetailComponent } from './desconto-grupo-detail.component';
import { DescontoGrupoUpdateComponent } from './desconto-grupo-update.component';
import { DescontoGrupoDeletePopupComponent } from './desconto-grupo-delete-dialog.component';
import { IDescontoGrupo } from 'app/shared/model/desconto-grupo.model';

@Injectable({ providedIn: 'root' })
export class DescontoGrupoResolve implements Resolve<IDescontoGrupo> {
    constructor(private service: DescontoGrupoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((descontoGrupo: HttpResponse<DescontoGrupo>) => descontoGrupo.body));
        }
        return of(new DescontoGrupo());
    }
}

export const descontoGrupoRoute: Routes = [
    {
        path: 'desconto-grupo',
        component: DescontoGrupoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoGrupos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'desconto-grupo/:id/view',
        component: DescontoGrupoDetailComponent,
        resolve: {
            descontoGrupo: DescontoGrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoGrupos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'desconto-grupo/new',
        component: DescontoGrupoUpdateComponent,
        resolve: {
            descontoGrupo: DescontoGrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoGrupos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'desconto-grupo/:id/edit',
        component: DescontoGrupoUpdateComponent,
        resolve: {
            descontoGrupo: DescontoGrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoGrupos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const descontoGrupoPopupRoute: Routes = [
    {
        path: 'desconto-grupo/:id/delete',
        component: DescontoGrupoDeletePopupComponent,
        resolve: {
            descontoGrupo: DescontoGrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DescontoGrupos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

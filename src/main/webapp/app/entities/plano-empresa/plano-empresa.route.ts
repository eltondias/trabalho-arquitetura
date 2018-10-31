import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PlanoEmpresa } from 'app/shared/model/plano-empresa.model';
import { PlanoEmpresaService } from './plano-empresa.service';
import { PlanoEmpresaComponent } from './plano-empresa.component';
import { PlanoEmpresaDetailComponent } from './plano-empresa-detail.component';
import { PlanoEmpresaUpdateComponent } from './plano-empresa-update.component';
import { PlanoEmpresaDeletePopupComponent } from './plano-empresa-delete-dialog.component';
import { IPlanoEmpresa } from 'app/shared/model/plano-empresa.model';

@Injectable({ providedIn: 'root' })
export class PlanoEmpresaResolve implements Resolve<IPlanoEmpresa> {
    constructor(private service: PlanoEmpresaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((planoEmpresa: HttpResponse<PlanoEmpresa>) => planoEmpresa.body));
        }
        return of(new PlanoEmpresa());
    }
}

export const planoEmpresaRoute: Routes = [
    {
        path: 'plano-empresa',
        component: PlanoEmpresaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoEmpresas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-empresa/:id/view',
        component: PlanoEmpresaDetailComponent,
        resolve: {
            planoEmpresa: PlanoEmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoEmpresas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-empresa/new',
        component: PlanoEmpresaUpdateComponent,
        resolve: {
            planoEmpresa: PlanoEmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoEmpresas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plano-empresa/:id/edit',
        component: PlanoEmpresaUpdateComponent,
        resolve: {
            planoEmpresa: PlanoEmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoEmpresas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planoEmpresaPopupRoute: Routes = [
    {
        path: 'plano-empresa/:id/delete',
        component: PlanoEmpresaDeletePopupComponent,
        resolve: {
            planoEmpresa: PlanoEmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanoEmpresas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

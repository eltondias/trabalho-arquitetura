import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { EmpresaComponent } from './empresa.component';
import { EmpresaDetailComponent } from './empresa-detail.component';
import { EmpresaUpdateComponent } from './empresa-update.component';
import { EmpresaDeletePopupComponent } from './empresa-delete-dialog.component';
import { IEmpresa } from 'app/shared/model/empresa.model';

@Injectable({ providedIn: 'root' })
export class EmpresaResolve implements Resolve<IEmpresa> {
    constructor(private service: EmpresaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((empresa: HttpResponse<Empresa>) => empresa.body));
        }
        return of(new Empresa());
    }
}

export const empresaRoute: Routes = [
    {
        path: 'empresa',
        component: EmpresaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Empresas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empresa/:id/view',
        component: EmpresaDetailComponent,
        resolve: {
            empresa: EmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Empresas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empresa/new',
        component: EmpresaUpdateComponent,
        resolve: {
            empresa: EmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Empresas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empresa/:id/edit',
        component: EmpresaUpdateComponent,
        resolve: {
            empresa: EmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Empresas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const empresaPopupRoute: Routes = [
    {
        path: 'empresa/:id/delete',
        component: EmpresaDeletePopupComponent,
        resolve: {
            empresa: EmpresaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Empresas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

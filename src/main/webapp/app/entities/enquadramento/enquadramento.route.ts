import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Enquadramento } from 'app/shared/model/enquadramento.model';
import { EnquadramentoService } from './enquadramento.service';
import { EnquadramentoComponent } from './enquadramento.component';
import { EnquadramentoDetailComponent } from './enquadramento-detail.component';
import { EnquadramentoUpdateComponent } from './enquadramento-update.component';
import { EnquadramentoDeletePopupComponent } from './enquadramento-delete-dialog.component';
import { IEnquadramento } from 'app/shared/model/enquadramento.model';

@Injectable({ providedIn: 'root' })
export class EnquadramentoResolve implements Resolve<IEnquadramento> {
    constructor(private service: EnquadramentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((enquadramento: HttpResponse<Enquadramento>) => enquadramento.body));
        }
        return of(new Enquadramento());
    }
}

export const enquadramentoRoute: Routes = [
    {
        path: 'enquadramento',
        component: EnquadramentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enquadramentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enquadramento/:id/view',
        component: EnquadramentoDetailComponent,
        resolve: {
            enquadramento: EnquadramentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enquadramentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enquadramento/new',
        component: EnquadramentoUpdateComponent,
        resolve: {
            enquadramento: EnquadramentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enquadramentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enquadramento/:id/edit',
        component: EnquadramentoUpdateComponent,
        resolve: {
            enquadramento: EnquadramentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enquadramentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enquadramentoPopupRoute: Routes = [
    {
        path: 'enquadramento/:id/delete',
        component: EnquadramentoDeletePopupComponent,
        resolve: {
            enquadramento: EnquadramentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enquadramentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

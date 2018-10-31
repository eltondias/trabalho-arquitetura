import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ramo } from 'app/shared/model/ramo.model';
import { RamoService } from './ramo.service';
import { RamoComponent } from './ramo.component';
import { RamoDetailComponent } from './ramo-detail.component';
import { RamoUpdateComponent } from './ramo-update.component';
import { RamoDeletePopupComponent } from './ramo-delete-dialog.component';
import { IRamo } from 'app/shared/model/ramo.model';

@Injectable({ providedIn: 'root' })
export class RamoResolve implements Resolve<IRamo> {
    constructor(private service: RamoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ramo: HttpResponse<Ramo>) => ramo.body));
        }
        return of(new Ramo());
    }
}

export const ramoRoute: Routes = [
    {
        path: 'ramo',
        component: RamoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ramos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ramo/:id/view',
        component: RamoDetailComponent,
        resolve: {
            ramo: RamoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ramos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ramo/new',
        component: RamoUpdateComponent,
        resolve: {
            ramo: RamoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ramos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ramo/:id/edit',
        component: RamoUpdateComponent,
        resolve: {
            ramo: RamoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ramos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ramoPopupRoute: Routes = [
    {
        path: 'ramo/:id/delete',
        component: RamoDeletePopupComponent,
        resolve: {
            ramo: RamoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ramos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

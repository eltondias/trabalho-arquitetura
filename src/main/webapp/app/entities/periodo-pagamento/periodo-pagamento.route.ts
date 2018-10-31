import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';
import { PeriodoPagamentoService } from './periodo-pagamento.service';
import { PeriodoPagamentoComponent } from './periodo-pagamento.component';
import { PeriodoPagamentoDetailComponent } from './periodo-pagamento-detail.component';
import { PeriodoPagamentoUpdateComponent } from './periodo-pagamento-update.component';
import { PeriodoPagamentoDeletePopupComponent } from './periodo-pagamento-delete-dialog.component';
import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';

@Injectable({ providedIn: 'root' })
export class PeriodoPagamentoResolve implements Resolve<IPeriodoPagamento> {
    constructor(private service: PeriodoPagamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((periodoPagamento: HttpResponse<PeriodoPagamento>) => periodoPagamento.body));
        }
        return of(new PeriodoPagamento());
    }
}

export const periodoPagamentoRoute: Routes = [
    {
        path: 'periodo-pagamento',
        component: PeriodoPagamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodoPagamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo-pagamento/:id/view',
        component: PeriodoPagamentoDetailComponent,
        resolve: {
            periodoPagamento: PeriodoPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodoPagamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo-pagamento/new',
        component: PeriodoPagamentoUpdateComponent,
        resolve: {
            periodoPagamento: PeriodoPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodoPagamentos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'periodo-pagamento/:id/edit',
        component: PeriodoPagamentoUpdateComponent,
        resolve: {
            periodoPagamento: PeriodoPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodoPagamentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodoPagamentoPopupRoute: Routes = [
    {
        path: 'periodo-pagamento/:id/delete',
        component: PeriodoPagamentoDeletePopupComponent,
        resolve: {
            periodoPagamento: PeriodoPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodoPagamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

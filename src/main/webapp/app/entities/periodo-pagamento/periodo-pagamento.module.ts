import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    PeriodoPagamentoComponent,
    PeriodoPagamentoDetailComponent,
    PeriodoPagamentoUpdateComponent,
    PeriodoPagamentoDeletePopupComponent,
    PeriodoPagamentoDeleteDialogComponent,
    periodoPagamentoRoute,
    periodoPagamentoPopupRoute
} from './';

const ENTITY_STATES = [...periodoPagamentoRoute, ...periodoPagamentoPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PeriodoPagamentoComponent,
        PeriodoPagamentoDetailComponent,
        PeriodoPagamentoUpdateComponent,
        PeriodoPagamentoDeleteDialogComponent,
        PeriodoPagamentoDeletePopupComponent
    ],
    entryComponents: [
        PeriodoPagamentoComponent,
        PeriodoPagamentoUpdateComponent,
        PeriodoPagamentoDeleteDialogComponent,
        PeriodoPagamentoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaPeriodoPagamentoModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    EnquadramentoComponent,
    EnquadramentoDetailComponent,
    EnquadramentoUpdateComponent,
    EnquadramentoDeletePopupComponent,
    EnquadramentoDeleteDialogComponent,
    enquadramentoRoute,
    enquadramentoPopupRoute
} from './';

const ENTITY_STATES = [...enquadramentoRoute, ...enquadramentoPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnquadramentoComponent,
        EnquadramentoDetailComponent,
        EnquadramentoUpdateComponent,
        EnquadramentoDeleteDialogComponent,
        EnquadramentoDeletePopupComponent
    ],
    entryComponents: [
        EnquadramentoComponent,
        EnquadramentoUpdateComponent,
        EnquadramentoDeleteDialogComponent,
        EnquadramentoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaEnquadramentoModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    PlanoContaAzulComponent,
    PlanoContaAzulDetailComponent,
    PlanoContaAzulUpdateComponent,
    PlanoContaAzulDeletePopupComponent,
    PlanoContaAzulDeleteDialogComponent,
    planoContaAzulRoute,
    planoContaAzulPopupRoute
} from './';

const ENTITY_STATES = [...planoContaAzulRoute, ...planoContaAzulPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlanoContaAzulComponent,
        PlanoContaAzulDetailComponent,
        PlanoContaAzulUpdateComponent,
        PlanoContaAzulDeleteDialogComponent,
        PlanoContaAzulDeletePopupComponent
    ],
    entryComponents: [
        PlanoContaAzulComponent,
        PlanoContaAzulUpdateComponent,
        PlanoContaAzulDeleteDialogComponent,
        PlanoContaAzulDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaPlanoContaAzulModule {}

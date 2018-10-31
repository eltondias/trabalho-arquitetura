import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    GrupoContaAzulComponent,
    GrupoContaAzulDetailComponent,
    GrupoContaAzulUpdateComponent,
    GrupoContaAzulDeletePopupComponent,
    GrupoContaAzulDeleteDialogComponent,
    grupoContaAzulRoute,
    grupoContaAzulPopupRoute
} from './';

const ENTITY_STATES = [...grupoContaAzulRoute, ...grupoContaAzulPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GrupoContaAzulComponent,
        GrupoContaAzulDetailComponent,
        GrupoContaAzulUpdateComponent,
        GrupoContaAzulDeleteDialogComponent,
        GrupoContaAzulDeletePopupComponent
    ],
    entryComponents: [
        GrupoContaAzulComponent,
        GrupoContaAzulUpdateComponent,
        GrupoContaAzulDeleteDialogComponent,
        GrupoContaAzulDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaGrupoContaAzulModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    DescontoGrupoComponent,
    DescontoGrupoDetailComponent,
    DescontoGrupoUpdateComponent,
    DescontoGrupoDeletePopupComponent,
    DescontoGrupoDeleteDialogComponent,
    descontoGrupoRoute,
    descontoGrupoPopupRoute
} from './';

const ENTITY_STATES = [...descontoGrupoRoute, ...descontoGrupoPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DescontoGrupoComponent,
        DescontoGrupoDetailComponent,
        DescontoGrupoUpdateComponent,
        DescontoGrupoDeleteDialogComponent,
        DescontoGrupoDeletePopupComponent
    ],
    entryComponents: [
        DescontoGrupoComponent,
        DescontoGrupoUpdateComponent,
        DescontoGrupoDeleteDialogComponent,
        DescontoGrupoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaDescontoGrupoModule {}

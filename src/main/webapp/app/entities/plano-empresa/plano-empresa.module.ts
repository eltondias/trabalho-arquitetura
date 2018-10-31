import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    PlanoEmpresaComponent,
    PlanoEmpresaDetailComponent,
    PlanoEmpresaUpdateComponent,
    PlanoEmpresaDeletePopupComponent,
    PlanoEmpresaDeleteDialogComponent,
    planoEmpresaRoute,
    planoEmpresaPopupRoute
} from './';

const ENTITY_STATES = [...planoEmpresaRoute, ...planoEmpresaPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlanoEmpresaComponent,
        PlanoEmpresaDetailComponent,
        PlanoEmpresaUpdateComponent,
        PlanoEmpresaDeleteDialogComponent,
        PlanoEmpresaDeletePopupComponent
    ],
    entryComponents: [
        PlanoEmpresaComponent,
        PlanoEmpresaUpdateComponent,
        PlanoEmpresaDeleteDialogComponent,
        PlanoEmpresaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaPlanoEmpresaModule {}

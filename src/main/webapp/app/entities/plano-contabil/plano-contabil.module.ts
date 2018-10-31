import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    PlanoContabilComponent,
    PlanoContabilDetailComponent,
    PlanoContabilUpdateComponent,
    PlanoContabilDeletePopupComponent,
    PlanoContabilDeleteDialogComponent,
    planoContabilRoute,
    planoContabilPopupRoute
} from './';

const ENTITY_STATES = [...planoContabilRoute, ...planoContabilPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlanoContabilComponent,
        PlanoContabilDetailComponent,
        PlanoContabilUpdateComponent,
        PlanoContabilDeleteDialogComponent,
        PlanoContabilDeletePopupComponent
    ],
    entryComponents: [
        PlanoContabilComponent,
        PlanoContabilUpdateComponent,
        PlanoContabilDeleteDialogComponent,
        PlanoContabilDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaPlanoContabilModule {}

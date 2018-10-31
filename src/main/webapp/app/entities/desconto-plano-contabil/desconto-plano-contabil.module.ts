import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    DescontoPlanoContabilComponent,
    DescontoPlanoContabilDetailComponent,
    DescontoPlanoContabilUpdateComponent,
    DescontoPlanoContabilDeletePopupComponent,
    DescontoPlanoContabilDeleteDialogComponent,
    descontoPlanoContabilRoute,
    descontoPlanoContabilPopupRoute
} from './';

const ENTITY_STATES = [...descontoPlanoContabilRoute, ...descontoPlanoContabilPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DescontoPlanoContabilComponent,
        DescontoPlanoContabilDetailComponent,
        DescontoPlanoContabilUpdateComponent,
        DescontoPlanoContabilDeleteDialogComponent,
        DescontoPlanoContabilDeletePopupComponent
    ],
    entryComponents: [
        DescontoPlanoContabilComponent,
        DescontoPlanoContabilUpdateComponent,
        DescontoPlanoContabilDeleteDialogComponent,
        DescontoPlanoContabilDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaDescontoPlanoContabilModule {}

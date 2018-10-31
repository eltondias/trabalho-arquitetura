import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrabalhoarquiteturaSharedModule } from 'app/shared';
import {
    RamoComponent,
    RamoDetailComponent,
    RamoUpdateComponent,
    RamoDeletePopupComponent,
    RamoDeleteDialogComponent,
    ramoRoute,
    ramoPopupRoute
} from './';

const ENTITY_STATES = [...ramoRoute, ...ramoPopupRoute];

@NgModule({
    imports: [TrabalhoarquiteturaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RamoComponent, RamoDetailComponent, RamoUpdateComponent, RamoDeleteDialogComponent, RamoDeletePopupComponent],
    entryComponents: [RamoComponent, RamoUpdateComponent, RamoDeleteDialogComponent, RamoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaRamoModule {}

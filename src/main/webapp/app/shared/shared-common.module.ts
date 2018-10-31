import { NgModule } from '@angular/core';

import { TrabalhoarquiteturaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TrabalhoarquiteturaSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TrabalhoarquiteturaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TrabalhoarquiteturaSharedCommonModule {}

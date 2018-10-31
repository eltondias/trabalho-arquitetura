import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TrabalhoarquiteturaEnderecoModule } from './endereco/endereco.module';
import { TrabalhoarquiteturaRamoModule } from './ramo/ramo.module';
import { TrabalhoarquiteturaEnquadramentoModule } from './enquadramento/enquadramento.module';
import { TrabalhoarquiteturaEmpresaModule } from './empresa/empresa.module';
import { TrabalhoarquiteturaPeriodoPagamentoModule } from './periodo-pagamento/periodo-pagamento.module';
import { TrabalhoarquiteturaGrupoContaAzulModule } from './grupo-conta-azul/grupo-conta-azul.module';
import { TrabalhoarquiteturaDescontoGrupoModule } from './desconto-grupo/desconto-grupo.module';
import { TrabalhoarquiteturaDescontoPlanoContabilModule } from './desconto-plano-contabil/desconto-plano-contabil.module';
import { TrabalhoarquiteturaPlanoContabilModule } from './plano-contabil/plano-contabil.module';
import { TrabalhoarquiteturaPlanoEmpresaModule } from './plano-empresa/plano-empresa.module';
import { TrabalhoarquiteturaPlanoContaAzulModule } from './plano-conta-azul/plano-conta-azul.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TrabalhoarquiteturaEnderecoModule,
        TrabalhoarquiteturaRamoModule,
        TrabalhoarquiteturaEnquadramentoModule,
        TrabalhoarquiteturaEmpresaModule,
        TrabalhoarquiteturaPeriodoPagamentoModule,
        TrabalhoarquiteturaGrupoContaAzulModule,
        TrabalhoarquiteturaDescontoGrupoModule,
        TrabalhoarquiteturaDescontoPlanoContabilModule,
        TrabalhoarquiteturaPlanoContabilModule,
        TrabalhoarquiteturaPlanoEmpresaModule,
        TrabalhoarquiteturaPlanoContaAzulModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TrabalhoarquiteturaEntityModule {}

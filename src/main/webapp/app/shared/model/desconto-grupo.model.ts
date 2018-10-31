import { IPeriodoPagamento } from 'app/shared/model//periodo-pagamento.model';
import { IGrupoContaAzul } from 'app/shared/model//grupo-conta-azul.model';

export interface IDescontoGrupo {
    id?: number;
    estado?: boolean;
    periodoPagamento?: IPeriodoPagamento;
    grupoContaAzul?: IGrupoContaAzul;
}

export class DescontoGrupo implements IDescontoGrupo {
    constructor(
        public id?: number,
        public estado?: boolean,
        public periodoPagamento?: IPeriodoPagamento,
        public grupoContaAzul?: IGrupoContaAzul
    ) {
        this.estado = this.estado || false;
    }
}

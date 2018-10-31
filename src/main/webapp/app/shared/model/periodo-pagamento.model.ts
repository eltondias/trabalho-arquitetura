import { IDescontoGrupo } from 'app/shared/model//desconto-grupo.model';
import { IDescontoPlanoContabil } from 'app/shared/model//desconto-plano-contabil.model';

export interface IPeriodoPagamento {
    id?: number;
    periodo?: string;
    descontoGrupos?: IDescontoGrupo[];
    descontoPlanoContabils?: IDescontoPlanoContabil[];
}

export class PeriodoPagamento implements IPeriodoPagamento {
    constructor(
        public id?: number,
        public periodo?: string,
        public descontoGrupos?: IDescontoGrupo[],
        public descontoPlanoContabils?: IDescontoPlanoContabil[]
    ) {}
}

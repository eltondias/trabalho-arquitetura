import { IPeriodoPagamento } from 'app/shared/model//periodo-pagamento.model';
import { IPlanoContabil } from 'app/shared/model//plano-contabil.model';

export interface IDescontoPlanoContabil {
    id?: number;
    estado?: boolean;
    periodoPagamento?: IPeriodoPagamento;
    planoContabil?: IPlanoContabil;
}

export class DescontoPlanoContabil implements IDescontoPlanoContabil {
    constructor(
        public id?: number,
        public estado?: boolean,
        public periodoPagamento?: IPeriodoPagamento,
        public planoContabil?: IPlanoContabil
    ) {
        this.estado = this.estado || false;
    }
}

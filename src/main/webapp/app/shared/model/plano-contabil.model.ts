import { Moment } from 'moment';
import { IPlanoEmpresa } from 'app/shared/model//plano-empresa.model';
import { IDescontoPlanoContabil } from 'app/shared/model//desconto-plano-contabil.model';

export interface IPlanoContabil {
    id?: number;
    funcionarios?: number;
    socios?: number;
    faturamentoMensal?: number;
    dataContratacao?: Moment;
    dataEncerramento?: Moment;
    planoEmpresas?: IPlanoEmpresa[];
    descontoPlanoContabils?: IDescontoPlanoContabil[];
}

export class PlanoContabil implements IPlanoContabil {
    constructor(
        public id?: number,
        public funcionarios?: number,
        public socios?: number,
        public faturamentoMensal?: number,
        public dataContratacao?: Moment,
        public dataEncerramento?: Moment,
        public planoEmpresas?: IPlanoEmpresa[],
        public descontoPlanoContabils?: IDescontoPlanoContabil[]
    ) {}
}

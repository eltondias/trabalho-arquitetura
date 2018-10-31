import { IEmpresa } from 'app/shared/model//empresa.model';
import { IPlanoContabil } from 'app/shared/model//plano-contabil.model';
import { IPlanoContaAzul } from 'app/shared/model//plano-conta-azul.model';

export interface IPlanoEmpresa {
    id?: number;
    funcionarios?: number;
    socios?: number;
    faturamentoMensal?: number;
    dataContratacao?: string;
    dataEncerramento?: string;
    empresa?: IEmpresa;
    planoContabil?: IPlanoContabil;
    planoContaAzul?: IPlanoContaAzul;
}

export class PlanoEmpresa implements IPlanoEmpresa {
    constructor(
        public id?: number,
        public funcionarios?: number,
        public socios?: number,
        public faturamentoMensal?: number,
        public dataContratacao?: string,
        public dataEncerramento?: string,
        public empresa?: IEmpresa,
        public planoContabil?: IPlanoContabil,
        public planoContaAzul?: IPlanoContaAzul
    ) {}
}

import { IGrupoContaAzul } from 'app/shared/model//grupo-conta-azul.model';
import { IPlanoEmpresa } from 'app/shared/model//plano-empresa.model';

export interface IPlanoContaAzul {
    id?: number;
    plano?: number;
    grupoContaAzul?: IGrupoContaAzul;
    planoEmpresas?: IPlanoEmpresa[];
}

export class PlanoContaAzul implements IPlanoContaAzul {
    constructor(
        public id?: number,
        public plano?: number,
        public grupoContaAzul?: IGrupoContaAzul,
        public planoEmpresas?: IPlanoEmpresa[]
    ) {}
}

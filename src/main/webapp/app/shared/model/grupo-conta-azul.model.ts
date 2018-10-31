import { IPlanoContaAzul } from 'app/shared/model//plano-conta-azul.model';
import { IDescontoGrupo } from 'app/shared/model//desconto-grupo.model';

export interface IGrupoContaAzul {
    id?: number;
    estado?: boolean;
    planoContaAzuls?: IPlanoContaAzul[];
    descontoGrupos?: IDescontoGrupo[];
}

export class GrupoContaAzul implements IGrupoContaAzul {
    constructor(
        public id?: number,
        public estado?: boolean,
        public planoContaAzuls?: IPlanoContaAzul[],
        public descontoGrupos?: IDescontoGrupo[]
    ) {
        this.estado = this.estado || false;
    }
}

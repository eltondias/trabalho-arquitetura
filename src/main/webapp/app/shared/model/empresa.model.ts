import { IRamo } from 'app/shared/model//ramo.model';
import { IEnquadramento } from 'app/shared/model//enquadramento.model';
import { IPlanoEmpresa } from 'app/shared/model//plano-empresa.model';
import { IEndereco } from 'app/shared/model//endereco.model';

export interface IEmpresa {
    id?: number;
    nome?: string;
    ramo?: IRamo;
    enquadramento?: IEnquadramento;
    planoEmpresas?: IPlanoEmpresa[];
    enderecos?: IEndereco[];
}

export class Empresa implements IEmpresa {
    constructor(
        public id?: number,
        public nome?: string,
        public ramo?: IRamo,
        public enquadramento?: IEnquadramento,
        public planoEmpresas?: IPlanoEmpresa[],
        public enderecos?: IEndereco[]
    ) {}
}

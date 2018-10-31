import { IEmpresa } from 'app/shared/model//empresa.model';

export interface IEnquadramento {
    id?: number;
    nome?: string;
    limite?: number;
    empresas?: IEmpresa[];
}

export class Enquadramento implements IEnquadramento {
    constructor(public id?: number, public nome?: string, public limite?: number, public empresas?: IEmpresa[]) {}
}

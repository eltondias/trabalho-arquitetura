import { IEmpresa } from 'app/shared/model//empresa.model';

export interface IRamo {
    id?: number;
    nome?: string;
    descricao?: string;
    empresas?: IEmpresa[];
}

export class Ramo implements IRamo {
    constructor(public id?: number, public nome?: string, public descricao?: string, public empresas?: IEmpresa[]) {}
}

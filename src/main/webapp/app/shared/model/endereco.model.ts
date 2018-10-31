import { IEmpresa } from 'app/shared/model//empresa.model';

export interface IEndereco {
    id?: number;
    logradouro?: string;
    empresa?: IEmpresa;
}

export class Endereco implements IEndereco {
    constructor(public id?: number, public logradouro?: string, public empresa?: IEmpresa) {}
}

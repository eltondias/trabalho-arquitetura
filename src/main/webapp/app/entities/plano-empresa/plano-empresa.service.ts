import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanoEmpresa } from 'app/shared/model/plano-empresa.model';

type EntityResponseType = HttpResponse<IPlanoEmpresa>;
type EntityArrayResponseType = HttpResponse<IPlanoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class PlanoEmpresaService {
    public resourceUrl = SERVER_API_URL + 'api/plano-empresas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/plano-empresas';

    constructor(private http: HttpClient) {}

    create(planoEmpresa: IPlanoEmpresa): Observable<EntityResponseType> {
        return this.http.post<IPlanoEmpresa>(this.resourceUrl, planoEmpresa, { observe: 'response' });
    }

    update(planoEmpresa: IPlanoEmpresa): Observable<EntityResponseType> {
        return this.http.put<IPlanoEmpresa>(this.resourceUrl, planoEmpresa, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlanoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanoEmpresa[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

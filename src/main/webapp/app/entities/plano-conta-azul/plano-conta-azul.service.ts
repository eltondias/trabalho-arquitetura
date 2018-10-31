import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';

type EntityResponseType = HttpResponse<IPlanoContaAzul>;
type EntityArrayResponseType = HttpResponse<IPlanoContaAzul[]>;

@Injectable({ providedIn: 'root' })
export class PlanoContaAzulService {
    public resourceUrl = SERVER_API_URL + 'api/plano-conta-azuls';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/plano-conta-azuls';

    constructor(private http: HttpClient) {}

    create(planoContaAzul: IPlanoContaAzul): Observable<EntityResponseType> {
        return this.http.post<IPlanoContaAzul>(this.resourceUrl, planoContaAzul, { observe: 'response' });
    }

    update(planoContaAzul: IPlanoContaAzul): Observable<EntityResponseType> {
        return this.http.put<IPlanoContaAzul>(this.resourceUrl, planoContaAzul, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlanoContaAzul>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanoContaAzul[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanoContaAzul[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

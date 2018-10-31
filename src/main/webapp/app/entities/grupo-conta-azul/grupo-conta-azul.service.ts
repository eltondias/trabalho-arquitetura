import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';

type EntityResponseType = HttpResponse<IGrupoContaAzul>;
type EntityArrayResponseType = HttpResponse<IGrupoContaAzul[]>;

@Injectable({ providedIn: 'root' })
export class GrupoContaAzulService {
    public resourceUrl = SERVER_API_URL + 'api/grupo-conta-azuls';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/grupo-conta-azuls';

    constructor(private http: HttpClient) {}

    create(grupoContaAzul: IGrupoContaAzul): Observable<EntityResponseType> {
        return this.http.post<IGrupoContaAzul>(this.resourceUrl, grupoContaAzul, { observe: 'response' });
    }

    update(grupoContaAzul: IGrupoContaAzul): Observable<EntityResponseType> {
        return this.http.put<IGrupoContaAzul>(this.resourceUrl, grupoContaAzul, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGrupoContaAzul>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGrupoContaAzul[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGrupoContaAzul[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

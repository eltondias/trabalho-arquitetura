import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';

type EntityResponseType = HttpResponse<IDescontoPlanoContabil>;
type EntityArrayResponseType = HttpResponse<IDescontoPlanoContabil[]>;

@Injectable({ providedIn: 'root' })
export class DescontoPlanoContabilService {
    public resourceUrl = SERVER_API_URL + 'api/desconto-plano-contabils';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/desconto-plano-contabils';

    constructor(private http: HttpClient) {}

    create(descontoPlanoContabil: IDescontoPlanoContabil): Observable<EntityResponseType> {
        return this.http.post<IDescontoPlanoContabil>(this.resourceUrl, descontoPlanoContabil, { observe: 'response' });
    }

    update(descontoPlanoContabil: IDescontoPlanoContabil): Observable<EntityResponseType> {
        return this.http.put<IDescontoPlanoContabil>(this.resourceUrl, descontoPlanoContabil, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDescontoPlanoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDescontoPlanoContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDescontoPlanoContabil[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

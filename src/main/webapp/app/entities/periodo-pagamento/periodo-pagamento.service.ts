import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';

type EntityResponseType = HttpResponse<IPeriodoPagamento>;
type EntityArrayResponseType = HttpResponse<IPeriodoPagamento[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoPagamentoService {
    public resourceUrl = SERVER_API_URL + 'api/periodo-pagamentos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/periodo-pagamentos';

    constructor(private http: HttpClient) {}

    create(periodoPagamento: IPeriodoPagamento): Observable<EntityResponseType> {
        return this.http.post<IPeriodoPagamento>(this.resourceUrl, periodoPagamento, { observe: 'response' });
    }

    update(periodoPagamento: IPeriodoPagamento): Observable<EntityResponseType> {
        return this.http.put<IPeriodoPagamento>(this.resourceUrl, periodoPagamento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPeriodoPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPeriodoPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPeriodoPagamento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

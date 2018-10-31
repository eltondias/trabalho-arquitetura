import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRamo } from 'app/shared/model/ramo.model';

type EntityResponseType = HttpResponse<IRamo>;
type EntityArrayResponseType = HttpResponse<IRamo[]>;

@Injectable({ providedIn: 'root' })
export class RamoService {
    public resourceUrl = SERVER_API_URL + 'api/ramos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/ramos';

    constructor(private http: HttpClient) {}

    create(ramo: IRamo): Observable<EntityResponseType> {
        return this.http.post<IRamo>(this.resourceUrl, ramo, { observe: 'response' });
    }

    update(ramo: IRamo): Observable<EntityResponseType> {
        return this.http.put<IRamo>(this.resourceUrl, ramo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRamo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRamo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRamo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

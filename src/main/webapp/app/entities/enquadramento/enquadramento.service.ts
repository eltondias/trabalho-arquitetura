import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnquadramento } from 'app/shared/model/enquadramento.model';

type EntityResponseType = HttpResponse<IEnquadramento>;
type EntityArrayResponseType = HttpResponse<IEnquadramento[]>;

@Injectable({ providedIn: 'root' })
export class EnquadramentoService {
    public resourceUrl = SERVER_API_URL + 'api/enquadramentos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/enquadramentos';

    constructor(private http: HttpClient) {}

    create(enquadramento: IEnquadramento): Observable<EntityResponseType> {
        return this.http.post<IEnquadramento>(this.resourceUrl, enquadramento, { observe: 'response' });
    }

    update(enquadramento: IEnquadramento): Observable<EntityResponseType> {
        return this.http.put<IEnquadramento>(this.resourceUrl, enquadramento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEnquadramento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnquadramento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnquadramento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

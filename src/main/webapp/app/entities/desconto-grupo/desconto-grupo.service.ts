import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDescontoGrupo } from 'app/shared/model/desconto-grupo.model';

type EntityResponseType = HttpResponse<IDescontoGrupo>;
type EntityArrayResponseType = HttpResponse<IDescontoGrupo[]>;

@Injectable({ providedIn: 'root' })
export class DescontoGrupoService {
    public resourceUrl = SERVER_API_URL + 'api/desconto-grupos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/desconto-grupos';

    constructor(private http: HttpClient) {}

    create(descontoGrupo: IDescontoGrupo): Observable<EntityResponseType> {
        return this.http.post<IDescontoGrupo>(this.resourceUrl, descontoGrupo, { observe: 'response' });
    }

    update(descontoGrupo: IDescontoGrupo): Observable<EntityResponseType> {
        return this.http.put<IDescontoGrupo>(this.resourceUrl, descontoGrupo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDescontoGrupo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDescontoGrupo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDescontoGrupo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

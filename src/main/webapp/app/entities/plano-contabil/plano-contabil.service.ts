import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';

type EntityResponseType = HttpResponse<IPlanoContabil>;
type EntityArrayResponseType = HttpResponse<IPlanoContabil[]>;

@Injectable({ providedIn: 'root' })
export class PlanoContabilService {
    public resourceUrl = SERVER_API_URL + 'api/plano-contabils';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/plano-contabils';

    constructor(private http: HttpClient) {}

    create(planoContabil: IPlanoContabil): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(planoContabil);
        return this.http
            .post<IPlanoContabil>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(planoContabil: IPlanoContabil): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(planoContabil);
        return this.http
            .put<IPlanoContabil>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPlanoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPlanoContabil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPlanoContabil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(planoContabil: IPlanoContabil): IPlanoContabil {
        const copy: IPlanoContabil = Object.assign({}, planoContabil, {
            dataContratacao:
                planoContabil.dataContratacao != null && planoContabil.dataContratacao.isValid()
                    ? planoContabil.dataContratacao.toJSON()
                    : null,
            dataEncerramento:
                planoContabil.dataEncerramento != null && planoContabil.dataEncerramento.isValid()
                    ? planoContabil.dataEncerramento.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataContratacao = res.body.dataContratacao != null ? moment(res.body.dataContratacao) : null;
        res.body.dataEncerramento = res.body.dataEncerramento != null ? moment(res.body.dataEncerramento) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((planoContabil: IPlanoContabil) => {
            planoContabil.dataContratacao = planoContabil.dataContratacao != null ? moment(planoContabil.dataContratacao) : null;
            planoContabil.dataEncerramento = planoContabil.dataEncerramento != null ? moment(planoContabil.dataEncerramento) : null;
        });
        return res;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';
import { PlanoContabilService } from './plano-contabil.service';

@Component({
    selector: 'jhi-plano-contabil-update',
    templateUrl: './plano-contabil-update.component.html'
})
export class PlanoContabilUpdateComponent implements OnInit {
    planoContabil: IPlanoContabil;
    isSaving: boolean;
    dataContratacao: string;
    dataEncerramento: string;

    constructor(private planoContabilService: PlanoContabilService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ planoContabil }) => {
            this.planoContabil = planoContabil;
            this.dataContratacao =
                this.planoContabil.dataContratacao != null ? this.planoContabil.dataContratacao.format(DATE_TIME_FORMAT) : null;
            this.dataEncerramento =
                this.planoContabil.dataEncerramento != null ? this.planoContabil.dataEncerramento.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.planoContabil.dataContratacao = this.dataContratacao != null ? moment(this.dataContratacao, DATE_TIME_FORMAT) : null;
        this.planoContabil.dataEncerramento = this.dataEncerramento != null ? moment(this.dataEncerramento, DATE_TIME_FORMAT) : null;
        if (this.planoContabil.id !== undefined) {
            this.subscribeToSaveResponse(this.planoContabilService.update(this.planoContabil));
        } else {
            this.subscribeToSaveResponse(this.planoContabilService.create(this.planoContabil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoContabil>>) {
        result.subscribe((res: HttpResponse<IPlanoContabil>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

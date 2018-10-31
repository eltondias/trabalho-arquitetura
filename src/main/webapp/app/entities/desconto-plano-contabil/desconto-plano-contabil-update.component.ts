import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from './desconto-plano-contabil.service';
import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento';
import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil';

@Component({
    selector: 'jhi-desconto-plano-contabil-update',
    templateUrl: './desconto-plano-contabil-update.component.html'
})
export class DescontoPlanoContabilUpdateComponent implements OnInit {
    descontoPlanoContabil: IDescontoPlanoContabil;
    isSaving: boolean;

    periodopagamentos: IPeriodoPagamento[];

    planocontabils: IPlanoContabil[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private descontoPlanoContabilService: DescontoPlanoContabilService,
        private periodoPagamentoService: PeriodoPagamentoService,
        private planoContabilService: PlanoContabilService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ descontoPlanoContabil }) => {
            this.descontoPlanoContabil = descontoPlanoContabil;
        });
        this.periodoPagamentoService.query().subscribe(
            (res: HttpResponse<IPeriodoPagamento[]>) => {
                this.periodopagamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.planoContabilService.query().subscribe(
            (res: HttpResponse<IPlanoContabil[]>) => {
                this.planocontabils = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.descontoPlanoContabil.id !== undefined) {
            this.subscribeToSaveResponse(this.descontoPlanoContabilService.update(this.descontoPlanoContabil));
        } else {
            this.subscribeToSaveResponse(this.descontoPlanoContabilService.create(this.descontoPlanoContabil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDescontoPlanoContabil>>) {
        result.subscribe(
            (res: HttpResponse<IDescontoPlanoContabil>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPeriodoPagamentoById(index: number, item: IPeriodoPagamento) {
        return item.id;
    }

    trackPlanoContabilById(index: number, item: IPlanoContabil) {
        return item.id;
    }
}

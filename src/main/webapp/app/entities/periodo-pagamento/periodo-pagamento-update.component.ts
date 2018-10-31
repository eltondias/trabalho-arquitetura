import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';
import { PeriodoPagamentoService } from './periodo-pagamento.service';

@Component({
    selector: 'jhi-periodo-pagamento-update',
    templateUrl: './periodo-pagamento-update.component.html'
})
export class PeriodoPagamentoUpdateComponent implements OnInit {
    periodoPagamento: IPeriodoPagamento;
    isSaving: boolean;

    constructor(private periodoPagamentoService: PeriodoPagamentoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ periodoPagamento }) => {
            this.periodoPagamento = periodoPagamento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.periodoPagamento.id !== undefined) {
            this.subscribeToSaveResponse(this.periodoPagamentoService.update(this.periodoPagamento));
        } else {
            this.subscribeToSaveResponse(this.periodoPagamentoService.create(this.periodoPagamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodoPagamento>>) {
        result.subscribe((res: HttpResponse<IPeriodoPagamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDescontoGrupo } from 'app/shared/model/desconto-grupo.model';
import { DescontoGrupoService } from './desconto-grupo.service';
import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento';
import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';
import { GrupoContaAzulService } from 'app/entities/grupo-conta-azul';

@Component({
    selector: 'jhi-desconto-grupo-update',
    templateUrl: './desconto-grupo-update.component.html'
})
export class DescontoGrupoUpdateComponent implements OnInit {
    descontoGrupo: IDescontoGrupo;
    isSaving: boolean;

    periodopagamentos: IPeriodoPagamento[];

    grupocontaazuls: IGrupoContaAzul[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private descontoGrupoService: DescontoGrupoService,
        private periodoPagamentoService: PeriodoPagamentoService,
        private grupoContaAzulService: GrupoContaAzulService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ descontoGrupo }) => {
            this.descontoGrupo = descontoGrupo;
        });
        this.periodoPagamentoService.query().subscribe(
            (res: HttpResponse<IPeriodoPagamento[]>) => {
                this.periodopagamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.grupoContaAzulService.query().subscribe(
            (res: HttpResponse<IGrupoContaAzul[]>) => {
                this.grupocontaazuls = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.descontoGrupo.id !== undefined) {
            this.subscribeToSaveResponse(this.descontoGrupoService.update(this.descontoGrupo));
        } else {
            this.subscribeToSaveResponse(this.descontoGrupoService.create(this.descontoGrupo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDescontoGrupo>>) {
        result.subscribe((res: HttpResponse<IDescontoGrupo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGrupoContaAzulById(index: number, item: IGrupoContaAzul) {
        return item.id;
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';
import { Principal } from 'app/core';
import { PeriodoPagamentoService } from './periodo-pagamento.service';

@Component({
    selector: 'jhi-periodo-pagamento',
    templateUrl: './periodo-pagamento.component.html'
})
export class PeriodoPagamentoComponent implements OnInit, OnDestroy {
    periodoPagamentos: IPeriodoPagamento[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private periodoPagamentoService: PeriodoPagamentoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.periodoPagamentoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPeriodoPagamento[]>) => (this.periodoPagamentos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.periodoPagamentoService.query().subscribe(
            (res: HttpResponse<IPeriodoPagamento[]>) => {
                this.periodoPagamentos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodoPagamentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPeriodoPagamento) {
        return item.id;
    }

    registerChangeInPeriodoPagamentos() {
        this.eventSubscriber = this.eventManager.subscribe('periodoPagamentoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

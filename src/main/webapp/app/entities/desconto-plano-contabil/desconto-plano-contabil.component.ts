import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';
import { Principal } from 'app/core';
import { DescontoPlanoContabilService } from './desconto-plano-contabil.service';

@Component({
    selector: 'jhi-desconto-plano-contabil',
    templateUrl: './desconto-plano-contabil.component.html'
})
export class DescontoPlanoContabilComponent implements OnInit, OnDestroy {
    descontoPlanoContabils: IDescontoPlanoContabil[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private descontoPlanoContabilService: DescontoPlanoContabilService,
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
            this.descontoPlanoContabilService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IDescontoPlanoContabil[]>) => (this.descontoPlanoContabils = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.descontoPlanoContabilService.query().subscribe(
            (res: HttpResponse<IDescontoPlanoContabil[]>) => {
                this.descontoPlanoContabils = res.body;
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
        this.registerChangeInDescontoPlanoContabils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDescontoPlanoContabil) {
        return item.id;
    }

    registerChangeInDescontoPlanoContabils() {
        this.eventSubscriber = this.eventManager.subscribe('descontoPlanoContabilListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

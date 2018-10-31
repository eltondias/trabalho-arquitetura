import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';
import { Principal } from 'app/core';
import { PlanoContabilService } from './plano-contabil.service';

@Component({
    selector: 'jhi-plano-contabil',
    templateUrl: './plano-contabil.component.html'
})
export class PlanoContabilComponent implements OnInit, OnDestroy {
    planoContabils: IPlanoContabil[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private planoContabilService: PlanoContabilService,
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
            this.planoContabilService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPlanoContabil[]>) => (this.planoContabils = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.planoContabilService.query().subscribe(
            (res: HttpResponse<IPlanoContabil[]>) => {
                this.planoContabils = res.body;
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
        this.registerChangeInPlanoContabils();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPlanoContabil) {
        return item.id;
    }

    registerChangeInPlanoContabils() {
        this.eventSubscriber = this.eventManager.subscribe('planoContabilListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

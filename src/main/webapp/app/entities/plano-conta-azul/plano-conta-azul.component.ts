import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';
import { Principal } from 'app/core';
import { PlanoContaAzulService } from './plano-conta-azul.service';

@Component({
    selector: 'jhi-plano-conta-azul',
    templateUrl: './plano-conta-azul.component.html'
})
export class PlanoContaAzulComponent implements OnInit, OnDestroy {
    planoContaAzuls: IPlanoContaAzul[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private planoContaAzulService: PlanoContaAzulService,
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
            this.planoContaAzulService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPlanoContaAzul[]>) => (this.planoContaAzuls = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.planoContaAzulService.query().subscribe(
            (res: HttpResponse<IPlanoContaAzul[]>) => {
                this.planoContaAzuls = res.body;
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
        this.registerChangeInPlanoContaAzuls();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPlanoContaAzul) {
        return item.id;
    }

    registerChangeInPlanoContaAzuls() {
        this.eventSubscriber = this.eventManager.subscribe('planoContaAzulListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';
import { Principal } from 'app/core';
import { GrupoContaAzulService } from './grupo-conta-azul.service';

@Component({
    selector: 'jhi-grupo-conta-azul',
    templateUrl: './grupo-conta-azul.component.html'
})
export class GrupoContaAzulComponent implements OnInit, OnDestroy {
    grupoContaAzuls: IGrupoContaAzul[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private grupoContaAzulService: GrupoContaAzulService,
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
            this.grupoContaAzulService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IGrupoContaAzul[]>) => (this.grupoContaAzuls = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.grupoContaAzulService.query().subscribe(
            (res: HttpResponse<IGrupoContaAzul[]>) => {
                this.grupoContaAzuls = res.body;
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
        this.registerChangeInGrupoContaAzuls();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGrupoContaAzul) {
        return item.id;
    }

    registerChangeInGrupoContaAzuls() {
        this.eventSubscriber = this.eventManager.subscribe('grupoContaAzulListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

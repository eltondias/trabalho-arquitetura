import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlanoEmpresa } from 'app/shared/model/plano-empresa.model';
import { Principal } from 'app/core';
import { PlanoEmpresaService } from './plano-empresa.service';

@Component({
    selector: 'jhi-plano-empresa',
    templateUrl: './plano-empresa.component.html'
})
export class PlanoEmpresaComponent implements OnInit, OnDestroy {
    planoEmpresas: IPlanoEmpresa[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private planoEmpresaService: PlanoEmpresaService,
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
            this.planoEmpresaService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPlanoEmpresa[]>) => (this.planoEmpresas = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.planoEmpresaService.query().subscribe(
            (res: HttpResponse<IPlanoEmpresa[]>) => {
                this.planoEmpresas = res.body;
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
        this.registerChangeInPlanoEmpresas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPlanoEmpresa) {
        return item.id;
    }

    registerChangeInPlanoEmpresas() {
        this.eventSubscriber = this.eventManager.subscribe('planoEmpresaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

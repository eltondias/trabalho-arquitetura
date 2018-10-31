import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEndereco } from 'app/shared/model/endereco.model';
import { Principal } from 'app/core';
import { EnderecoService } from './endereco.service';

@Component({
    selector: 'jhi-endereco',
    templateUrl: './endereco.component.html'
})
export class EnderecoComponent implements OnInit, OnDestroy {
    enderecos: IEndereco[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private enderecoService: EnderecoService,
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
            this.enderecoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IEndereco[]>) => (this.enderecos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.enderecoService.query().subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                this.enderecos = res.body;
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
        this.registerChangeInEnderecos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEndereco) {
        return item.id;
    }

    registerChangeInEnderecos() {
        this.eventSubscriber = this.eventManager.subscribe('enderecoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEnquadramento } from 'app/shared/model/enquadramento.model';
import { Principal } from 'app/core';
import { EnquadramentoService } from './enquadramento.service';

@Component({
    selector: 'jhi-enquadramento',
    templateUrl: './enquadramento.component.html'
})
export class EnquadramentoComponent implements OnInit, OnDestroy {
    enquadramentos: IEnquadramento[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private enquadramentoService: EnquadramentoService,
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
            this.enquadramentoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IEnquadramento[]>) => (this.enquadramentos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.enquadramentoService.query().subscribe(
            (res: HttpResponse<IEnquadramento[]>) => {
                this.enquadramentos = res.body;
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
        this.registerChangeInEnquadramentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEnquadramento) {
        return item.id;
    }

    registerChangeInEnquadramentos() {
        this.eventSubscriber = this.eventManager.subscribe('enquadramentoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

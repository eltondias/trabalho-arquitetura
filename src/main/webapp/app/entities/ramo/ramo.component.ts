import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRamo } from 'app/shared/model/ramo.model';
import { Principal } from 'app/core';
import { RamoService } from './ramo.service';

@Component({
    selector: 'jhi-ramo',
    templateUrl: './ramo.component.html'
})
export class RamoComponent implements OnInit, OnDestroy {
    ramos: IRamo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private ramoService: RamoService,
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
            this.ramoService
                .search({
                    query: this.currentSearch
                })
                .subscribe((res: HttpResponse<IRamo[]>) => (this.ramos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.ramoService.query().subscribe(
            (res: HttpResponse<IRamo[]>) => {
                this.ramos = res.body;
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
        this.registerChangeInRamos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRamo) {
        return item.id;
    }

    registerChangeInRamos() {
        this.eventSubscriber = this.eventManager.subscribe('ramoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

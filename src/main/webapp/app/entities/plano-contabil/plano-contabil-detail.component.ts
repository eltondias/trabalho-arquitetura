import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';

@Component({
    selector: 'jhi-plano-contabil-detail',
    templateUrl: './plano-contabil-detail.component.html'
})
export class PlanoContabilDetailComponent implements OnInit {
    planoContabil: IPlanoContabil;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planoContabil }) => {
            this.planoContabil = planoContabil;
        });
    }

    previousState() {
        window.history.back();
    }
}

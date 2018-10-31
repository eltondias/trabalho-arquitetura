import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';

@Component({
    selector: 'jhi-desconto-plano-contabil-detail',
    templateUrl: './desconto-plano-contabil-detail.component.html'
})
export class DescontoPlanoContabilDetailComponent implements OnInit {
    descontoPlanoContabil: IDescontoPlanoContabil;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ descontoPlanoContabil }) => {
            this.descontoPlanoContabil = descontoPlanoContabil;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';

@Component({
    selector: 'jhi-plano-conta-azul-detail',
    templateUrl: './plano-conta-azul-detail.component.html'
})
export class PlanoContaAzulDetailComponent implements OnInit {
    planoContaAzul: IPlanoContaAzul;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planoContaAzul }) => {
            this.planoContaAzul = planoContaAzul;
        });
    }

    previousState() {
        window.history.back();
    }
}

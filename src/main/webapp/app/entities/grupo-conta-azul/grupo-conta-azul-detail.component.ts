import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';

@Component({
    selector: 'jhi-grupo-conta-azul-detail',
    templateUrl: './grupo-conta-azul-detail.component.html'
})
export class GrupoContaAzulDetailComponent implements OnInit {
    grupoContaAzul: IGrupoContaAzul;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupoContaAzul }) => {
            this.grupoContaAzul = grupoContaAzul;
        });
    }

    previousState() {
        window.history.back();
    }
}

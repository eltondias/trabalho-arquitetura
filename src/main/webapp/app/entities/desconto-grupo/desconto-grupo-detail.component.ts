import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDescontoGrupo } from 'app/shared/model/desconto-grupo.model';

@Component({
    selector: 'jhi-desconto-grupo-detail',
    templateUrl: './desconto-grupo-detail.component.html'
})
export class DescontoGrupoDetailComponent implements OnInit {
    descontoGrupo: IDescontoGrupo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ descontoGrupo }) => {
            this.descontoGrupo = descontoGrupo;
        });
    }

    previousState() {
        window.history.back();
    }
}

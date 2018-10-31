import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoEmpresa } from 'app/shared/model/plano-empresa.model';

@Component({
    selector: 'jhi-plano-empresa-detail',
    templateUrl: './plano-empresa-detail.component.html'
})
export class PlanoEmpresaDetailComponent implements OnInit {
    planoEmpresa: IPlanoEmpresa;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planoEmpresa }) => {
            this.planoEmpresa = planoEmpresa;
        });
    }

    previousState() {
        window.history.back();
    }
}

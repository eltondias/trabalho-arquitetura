import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';

@Component({
    selector: 'jhi-periodo-pagamento-detail',
    templateUrl: './periodo-pagamento-detail.component.html'
})
export class PeriodoPagamentoDetailComponent implements OnInit {
    periodoPagamento: IPeriodoPagamento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodoPagamento }) => {
            this.periodoPagamento = periodoPagamento;
        });
    }

    previousState() {
        window.history.back();
    }
}

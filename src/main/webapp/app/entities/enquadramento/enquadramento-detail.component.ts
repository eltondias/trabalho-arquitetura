import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnquadramento } from 'app/shared/model/enquadramento.model';

@Component({
    selector: 'jhi-enquadramento-detail',
    templateUrl: './enquadramento-detail.component.html'
})
export class EnquadramentoDetailComponent implements OnInit {
    enquadramento: IEnquadramento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enquadramento }) => {
            this.enquadramento = enquadramento;
        });
    }

    previousState() {
        window.history.back();
    }
}

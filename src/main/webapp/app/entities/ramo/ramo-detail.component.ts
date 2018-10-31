import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRamo } from 'app/shared/model/ramo.model';

@Component({
    selector: 'jhi-ramo-detail',
    templateUrl: './ramo-detail.component.html'
})
export class RamoDetailComponent implements OnInit {
    ramo: IRamo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ramo }) => {
            this.ramo = ramo;
        });
    }

    previousState() {
        window.history.back();
    }
}

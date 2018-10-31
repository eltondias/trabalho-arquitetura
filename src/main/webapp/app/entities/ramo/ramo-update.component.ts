import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRamo } from 'app/shared/model/ramo.model';
import { RamoService } from './ramo.service';

@Component({
    selector: 'jhi-ramo-update',
    templateUrl: './ramo-update.component.html'
})
export class RamoUpdateComponent implements OnInit {
    ramo: IRamo;
    isSaving: boolean;

    constructor(private ramoService: RamoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ramo }) => {
            this.ramo = ramo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ramo.id !== undefined) {
            this.subscribeToSaveResponse(this.ramoService.update(this.ramo));
        } else {
            this.subscribeToSaveResponse(this.ramoService.create(this.ramo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRamo>>) {
        result.subscribe((res: HttpResponse<IRamo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

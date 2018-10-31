import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEnquadramento } from 'app/shared/model/enquadramento.model';
import { EnquadramentoService } from './enquadramento.service';

@Component({
    selector: 'jhi-enquadramento-update',
    templateUrl: './enquadramento-update.component.html'
})
export class EnquadramentoUpdateComponent implements OnInit {
    enquadramento: IEnquadramento;
    isSaving: boolean;

    constructor(private enquadramentoService: EnquadramentoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ enquadramento }) => {
            this.enquadramento = enquadramento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.enquadramento.id !== undefined) {
            this.subscribeToSaveResponse(this.enquadramentoService.update(this.enquadramento));
        } else {
            this.subscribeToSaveResponse(this.enquadramentoService.create(this.enquadramento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnquadramento>>) {
        result.subscribe((res: HttpResponse<IEnquadramento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

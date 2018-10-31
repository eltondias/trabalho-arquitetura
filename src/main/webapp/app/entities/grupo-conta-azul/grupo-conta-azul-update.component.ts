import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';
import { GrupoContaAzulService } from './grupo-conta-azul.service';

@Component({
    selector: 'jhi-grupo-conta-azul-update',
    templateUrl: './grupo-conta-azul-update.component.html'
})
export class GrupoContaAzulUpdateComponent implements OnInit {
    grupoContaAzul: IGrupoContaAzul;
    isSaving: boolean;

    constructor(private grupoContaAzulService: GrupoContaAzulService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ grupoContaAzul }) => {
            this.grupoContaAzul = grupoContaAzul;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.grupoContaAzul.id !== undefined) {
            this.subscribeToSaveResponse(this.grupoContaAzulService.update(this.grupoContaAzul));
        } else {
            this.subscribeToSaveResponse(this.grupoContaAzulService.create(this.grupoContaAzul));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoContaAzul>>) {
        result.subscribe((res: HttpResponse<IGrupoContaAzul>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

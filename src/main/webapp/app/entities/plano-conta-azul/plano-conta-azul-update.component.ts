import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';
import { PlanoContaAzulService } from './plano-conta-azul.service';
import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';
import { GrupoContaAzulService } from 'app/entities/grupo-conta-azul';

@Component({
    selector: 'jhi-plano-conta-azul-update',
    templateUrl: './plano-conta-azul-update.component.html'
})
export class PlanoContaAzulUpdateComponent implements OnInit {
    planoContaAzul: IPlanoContaAzul;
    isSaving: boolean;

    grupocontaazuls: IGrupoContaAzul[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private planoContaAzulService: PlanoContaAzulService,
        private grupoContaAzulService: GrupoContaAzulService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ planoContaAzul }) => {
            this.planoContaAzul = planoContaAzul;
        });
        this.grupoContaAzulService.query().subscribe(
            (res: HttpResponse<IGrupoContaAzul[]>) => {
                this.grupocontaazuls = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.planoContaAzul.id !== undefined) {
            this.subscribeToSaveResponse(this.planoContaAzulService.update(this.planoContaAzul));
        } else {
            this.subscribeToSaveResponse(this.planoContaAzulService.create(this.planoContaAzul));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoContaAzul>>) {
        result.subscribe((res: HttpResponse<IPlanoContaAzul>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackGrupoContaAzulById(index: number, item: IGrupoContaAzul) {
        return item.id;
    }
}

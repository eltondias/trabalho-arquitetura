import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPlanoEmpresa } from 'app/shared/model/plano-empresa.model';
import { PlanoEmpresaService } from './plano-empresa.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil';
import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul';

@Component({
    selector: 'jhi-plano-empresa-update',
    templateUrl: './plano-empresa-update.component.html'
})
export class PlanoEmpresaUpdateComponent implements OnInit {
    planoEmpresa: IPlanoEmpresa;
    isSaving: boolean;

    empresas: IEmpresa[];

    planocontabils: IPlanoContabil[];

    planocontaazuls: IPlanoContaAzul[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private planoEmpresaService: PlanoEmpresaService,
        private empresaService: EmpresaService,
        private planoContabilService: PlanoContabilService,
        private planoContaAzulService: PlanoContaAzulService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ planoEmpresa }) => {
            this.planoEmpresa = planoEmpresa;
        });
        this.empresaService.query().subscribe(
            (res: HttpResponse<IEmpresa[]>) => {
                this.empresas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.planoContabilService.query().subscribe(
            (res: HttpResponse<IPlanoContabil[]>) => {
                this.planocontabils = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.planoContaAzulService.query().subscribe(
            (res: HttpResponse<IPlanoContaAzul[]>) => {
                this.planocontaazuls = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.planoEmpresa.id !== undefined) {
            this.subscribeToSaveResponse(this.planoEmpresaService.update(this.planoEmpresa));
        } else {
            this.subscribeToSaveResponse(this.planoEmpresaService.create(this.planoEmpresa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoEmpresa>>) {
        result.subscribe((res: HttpResponse<IPlanoEmpresa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }

    trackPlanoContabilById(index: number, item: IPlanoContabil) {
        return item.id;
    }

    trackPlanoContaAzulById(index: number, item: IPlanoContaAzul) {
        return item.id;
    }
}

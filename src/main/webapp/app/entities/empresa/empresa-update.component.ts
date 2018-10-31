import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { IRamo } from 'app/shared/model/ramo.model';
import { RamoService } from 'app/entities/ramo';
import { IEnquadramento } from 'app/shared/model/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento';

@Component({
    selector: 'jhi-empresa-update',
    templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
    empresa: IEmpresa;
    isSaving: boolean;

    ramos: IRamo[];

    enquadramentos: IEnquadramento[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private empresaService: EmpresaService,
        private ramoService: RamoService,
        private enquadramentoService: EnquadramentoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empresa }) => {
            this.empresa = empresa;
        });
        this.ramoService.query().subscribe(
            (res: HttpResponse<IRamo[]>) => {
                this.ramos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.enquadramentoService.query().subscribe(
            (res: HttpResponse<IEnquadramento[]>) => {
                this.enquadramentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.empresa.id !== undefined) {
            this.subscribeToSaveResponse(this.empresaService.update(this.empresa));
        } else {
            this.subscribeToSaveResponse(this.empresaService.create(this.empresa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
        result.subscribe((res: HttpResponse<IEmpresa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRamoById(index: number, item: IRamo) {
        return item.id;
    }

    trackEnquadramentoById(index: number, item: IEnquadramento) {
        return item.id;
    }
}

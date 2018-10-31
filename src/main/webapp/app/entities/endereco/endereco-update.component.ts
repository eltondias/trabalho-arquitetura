import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
    selector: 'jhi-endereco-update',
    templateUrl: './endereco-update.component.html'
})
export class EnderecoUpdateComponent implements OnInit {
    endereco: IEndereco;
    isSaving: boolean;

    empresas: IEmpresa[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private enderecoService: EnderecoService,
        private empresaService: EmpresaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ endereco }) => {
            this.endereco = endereco;
        });
        this.empresaService.query().subscribe(
            (res: HttpResponse<IEmpresa[]>) => {
                this.empresas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.endereco.id !== undefined) {
            this.subscribeToSaveResponse(this.enderecoService.update(this.endereco));
        } else {
            this.subscribeToSaveResponse(this.enderecoService.create(this.endereco));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>) {
        result.subscribe((res: HttpResponse<IEndereco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}

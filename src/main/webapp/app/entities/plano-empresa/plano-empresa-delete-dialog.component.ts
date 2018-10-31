import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoEmpresa } from 'app/shared/model/plano-empresa.model';
import { PlanoEmpresaService } from './plano-empresa.service';

@Component({
    selector: 'jhi-plano-empresa-delete-dialog',
    templateUrl: './plano-empresa-delete-dialog.component.html'
})
export class PlanoEmpresaDeleteDialogComponent {
    planoEmpresa: IPlanoEmpresa;

    constructor(
        private planoEmpresaService: PlanoEmpresaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planoEmpresaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'planoEmpresaListModification',
                content: 'Deleted an planoEmpresa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plano-empresa-delete-popup',
    template: ''
})
export class PlanoEmpresaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planoEmpresa }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlanoEmpresaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.planoEmpresa = planoEmpresa;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

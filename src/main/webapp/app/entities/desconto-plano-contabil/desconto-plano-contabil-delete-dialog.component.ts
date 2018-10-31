import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from './desconto-plano-contabil.service';

@Component({
    selector: 'jhi-desconto-plano-contabil-delete-dialog',
    templateUrl: './desconto-plano-contabil-delete-dialog.component.html'
})
export class DescontoPlanoContabilDeleteDialogComponent {
    descontoPlanoContabil: IDescontoPlanoContabil;

    constructor(
        private descontoPlanoContabilService: DescontoPlanoContabilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.descontoPlanoContabilService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'descontoPlanoContabilListModification',
                content: 'Deleted an descontoPlanoContabil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-desconto-plano-contabil-delete-popup',
    template: ''
})
export class DescontoPlanoContabilDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ descontoPlanoContabil }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DescontoPlanoContabilDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.descontoPlanoContabil = descontoPlanoContabil;
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

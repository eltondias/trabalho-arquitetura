import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoContabil } from 'app/shared/model/plano-contabil.model';
import { PlanoContabilService } from './plano-contabil.service';

@Component({
    selector: 'jhi-plano-contabil-delete-dialog',
    templateUrl: './plano-contabil-delete-dialog.component.html'
})
export class PlanoContabilDeleteDialogComponent {
    planoContabil: IPlanoContabil;

    constructor(
        private planoContabilService: PlanoContabilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planoContabilService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'planoContabilListModification',
                content: 'Deleted an planoContabil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plano-contabil-delete-popup',
    template: ''
})
export class PlanoContabilDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planoContabil }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlanoContabilDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.planoContabil = planoContabil;
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

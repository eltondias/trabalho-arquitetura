import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';
import { PlanoContaAzulService } from './plano-conta-azul.service';

@Component({
    selector: 'jhi-plano-conta-azul-delete-dialog',
    templateUrl: './plano-conta-azul-delete-dialog.component.html'
})
export class PlanoContaAzulDeleteDialogComponent {
    planoContaAzul: IPlanoContaAzul;

    constructor(
        private planoContaAzulService: PlanoContaAzulService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planoContaAzulService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'planoContaAzulListModification',
                content: 'Deleted an planoContaAzul'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plano-conta-azul-delete-popup',
    template: ''
})
export class PlanoContaAzulDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planoContaAzul }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlanoContaAzulDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.planoContaAzul = planoContaAzul;
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

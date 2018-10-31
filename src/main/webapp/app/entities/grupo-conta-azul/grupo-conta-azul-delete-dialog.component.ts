import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';
import { GrupoContaAzulService } from './grupo-conta-azul.service';

@Component({
    selector: 'jhi-grupo-conta-azul-delete-dialog',
    templateUrl: './grupo-conta-azul-delete-dialog.component.html'
})
export class GrupoContaAzulDeleteDialogComponent {
    grupoContaAzul: IGrupoContaAzul;

    constructor(
        private grupoContaAzulService: GrupoContaAzulService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grupoContaAzulService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'grupoContaAzulListModification',
                content: 'Deleted an grupoContaAzul'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grupo-conta-azul-delete-popup',
    template: ''
})
export class GrupoContaAzulDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupoContaAzul }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GrupoContaAzulDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.grupoContaAzul = grupoContaAzul;
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

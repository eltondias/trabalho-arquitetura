import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDescontoGrupo } from 'app/shared/model/desconto-grupo.model';
import { DescontoGrupoService } from './desconto-grupo.service';

@Component({
    selector: 'jhi-desconto-grupo-delete-dialog',
    templateUrl: './desconto-grupo-delete-dialog.component.html'
})
export class DescontoGrupoDeleteDialogComponent {
    descontoGrupo: IDescontoGrupo;

    constructor(
        private descontoGrupoService: DescontoGrupoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.descontoGrupoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'descontoGrupoListModification',
                content: 'Deleted an descontoGrupo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-desconto-grupo-delete-popup',
    template: ''
})
export class DescontoGrupoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ descontoGrupo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DescontoGrupoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.descontoGrupo = descontoGrupo;
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

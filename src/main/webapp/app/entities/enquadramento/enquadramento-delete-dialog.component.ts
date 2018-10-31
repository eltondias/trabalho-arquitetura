import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnquadramento } from 'app/shared/model/enquadramento.model';
import { EnquadramentoService } from './enquadramento.service';

@Component({
    selector: 'jhi-enquadramento-delete-dialog',
    templateUrl: './enquadramento-delete-dialog.component.html'
})
export class EnquadramentoDeleteDialogComponent {
    enquadramento: IEnquadramento;

    constructor(
        private enquadramentoService: EnquadramentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enquadramentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enquadramentoListModification',
                content: 'Deleted an enquadramento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enquadramento-delete-popup',
    template: ''
})
export class EnquadramentoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enquadramento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnquadramentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.enquadramento = enquadramento;
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

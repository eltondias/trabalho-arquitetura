import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';
import { PeriodoPagamentoService } from './periodo-pagamento.service';

@Component({
    selector: 'jhi-periodo-pagamento-delete-dialog',
    templateUrl: './periodo-pagamento-delete-dialog.component.html'
})
export class PeriodoPagamentoDeleteDialogComponent {
    periodoPagamento: IPeriodoPagamento;

    constructor(
        private periodoPagamentoService: PeriodoPagamentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.periodoPagamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'periodoPagamentoListModification',
                content: 'Deleted an periodoPagamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-periodo-pagamento-delete-popup',
    template: ''
})
export class PeriodoPagamentoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodoPagamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PeriodoPagamentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.periodoPagamento = periodoPagamento;
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

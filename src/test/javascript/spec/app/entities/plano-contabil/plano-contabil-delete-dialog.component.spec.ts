/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContabilDeleteDialogComponent } from 'app/entities/plano-contabil/plano-contabil-delete-dialog.component';
import { PlanoContabilService } from 'app/entities/plano-contabil/plano-contabil.service';

describe('Component Tests', () => {
    describe('PlanoContabil Management Delete Component', () => {
        let comp: PlanoContabilDeleteDialogComponent;
        let fixture: ComponentFixture<PlanoContabilDeleteDialogComponent>;
        let service: PlanoContabilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContabilDeleteDialogComponent]
            })
                .overrideTemplate(PlanoContabilDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanoContabilDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoContabilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

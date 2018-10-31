/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContaAzulDeleteDialogComponent } from 'app/entities/plano-conta-azul/plano-conta-azul-delete-dialog.component';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/plano-conta-azul.service';

describe('Component Tests', () => {
    describe('PlanoContaAzul Management Delete Component', () => {
        let comp: PlanoContaAzulDeleteDialogComponent;
        let fixture: ComponentFixture<PlanoContaAzulDeleteDialogComponent>;
        let service: PlanoContaAzulService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContaAzulDeleteDialogComponent]
            })
                .overrideTemplate(PlanoContaAzulDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanoContaAzulDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoContaAzulService);
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

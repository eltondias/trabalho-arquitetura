/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { RamoDeleteDialogComponent } from 'app/entities/ramo/ramo-delete-dialog.component';
import { RamoService } from 'app/entities/ramo/ramo.service';

describe('Component Tests', () => {
    describe('Ramo Management Delete Component', () => {
        let comp: RamoDeleteDialogComponent;
        let fixture: ComponentFixture<RamoDeleteDialogComponent>;
        let service: RamoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [RamoDeleteDialogComponent]
            })
                .overrideTemplate(RamoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RamoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RamoService);
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

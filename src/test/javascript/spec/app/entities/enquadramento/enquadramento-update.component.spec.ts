/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { EnquadramentoUpdateComponent } from 'app/entities/enquadramento/enquadramento-update.component';
import { EnquadramentoService } from 'app/entities/enquadramento/enquadramento.service';
import { Enquadramento } from 'app/shared/model/enquadramento.model';

describe('Component Tests', () => {
    describe('Enquadramento Management Update Component', () => {
        let comp: EnquadramentoUpdateComponent;
        let fixture: ComponentFixture<EnquadramentoUpdateComponent>;
        let service: EnquadramentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [EnquadramentoUpdateComponent]
            })
                .overrideTemplate(EnquadramentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnquadramentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnquadramentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Enquadramento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.enquadramento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Enquadramento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.enquadramento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

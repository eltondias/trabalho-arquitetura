/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { DescontoPlanoContabilUpdateComponent } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil-update.component';
import { DescontoPlanoContabilService } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.service';
import { DescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';

describe('Component Tests', () => {
    describe('DescontoPlanoContabil Management Update Component', () => {
        let comp: DescontoPlanoContabilUpdateComponent;
        let fixture: ComponentFixture<DescontoPlanoContabilUpdateComponent>;
        let service: DescontoPlanoContabilService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [DescontoPlanoContabilUpdateComponent]
            })
                .overrideTemplate(DescontoPlanoContabilUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DescontoPlanoContabilUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DescontoPlanoContabilService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DescontoPlanoContabil(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.descontoPlanoContabil = entity;
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
                    const entity = new DescontoPlanoContabil();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.descontoPlanoContabil = entity;
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

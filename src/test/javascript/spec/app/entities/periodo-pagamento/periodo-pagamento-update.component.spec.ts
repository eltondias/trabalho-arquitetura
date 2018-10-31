/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PeriodoPagamentoUpdateComponent } from 'app/entities/periodo-pagamento/periodo-pagamento-update.component';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/periodo-pagamento.service';
import { PeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';

describe('Component Tests', () => {
    describe('PeriodoPagamento Management Update Component', () => {
        let comp: PeriodoPagamentoUpdateComponent;
        let fixture: ComponentFixture<PeriodoPagamentoUpdateComponent>;
        let service: PeriodoPagamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PeriodoPagamentoUpdateComponent]
            })
                .overrideTemplate(PeriodoPagamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodoPagamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoPagamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PeriodoPagamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.periodoPagamento = entity;
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
                    const entity = new PeriodoPagamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.periodoPagamento = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PeriodoPagamentoComponent } from 'app/entities/periodo-pagamento/periodo-pagamento.component';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/periodo-pagamento.service';
import { PeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';

describe('Component Tests', () => {
    describe('PeriodoPagamento Management Component', () => {
        let comp: PeriodoPagamentoComponent;
        let fixture: ComponentFixture<PeriodoPagamentoComponent>;
        let service: PeriodoPagamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PeriodoPagamentoComponent],
                providers: []
            })
                .overrideTemplate(PeriodoPagamentoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodoPagamentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodoPagamentoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PeriodoPagamento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.periodoPagamentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

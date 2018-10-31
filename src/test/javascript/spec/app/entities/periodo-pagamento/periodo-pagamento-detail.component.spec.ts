/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PeriodoPagamentoDetailComponent } from 'app/entities/periodo-pagamento/periodo-pagamento-detail.component';
import { PeriodoPagamento } from 'app/shared/model/periodo-pagamento.model';

describe('Component Tests', () => {
    describe('PeriodoPagamento Management Detail Component', () => {
        let comp: PeriodoPagamentoDetailComponent;
        let fixture: ComponentFixture<PeriodoPagamentoDetailComponent>;
        const route = ({ data: of({ periodoPagamento: new PeriodoPagamento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PeriodoPagamentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PeriodoPagamentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PeriodoPagamentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.periodoPagamento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

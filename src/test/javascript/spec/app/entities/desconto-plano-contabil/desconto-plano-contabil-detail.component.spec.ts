/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { DescontoPlanoContabilDetailComponent } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil-detail.component';
import { DescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';

describe('Component Tests', () => {
    describe('DescontoPlanoContabil Management Detail Component', () => {
        let comp: DescontoPlanoContabilDetailComponent;
        let fixture: ComponentFixture<DescontoPlanoContabilDetailComponent>;
        const route = ({ data: of({ descontoPlanoContabil: new DescontoPlanoContabil(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [DescontoPlanoContabilDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DescontoPlanoContabilDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DescontoPlanoContabilDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.descontoPlanoContabil).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

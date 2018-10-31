/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContaAzulDetailComponent } from 'app/entities/plano-conta-azul/plano-conta-azul-detail.component';
import { PlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';

describe('Component Tests', () => {
    describe('PlanoContaAzul Management Detail Component', () => {
        let comp: PlanoContaAzulDetailComponent;
        let fixture: ComponentFixture<PlanoContaAzulDetailComponent>;
        const route = ({ data: of({ planoContaAzul: new PlanoContaAzul(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContaAzulDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlanoContaAzulDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanoContaAzulDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.planoContaAzul).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

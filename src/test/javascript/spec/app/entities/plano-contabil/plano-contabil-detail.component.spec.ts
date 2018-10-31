/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContabilDetailComponent } from 'app/entities/plano-contabil/plano-contabil-detail.component';
import { PlanoContabil } from 'app/shared/model/plano-contabil.model';

describe('Component Tests', () => {
    describe('PlanoContabil Management Detail Component', () => {
        let comp: PlanoContabilDetailComponent;
        let fixture: ComponentFixture<PlanoContabilDetailComponent>;
        const route = ({ data: of({ planoContabil: new PlanoContabil(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContabilDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlanoContabilDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanoContabilDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.planoContabil).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

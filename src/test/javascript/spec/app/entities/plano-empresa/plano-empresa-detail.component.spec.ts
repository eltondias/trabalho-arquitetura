/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoEmpresaDetailComponent } from 'app/entities/plano-empresa/plano-empresa-detail.component';
import { PlanoEmpresa } from 'app/shared/model/plano-empresa.model';

describe('Component Tests', () => {
    describe('PlanoEmpresa Management Detail Component', () => {
        let comp: PlanoEmpresaDetailComponent;
        let fixture: ComponentFixture<PlanoEmpresaDetailComponent>;
        const route = ({ data: of({ planoEmpresa: new PlanoEmpresa(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoEmpresaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlanoEmpresaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanoEmpresaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.planoEmpresa).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

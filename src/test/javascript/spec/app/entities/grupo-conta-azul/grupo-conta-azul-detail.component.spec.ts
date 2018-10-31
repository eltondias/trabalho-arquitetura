/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { GrupoContaAzulDetailComponent } from 'app/entities/grupo-conta-azul/grupo-conta-azul-detail.component';
import { GrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';

describe('Component Tests', () => {
    describe('GrupoContaAzul Management Detail Component', () => {
        let comp: GrupoContaAzulDetailComponent;
        let fixture: ComponentFixture<GrupoContaAzulDetailComponent>;
        const route = ({ data: of({ grupoContaAzul: new GrupoContaAzul(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [GrupoContaAzulDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GrupoContaAzulDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupoContaAzulDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.grupoContaAzul).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

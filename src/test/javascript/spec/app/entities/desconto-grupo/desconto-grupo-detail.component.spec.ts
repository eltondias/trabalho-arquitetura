/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { DescontoGrupoDetailComponent } from 'app/entities/desconto-grupo/desconto-grupo-detail.component';
import { DescontoGrupo } from 'app/shared/model/desconto-grupo.model';

describe('Component Tests', () => {
    describe('DescontoGrupo Management Detail Component', () => {
        let comp: DescontoGrupoDetailComponent;
        let fixture: ComponentFixture<DescontoGrupoDetailComponent>;
        const route = ({ data: of({ descontoGrupo: new DescontoGrupo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [DescontoGrupoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DescontoGrupoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DescontoGrupoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.descontoGrupo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

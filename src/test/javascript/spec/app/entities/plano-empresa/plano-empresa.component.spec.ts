/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoEmpresaComponent } from 'app/entities/plano-empresa/plano-empresa.component';
import { PlanoEmpresaService } from 'app/entities/plano-empresa/plano-empresa.service';
import { PlanoEmpresa } from 'app/shared/model/plano-empresa.model';

describe('Component Tests', () => {
    describe('PlanoEmpresa Management Component', () => {
        let comp: PlanoEmpresaComponent;
        let fixture: ComponentFixture<PlanoEmpresaComponent>;
        let service: PlanoEmpresaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoEmpresaComponent],
                providers: []
            })
                .overrideTemplate(PlanoEmpresaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanoEmpresaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoEmpresaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PlanoEmpresa(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.planoEmpresas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

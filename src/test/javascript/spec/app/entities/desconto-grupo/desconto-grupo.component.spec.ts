/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { DescontoGrupoComponent } from 'app/entities/desconto-grupo/desconto-grupo.component';
import { DescontoGrupoService } from 'app/entities/desconto-grupo/desconto-grupo.service';
import { DescontoGrupo } from 'app/shared/model/desconto-grupo.model';

describe('Component Tests', () => {
    describe('DescontoGrupo Management Component', () => {
        let comp: DescontoGrupoComponent;
        let fixture: ComponentFixture<DescontoGrupoComponent>;
        let service: DescontoGrupoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [DescontoGrupoComponent],
                providers: []
            })
                .overrideTemplate(DescontoGrupoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DescontoGrupoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DescontoGrupoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DescontoGrupo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.descontoGrupos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { GrupoContaAzulComponent } from 'app/entities/grupo-conta-azul/grupo-conta-azul.component';
import { GrupoContaAzulService } from 'app/entities/grupo-conta-azul/grupo-conta-azul.service';
import { GrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';

describe('Component Tests', () => {
    describe('GrupoContaAzul Management Component', () => {
        let comp: GrupoContaAzulComponent;
        let fixture: ComponentFixture<GrupoContaAzulComponent>;
        let service: GrupoContaAzulService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [GrupoContaAzulComponent],
                providers: []
            })
                .overrideTemplate(GrupoContaAzulComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrupoContaAzulComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupoContaAzulService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GrupoContaAzul(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.grupoContaAzuls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

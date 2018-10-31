/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContaAzulComponent } from 'app/entities/plano-conta-azul/plano-conta-azul.component';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/plano-conta-azul.service';
import { PlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';

describe('Component Tests', () => {
    describe('PlanoContaAzul Management Component', () => {
        let comp: PlanoContaAzulComponent;
        let fixture: ComponentFixture<PlanoContaAzulComponent>;
        let service: PlanoContaAzulService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContaAzulComponent],
                providers: []
            })
                .overrideTemplate(PlanoContaAzulComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanoContaAzulComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoContaAzulService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PlanoContaAzul(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.planoContaAzuls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

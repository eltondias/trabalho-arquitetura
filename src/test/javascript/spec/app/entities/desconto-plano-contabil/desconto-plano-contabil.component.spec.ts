/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { DescontoPlanoContabilComponent } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.component';
import { DescontoPlanoContabilService } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.service';
import { DescontoPlanoContabil } from 'app/shared/model/desconto-plano-contabil.model';

describe('Component Tests', () => {
    describe('DescontoPlanoContabil Management Component', () => {
        let comp: DescontoPlanoContabilComponent;
        let fixture: ComponentFixture<DescontoPlanoContabilComponent>;
        let service: DescontoPlanoContabilService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [DescontoPlanoContabilComponent],
                providers: []
            })
                .overrideTemplate(DescontoPlanoContabilComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DescontoPlanoContabilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DescontoPlanoContabilService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DescontoPlanoContabil(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.descontoPlanoContabils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

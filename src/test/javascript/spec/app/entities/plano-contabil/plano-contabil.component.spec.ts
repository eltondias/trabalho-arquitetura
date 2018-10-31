/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContabilComponent } from 'app/entities/plano-contabil/plano-contabil.component';
import { PlanoContabilService } from 'app/entities/plano-contabil/plano-contabil.service';
import { PlanoContabil } from 'app/shared/model/plano-contabil.model';

describe('Component Tests', () => {
    describe('PlanoContabil Management Component', () => {
        let comp: PlanoContabilComponent;
        let fixture: ComponentFixture<PlanoContabilComponent>;
        let service: PlanoContabilService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContabilComponent],
                providers: []
            })
                .overrideTemplate(PlanoContabilComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanoContabilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoContabilService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PlanoContabil(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.planoContabils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

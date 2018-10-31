/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { EnquadramentoComponent } from 'app/entities/enquadramento/enquadramento.component';
import { EnquadramentoService } from 'app/entities/enquadramento/enquadramento.service';
import { Enquadramento } from 'app/shared/model/enquadramento.model';

describe('Component Tests', () => {
    describe('Enquadramento Management Component', () => {
        let comp: EnquadramentoComponent;
        let fixture: ComponentFixture<EnquadramentoComponent>;
        let service: EnquadramentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [EnquadramentoComponent],
                providers: []
            })
                .overrideTemplate(EnquadramentoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnquadramentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnquadramentoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Enquadramento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.enquadramentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

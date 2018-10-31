/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { RamoComponent } from 'app/entities/ramo/ramo.component';
import { RamoService } from 'app/entities/ramo/ramo.service';
import { Ramo } from 'app/shared/model/ramo.model';

describe('Component Tests', () => {
    describe('Ramo Management Component', () => {
        let comp: RamoComponent;
        let fixture: ComponentFixture<RamoComponent>;
        let service: RamoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [RamoComponent],
                providers: []
            })
                .overrideTemplate(RamoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RamoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RamoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Ramo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ramos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

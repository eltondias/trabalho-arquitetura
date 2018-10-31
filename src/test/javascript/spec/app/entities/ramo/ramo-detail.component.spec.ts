/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { RamoDetailComponent } from 'app/entities/ramo/ramo-detail.component';
import { Ramo } from 'app/shared/model/ramo.model';

describe('Component Tests', () => {
    describe('Ramo Management Detail Component', () => {
        let comp: RamoDetailComponent;
        let fixture: ComponentFixture<RamoDetailComponent>;
        const route = ({ data: of({ ramo: new Ramo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [RamoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RamoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RamoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ramo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

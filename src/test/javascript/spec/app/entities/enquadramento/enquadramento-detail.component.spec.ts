/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { EnquadramentoDetailComponent } from 'app/entities/enquadramento/enquadramento-detail.component';
import { Enquadramento } from 'app/shared/model/enquadramento.model';

describe('Component Tests', () => {
    describe('Enquadramento Management Detail Component', () => {
        let comp: EnquadramentoDetailComponent;
        let fixture: ComponentFixture<EnquadramentoDetailComponent>;
        const route = ({ data: of({ enquadramento: new Enquadramento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [EnquadramentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnquadramentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnquadramentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.enquadramento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

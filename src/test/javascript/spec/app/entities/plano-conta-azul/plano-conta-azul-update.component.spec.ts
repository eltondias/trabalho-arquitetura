/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContaAzulUpdateComponent } from 'app/entities/plano-conta-azul/plano-conta-azul-update.component';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/plano-conta-azul.service';
import { PlanoContaAzul } from 'app/shared/model/plano-conta-azul.model';

describe('Component Tests', () => {
    describe('PlanoContaAzul Management Update Component', () => {
        let comp: PlanoContaAzulUpdateComponent;
        let fixture: ComponentFixture<PlanoContaAzulUpdateComponent>;
        let service: PlanoContaAzulService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContaAzulUpdateComponent]
            })
                .overrideTemplate(PlanoContaAzulUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanoContaAzulUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoContaAzulService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PlanoContaAzul(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planoContaAzul = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PlanoContaAzul();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planoContaAzul = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

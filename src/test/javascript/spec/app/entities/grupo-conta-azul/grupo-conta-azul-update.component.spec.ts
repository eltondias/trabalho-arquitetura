/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { GrupoContaAzulUpdateComponent } from 'app/entities/grupo-conta-azul/grupo-conta-azul-update.component';
import { GrupoContaAzulService } from 'app/entities/grupo-conta-azul/grupo-conta-azul.service';
import { GrupoContaAzul } from 'app/shared/model/grupo-conta-azul.model';

describe('Component Tests', () => {
    describe('GrupoContaAzul Management Update Component', () => {
        let comp: GrupoContaAzulUpdateComponent;
        let fixture: ComponentFixture<GrupoContaAzulUpdateComponent>;
        let service: GrupoContaAzulService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [GrupoContaAzulUpdateComponent]
            })
                .overrideTemplate(GrupoContaAzulUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrupoContaAzulUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupoContaAzulService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GrupoContaAzul(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupoContaAzul = entity;
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
                    const entity = new GrupoContaAzul();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupoContaAzul = entity;
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

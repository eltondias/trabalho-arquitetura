/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoEmpresaUpdateComponent } from 'app/entities/plano-empresa/plano-empresa-update.component';
import { PlanoEmpresaService } from 'app/entities/plano-empresa/plano-empresa.service';
import { PlanoEmpresa } from 'app/shared/model/plano-empresa.model';

describe('Component Tests', () => {
    describe('PlanoEmpresa Management Update Component', () => {
        let comp: PlanoEmpresaUpdateComponent;
        let fixture: ComponentFixture<PlanoEmpresaUpdateComponent>;
        let service: PlanoEmpresaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoEmpresaUpdateComponent]
            })
                .overrideTemplate(PlanoEmpresaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanoEmpresaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoEmpresaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PlanoEmpresa(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planoEmpresa = entity;
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
                    const entity = new PlanoEmpresa();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planoEmpresa = entity;
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

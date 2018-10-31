/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { DescontoGrupoUpdateComponent } from 'app/entities/desconto-grupo/desconto-grupo-update.component';
import { DescontoGrupoService } from 'app/entities/desconto-grupo/desconto-grupo.service';
import { DescontoGrupo } from 'app/shared/model/desconto-grupo.model';

describe('Component Tests', () => {
    describe('DescontoGrupo Management Update Component', () => {
        let comp: DescontoGrupoUpdateComponent;
        let fixture: ComponentFixture<DescontoGrupoUpdateComponent>;
        let service: DescontoGrupoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [DescontoGrupoUpdateComponent]
            })
                .overrideTemplate(DescontoGrupoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DescontoGrupoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DescontoGrupoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DescontoGrupo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.descontoGrupo = entity;
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
                    const entity = new DescontoGrupo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.descontoGrupo = entity;
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

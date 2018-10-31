/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { PlanoContabilUpdateComponent } from 'app/entities/plano-contabil/plano-contabil-update.component';
import { PlanoContabilService } from 'app/entities/plano-contabil/plano-contabil.service';
import { PlanoContabil } from 'app/shared/model/plano-contabil.model';

describe('Component Tests', () => {
    describe('PlanoContabil Management Update Component', () => {
        let comp: PlanoContabilUpdateComponent;
        let fixture: ComponentFixture<PlanoContabilUpdateComponent>;
        let service: PlanoContabilService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [PlanoContabilUpdateComponent]
            })
                .overrideTemplate(PlanoContabilUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanoContabilUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanoContabilService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PlanoContabil(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planoContabil = entity;
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
                    const entity = new PlanoContabil();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planoContabil = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TrabalhoarquiteturaTestModule } from '../../../test.module';
import { RamoUpdateComponent } from 'app/entities/ramo/ramo-update.component';
import { RamoService } from 'app/entities/ramo/ramo.service';
import { Ramo } from 'app/shared/model/ramo.model';

describe('Component Tests', () => {
    describe('Ramo Management Update Component', () => {
        let comp: RamoUpdateComponent;
        let fixture: ComponentFixture<RamoUpdateComponent>;
        let service: RamoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TrabalhoarquiteturaTestModule],
                declarations: [RamoUpdateComponent]
            })
                .overrideTemplate(RamoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RamoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RamoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Ramo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ramo = entity;
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
                    const entity = new Ramo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ramo = entity;
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

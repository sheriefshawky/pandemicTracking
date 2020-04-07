import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSumbissionUpdateComponent } from 'app/entities/symptoms-sumbission/symptoms-sumbission-update.component';
import { SymptomsSumbissionService } from 'app/entities/symptoms-sumbission/symptoms-sumbission.service';
import { SymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

describe('Component Tests', () => {
  describe('SymptomsSumbission Management Update Component', () => {
    let comp: SymptomsSumbissionUpdateComponent;
    let fixture: ComponentFixture<SymptomsSumbissionUpdateComponent>;
    let service: SymptomsSumbissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSumbissionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SymptomsSumbissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomsSumbissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomsSumbissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SymptomsSumbission(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SymptomsSumbission();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

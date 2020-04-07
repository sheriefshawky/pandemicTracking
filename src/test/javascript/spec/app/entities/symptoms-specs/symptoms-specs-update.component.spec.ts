import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSpecsUpdateComponent } from 'app/entities/symptoms-specs/symptoms-specs-update.component';
import { SymptomsSpecsService } from 'app/entities/symptoms-specs/symptoms-specs.service';
import { SymptomsSpecs } from 'app/shared/model/symptoms-specs.model';

describe('Component Tests', () => {
  describe('SymptomsSpecs Management Update Component', () => {
    let comp: SymptomsSpecsUpdateComponent;
    let fixture: ComponentFixture<SymptomsSpecsUpdateComponent>;
    let service: SymptomsSpecsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSpecsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SymptomsSpecsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomsSpecsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomsSpecsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SymptomsSpecs(123);
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
        const entity = new SymptomsSpecs();
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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSubDetailsUpdateComponent } from 'app/entities/symptoms-sub-details/symptoms-sub-details-update.component';
import { SymptomsSubDetailsService } from 'app/entities/symptoms-sub-details/symptoms-sub-details.service';
import { SymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

describe('Component Tests', () => {
  describe('SymptomsSubDetails Management Update Component', () => {
    let comp: SymptomsSubDetailsUpdateComponent;
    let fixture: ComponentFixture<SymptomsSubDetailsUpdateComponent>;
    let service: SymptomsSubDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSubDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SymptomsSubDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomsSubDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomsSubDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SymptomsSubDetails(123);
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
        const entity = new SymptomsSubDetails();
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

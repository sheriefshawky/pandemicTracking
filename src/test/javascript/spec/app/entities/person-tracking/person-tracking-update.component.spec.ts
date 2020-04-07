import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { PersonTrackingUpdateComponent } from 'app/entities/person-tracking/person-tracking-update.component';
import { PersonTrackingService } from 'app/entities/person-tracking/person-tracking.service';
import { PersonTracking } from 'app/shared/model/person-tracking.model';

describe('Component Tests', () => {
  describe('PersonTracking Management Update Component', () => {
    let comp: PersonTrackingUpdateComponent;
    let fixture: ComponentFixture<PersonTrackingUpdateComponent>;
    let service: PersonTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [PersonTrackingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonTrackingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonTrackingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonTrackingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonTracking(123);
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
        const entity = new PersonTracking();
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

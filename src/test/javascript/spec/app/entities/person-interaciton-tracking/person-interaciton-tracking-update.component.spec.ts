import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { PersonInteracitonTrackingUpdateComponent } from 'app/entities/person-interaciton-tracking/person-interaciton-tracking-update.component';
import { PersonInteracitonTrackingService } from 'app/entities/person-interaciton-tracking/person-interaciton-tracking.service';
import { PersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';

describe('Component Tests', () => {
  describe('PersonInteracitonTracking Management Update Component', () => {
    let comp: PersonInteracitonTrackingUpdateComponent;
    let fixture: ComponentFixture<PersonInteracitonTrackingUpdateComponent>;
    let service: PersonInteracitonTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [PersonInteracitonTrackingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonInteracitonTrackingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonInteracitonTrackingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonInteracitonTrackingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonInteracitonTracking(123);
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
        const entity = new PersonInteracitonTracking();
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

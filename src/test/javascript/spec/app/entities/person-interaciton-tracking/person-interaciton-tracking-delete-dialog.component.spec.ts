import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PandemicTrackingTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { PersonInteracitonTrackingDeleteDialogComponent } from 'app/entities/person-interaciton-tracking/person-interaciton-tracking-delete-dialog.component';
import { PersonInteracitonTrackingService } from 'app/entities/person-interaciton-tracking/person-interaciton-tracking.service';

describe('Component Tests', () => {
  describe('PersonInteracitonTracking Management Delete Component', () => {
    let comp: PersonInteracitonTrackingDeleteDialogComponent;
    let fixture: ComponentFixture<PersonInteracitonTrackingDeleteDialogComponent>;
    let service: PersonInteracitonTrackingService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [PersonInteracitonTrackingDeleteDialogComponent]
      })
        .overrideTemplate(PersonInteracitonTrackingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonInteracitonTrackingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonInteracitonTrackingService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});

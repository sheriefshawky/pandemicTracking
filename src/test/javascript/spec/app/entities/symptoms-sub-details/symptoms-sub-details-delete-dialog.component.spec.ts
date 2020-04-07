import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PandemicTrackingTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SymptomsSubDetailsDeleteDialogComponent } from 'app/entities/symptoms-sub-details/symptoms-sub-details-delete-dialog.component';
import { SymptomsSubDetailsService } from 'app/entities/symptoms-sub-details/symptoms-sub-details.service';

describe('Component Tests', () => {
  describe('SymptomsSubDetails Management Delete Component', () => {
    let comp: SymptomsSubDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<SymptomsSubDetailsDeleteDialogComponent>;
    let service: SymptomsSubDetailsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSubDetailsDeleteDialogComponent]
      })
        .overrideTemplate(SymptomsSubDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomsSubDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomsSubDetailsService);
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

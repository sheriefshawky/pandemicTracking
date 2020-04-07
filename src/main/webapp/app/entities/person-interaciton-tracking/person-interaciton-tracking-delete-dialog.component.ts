import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';
import { PersonInteracitonTrackingService } from './person-interaciton-tracking.service';

@Component({
  templateUrl: './person-interaciton-tracking-delete-dialog.component.html'
})
export class PersonInteracitonTrackingDeleteDialogComponent {
  personInteracitonTracking?: IPersonInteracitonTracking;

  constructor(
    protected personInteracitonTrackingService: PersonInteracitonTrackingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personInteracitonTrackingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personInteracitonTrackingListModification');
      this.activeModal.close();
    });
  }
}

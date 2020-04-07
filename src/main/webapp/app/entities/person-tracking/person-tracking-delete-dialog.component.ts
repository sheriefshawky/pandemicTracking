import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonTracking } from 'app/shared/model/person-tracking.model';
import { PersonTrackingService } from './person-tracking.service';

@Component({
  templateUrl: './person-tracking-delete-dialog.component.html'
})
export class PersonTrackingDeleteDialogComponent {
  personTracking?: IPersonTracking;

  constructor(
    protected personTrackingService: PersonTrackingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personTrackingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personTrackingListModification');
      this.activeModal.close();
    });
  }
}

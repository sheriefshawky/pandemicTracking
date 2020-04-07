import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';
import { SymptomsSubDetailsService } from './symptoms-sub-details.service';

@Component({
  templateUrl: './symptoms-sub-details-delete-dialog.component.html'
})
export class SymptomsSubDetailsDeleteDialogComponent {
  symptomsSubDetails?: ISymptomsSubDetails;

  constructor(
    protected symptomsSubDetailsService: SymptomsSubDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.symptomsSubDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('symptomsSubDetailsListModification');
      this.activeModal.close();
    });
  }
}

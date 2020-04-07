import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISymptomsSpecs } from 'app/shared/model/symptoms-specs.model';
import { SymptomsSpecsService } from './symptoms-specs.service';

@Component({
  templateUrl: './symptoms-specs-delete-dialog.component.html'
})
export class SymptomsSpecsDeleteDialogComponent {
  symptomsSpecs?: ISymptomsSpecs;

  constructor(
    protected symptomsSpecsService: SymptomsSpecsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.symptomsSpecsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('symptomsSpecsListModification');
      this.activeModal.close();
    });
  }
}

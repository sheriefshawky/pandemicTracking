import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';
import { SymptomsSumbissionService } from './symptoms-sumbission.service';

@Component({
  templateUrl: './symptoms-sumbission-delete-dialog.component.html'
})
export class SymptomsSumbissionDeleteDialogComponent {
  symptomsSumbission?: ISymptomsSumbission;

  constructor(
    protected symptomsSumbissionService: SymptomsSumbissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.symptomsSumbissionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('symptomsSumbissionListModification');
      this.activeModal.close();
    });
  }
}

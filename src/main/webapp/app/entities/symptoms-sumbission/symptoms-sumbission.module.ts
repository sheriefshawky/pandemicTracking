import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PandemicTrackingSharedModule } from 'app/shared/shared.module';
import { SymptomsSumbissionComponent } from './symptoms-sumbission.component';
import { SymptomsSumbissionDetailComponent } from './symptoms-sumbission-detail.component';
import { SymptomsSumbissionUpdateComponent } from './symptoms-sumbission-update.component';
import { SymptomsSumbissionDeleteDialogComponent } from './symptoms-sumbission-delete-dialog.component';
import { symptomsSumbissionRoute } from './symptoms-sumbission.route';

@NgModule({
  imports: [PandemicTrackingSharedModule, RouterModule.forChild(symptomsSumbissionRoute)],
  declarations: [
    SymptomsSumbissionComponent,
    SymptomsSumbissionDetailComponent,
    SymptomsSumbissionUpdateComponent,
    SymptomsSumbissionDeleteDialogComponent
  ],
  entryComponents: [SymptomsSumbissionDeleteDialogComponent]
})
export class PandemicTrackingSymptomsSumbissionModule {}

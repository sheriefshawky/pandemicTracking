import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PandemicTrackingSharedModule } from 'app/shared/shared.module';
import { SymptomsSubDetailsComponent } from './symptoms-sub-details.component';
import { SymptomsSubDetailsDetailComponent } from './symptoms-sub-details-detail.component';
import { SymptomsSubDetailsUpdateComponent } from './symptoms-sub-details-update.component';
import { SymptomsSubDetailsDeleteDialogComponent } from './symptoms-sub-details-delete-dialog.component';
import { symptomsSubDetailsRoute } from './symptoms-sub-details.route';

@NgModule({
  imports: [PandemicTrackingSharedModule, RouterModule.forChild(symptomsSubDetailsRoute)],
  declarations: [
    SymptomsSubDetailsComponent,
    SymptomsSubDetailsDetailComponent,
    SymptomsSubDetailsUpdateComponent,
    SymptomsSubDetailsDeleteDialogComponent
  ],
  entryComponents: [SymptomsSubDetailsDeleteDialogComponent]
})
export class PandemicTrackingSymptomsSubDetailsModule {}

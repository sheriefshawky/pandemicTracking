import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PandemicTrackingSharedModule } from 'app/shared/shared.module';
import { PersonTrackingComponent } from './person-tracking.component';
import { PersonTrackingDetailComponent } from './person-tracking-detail.component';
import { PersonTrackingUpdateComponent } from './person-tracking-update.component';
import { PersonTrackingDeleteDialogComponent } from './person-tracking-delete-dialog.component';
import { personTrackingRoute } from './person-tracking.route';

@NgModule({
  imports: [PandemicTrackingSharedModule, RouterModule.forChild(personTrackingRoute)],
  declarations: [
    PersonTrackingComponent,
    PersonTrackingDetailComponent,
    PersonTrackingUpdateComponent,
    PersonTrackingDeleteDialogComponent
  ],
  entryComponents: [PersonTrackingDeleteDialogComponent]
})
export class PandemicTrackingPersonTrackingModule {}

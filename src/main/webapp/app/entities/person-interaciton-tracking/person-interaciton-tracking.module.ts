import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PandemicTrackingSharedModule } from 'app/shared/shared.module';
import { PersonInteracitonTrackingComponent } from './person-interaciton-tracking.component';
import { PersonInteracitonTrackingDetailComponent } from './person-interaciton-tracking-detail.component';
import { PersonInteracitonTrackingUpdateComponent } from './person-interaciton-tracking-update.component';
import { PersonInteracitonTrackingDeleteDialogComponent } from './person-interaciton-tracking-delete-dialog.component';
import { personInteracitonTrackingRoute } from './person-interaciton-tracking.route';

@NgModule({
  imports: [PandemicTrackingSharedModule, RouterModule.forChild(personInteracitonTrackingRoute)],
  declarations: [
    PersonInteracitonTrackingComponent,
    PersonInteracitonTrackingDetailComponent,
    PersonInteracitonTrackingUpdateComponent,
    PersonInteracitonTrackingDeleteDialogComponent
  ],
  entryComponents: [PersonInteracitonTrackingDeleteDialogComponent]
})
export class PandemicTrackingPersonInteracitonTrackingModule {}

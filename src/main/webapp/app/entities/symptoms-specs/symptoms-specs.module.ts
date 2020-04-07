import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PandemicTrackingSharedModule } from 'app/shared/shared.module';
import { SymptomsSpecsComponent } from './symptoms-specs.component';
import { SymptomsSpecsDetailComponent } from './symptoms-specs-detail.component';
import { SymptomsSpecsUpdateComponent } from './symptoms-specs-update.component';
import { SymptomsSpecsDeleteDialogComponent } from './symptoms-specs-delete-dialog.component';
import { symptomsSpecsRoute } from './symptoms-specs.route';

@NgModule({
  imports: [PandemicTrackingSharedModule, RouterModule.forChild(symptomsSpecsRoute)],
  declarations: [SymptomsSpecsComponent, SymptomsSpecsDetailComponent, SymptomsSpecsUpdateComponent, SymptomsSpecsDeleteDialogComponent],
  entryComponents: [SymptomsSpecsDeleteDialogComponent]
})
export class PandemicTrackingSymptomsSpecsModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.PandemicTrackingPersonModule)
      },
      {
        path: 'person-tracking',
        loadChildren: () => import('./person-tracking/person-tracking.module').then(m => m.PandemicTrackingPersonTrackingModule)
      },
      {
        path: 'person-interaciton-tracking',
        loadChildren: () =>
          import('./person-interaciton-tracking/person-interaciton-tracking.module').then(
            m => m.PandemicTrackingPersonInteracitonTrackingModule
          )
      },
      {
        path: 'symptoms-specs',
        loadChildren: () => import('./symptoms-specs/symptoms-specs.module').then(m => m.PandemicTrackingSymptomsSpecsModule)
      },
      {
        path: 'symptoms-sumbission',
        loadChildren: () => import('./symptoms-sumbission/symptoms-sumbission.module').then(m => m.PandemicTrackingSymptomsSumbissionModule)
      },
      {
        path: 'symptoms-sub-details',
        loadChildren: () =>
          import('./symptoms-sub-details/symptoms-sub-details.module').then(m => m.PandemicTrackingSymptomsSubDetailsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PandemicTrackingEntityModule {}

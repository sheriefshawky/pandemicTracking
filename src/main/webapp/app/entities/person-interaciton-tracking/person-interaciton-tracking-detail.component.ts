import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';

@Component({
  selector: 'jhi-person-interaciton-tracking-detail',
  templateUrl: './person-interaciton-tracking-detail.component.html'
})
export class PersonInteracitonTrackingDetailComponent implements OnInit {
  personInteracitonTracking: IPersonInteracitonTracking | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInteracitonTracking }) => (this.personInteracitonTracking = personInteracitonTracking));
  }

  previousState(): void {
    window.history.back();
  }
}

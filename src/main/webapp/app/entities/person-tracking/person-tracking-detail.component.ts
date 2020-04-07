import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonTracking } from 'app/shared/model/person-tracking.model';

@Component({
  selector: 'jhi-person-tracking-detail',
  templateUrl: './person-tracking-detail.component.html'
})
export class PersonTrackingDetailComponent implements OnInit {
  personTracking: IPersonTracking | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personTracking }) => (this.personTracking = personTracking));
  }

  previousState(): void {
    window.history.back();
  }
}

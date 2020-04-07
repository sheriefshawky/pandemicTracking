import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

@Component({
  selector: 'jhi-symptoms-sub-details-detail',
  templateUrl: './symptoms-sub-details-detail.component.html'
})
export class SymptomsSubDetailsDetailComponent implements OnInit {
  symptomsSubDetails: ISymptomsSubDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptomsSubDetails }) => (this.symptomsSubDetails = symptomsSubDetails));
  }

  previousState(): void {
    window.history.back();
  }
}

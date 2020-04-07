import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISymptomsSpecs } from 'app/shared/model/symptoms-specs.model';

@Component({
  selector: 'jhi-symptoms-specs-detail',
  templateUrl: './symptoms-specs-detail.component.html'
})
export class SymptomsSpecsDetailComponent implements OnInit {
  symptomsSpecs: ISymptomsSpecs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptomsSpecs }) => (this.symptomsSpecs = symptomsSpecs));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

@Component({
  selector: 'jhi-symptoms-sumbission-detail',
  templateUrl: './symptoms-sumbission-detail.component.html'
})
export class SymptomsSumbissionDetailComponent implements OnInit {
  symptomsSumbission: ISymptomsSumbission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptomsSumbission }) => (this.symptomsSumbission = symptomsSumbission));
  }

  previousState(): void {
    window.history.back();
  }
}

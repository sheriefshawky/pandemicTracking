import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISymptomsSumbission, SymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';
import { SymptomsSumbissionService } from './symptoms-sumbission.service';

@Component({
  selector: 'jhi-symptoms-sumbission-update',
  templateUrl: './symptoms-sumbission-update.component.html'
})
export class SymptomsSumbissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    submissionTime: [null, [Validators.required]]
  });

  constructor(
    protected symptomsSumbissionService: SymptomsSumbissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptomsSumbission }) => {
      if (!symptomsSumbission.id) {
        const today = moment().startOf('day');
        symptomsSumbission.submissionTime = today;
      }

      this.updateForm(symptomsSumbission);
    });
  }

  updateForm(symptomsSumbission: ISymptomsSumbission): void {
    this.editForm.patchValue({
      id: symptomsSumbission.id,
      userId: symptomsSumbission.userId,
      submissionTime: symptomsSumbission.submissionTime ? symptomsSumbission.submissionTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const symptomsSumbission = this.createFromForm();
    if (symptomsSumbission.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomsSumbissionService.update(symptomsSumbission));
    } else {
      this.subscribeToSaveResponse(this.symptomsSumbissionService.create(symptomsSumbission));
    }
  }

  private createFromForm(): ISymptomsSumbission {
    return {
      ...new SymptomsSumbission(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      submissionTime: this.editForm.get(['submissionTime'])!.value
        ? moment(this.editForm.get(['submissionTime'])!.value, DATE_TIME_FORMAT)
        : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptomsSumbission>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

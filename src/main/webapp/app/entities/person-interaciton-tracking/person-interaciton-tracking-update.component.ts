import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPersonInteracitonTracking, PersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';
import { PersonInteracitonTrackingService } from './person-interaciton-tracking.service';

@Component({
  selector: 'jhi-person-interaciton-tracking-update',
  templateUrl: './person-interaciton-tracking-update.component.html'
})
export class PersonInteracitonTrackingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    interactedUserId: [null, [Validators.required]],
    locationLongitude: [null, [Validators.required]],
    locationLatitude: [null, [Validators.required]],
    locationTime: []
  });

  constructor(
    protected personInteracitonTrackingService: PersonInteracitonTrackingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInteracitonTracking }) => {
      if (!personInteracitonTracking.id) {
        const today = moment().startOf('day');
        personInteracitonTracking.locationTime = today;
      }

      this.updateForm(personInteracitonTracking);
    });
  }

  updateForm(personInteracitonTracking: IPersonInteracitonTracking): void {
    this.editForm.patchValue({
      id: personInteracitonTracking.id,
      userId: personInteracitonTracking.userId,
      interactedUserId: personInteracitonTracking.interactedUserId,
      locationLongitude: personInteracitonTracking.locationLongitude,
      locationLatitude: personInteracitonTracking.locationLatitude,
      locationTime: personInteracitonTracking.locationTime ? personInteracitonTracking.locationTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personInteracitonTracking = this.createFromForm();
    if (personInteracitonTracking.id !== undefined) {
      this.subscribeToSaveResponse(this.personInteracitonTrackingService.update(personInteracitonTracking));
    } else {
      this.subscribeToSaveResponse(this.personInteracitonTrackingService.create(personInteracitonTracking));
    }
  }

  private createFromForm(): IPersonInteracitonTracking {
    return {
      ...new PersonInteracitonTracking(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      interactedUserId: this.editForm.get(['interactedUserId'])!.value,
      locationLongitude: this.editForm.get(['locationLongitude'])!.value,
      locationLatitude: this.editForm.get(['locationLatitude'])!.value,
      locationTime: this.editForm.get(['locationTime'])!.value
        ? moment(this.editForm.get(['locationTime'])!.value, DATE_TIME_FORMAT)
        : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonInteracitonTracking>>): void {
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

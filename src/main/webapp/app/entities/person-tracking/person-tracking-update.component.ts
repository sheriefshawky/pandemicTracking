import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPersonTracking, PersonTracking } from 'app/shared/model/person-tracking.model';
import { PersonTrackingService } from './person-tracking.service';

@Component({
  selector: 'jhi-person-tracking-update',
  templateUrl: './person-tracking-update.component.html'
})
export class PersonTrackingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    locationLongitude: [null, [Validators.required]],
    locationLatitude: [null, [Validators.required]],
    locationTime: []
  });

  constructor(protected personTrackingService: PersonTrackingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personTracking }) => {
      if (!personTracking.id) {
        const today = moment().startOf('day');
        personTracking.locationTime = today;
      }

      this.updateForm(personTracking);
    });
  }

  updateForm(personTracking: IPersonTracking): void {
    this.editForm.patchValue({
      id: personTracking.id,
      userId: personTracking.userId,
      locationLongitude: personTracking.locationLongitude,
      locationLatitude: personTracking.locationLatitude,
      locationTime: personTracking.locationTime ? personTracking.locationTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personTracking = this.createFromForm();
    if (personTracking.id !== undefined) {
      this.subscribeToSaveResponse(this.personTrackingService.update(personTracking));
    } else {
      this.subscribeToSaveResponse(this.personTrackingService.create(personTracking));
    }
  }

  private createFromForm(): IPersonTracking {
    return {
      ...new PersonTracking(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      locationLongitude: this.editForm.get(['locationLongitude'])!.value,
      locationLatitude: this.editForm.get(['locationLatitude'])!.value,
      locationTime: this.editForm.get(['locationTime'])!.value
        ? moment(this.editForm.get(['locationTime'])!.value, DATE_TIME_FORMAT)
        : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonTracking>>): void {
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

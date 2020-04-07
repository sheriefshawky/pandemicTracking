import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISymptomsSubDetails, SymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';
import { SymptomsSubDetailsService } from './symptoms-sub-details.service';
import { ISymptomsSpecs } from 'app/shared/model/symptoms-specs.model';
import { SymptomsSpecsService } from 'app/entities/symptoms-specs/symptoms-specs.service';
import { ISymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';
import { SymptomsSumbissionService } from 'app/entities/symptoms-sumbission/symptoms-sumbission.service';

type SelectableEntity = ISymptomsSpecs | ISymptomsSumbission;

@Component({
  selector: 'jhi-symptoms-sub-details-update',
  templateUrl: './symptoms-sub-details-update.component.html'
})
export class SymptomsSubDetailsUpdateComponent implements OnInit {
  isSaving = false;
  symptomspecs: ISymptomsSpecs[] = [];
  symptomssumbissions: ISymptomsSumbission[] = [];

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.required]],
    symptomSpec: [],
    symptomsSumbission: []
  });

  constructor(
    protected symptomsSubDetailsService: SymptomsSubDetailsService,
    protected symptomsSpecsService: SymptomsSpecsService,
    protected symptomsSumbissionService: SymptomsSumbissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptomsSubDetails }) => {
      this.updateForm(symptomsSubDetails);

      this.symptomsSpecsService
        .query({ filter: 'symptomssubdetails-is-null' })
        .pipe(
          map((res: HttpResponse<ISymptomsSpecs[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISymptomsSpecs[]) => {
          if (!symptomsSubDetails.symptomSpec || !symptomsSubDetails.symptomSpec.id) {
            this.symptomspecs = resBody;
          } else {
            this.symptomsSpecsService
              .find(symptomsSubDetails.symptomSpec.id)
              .pipe(
                map((subRes: HttpResponse<ISymptomsSpecs>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISymptomsSpecs[]) => (this.symptomspecs = concatRes));
          }
        });

      this.symptomsSumbissionService
        .query()
        .subscribe((res: HttpResponse<ISymptomsSumbission[]>) => (this.symptomssumbissions = res.body || []));
    });
  }

  updateForm(symptomsSubDetails: ISymptomsSubDetails): void {
    this.editForm.patchValue({
      id: symptomsSubDetails.id,
      value: symptomsSubDetails.value,
      symptomSpec: symptomsSubDetails.symptomSpec,
      symptomsSumbission: symptomsSubDetails.symptomsSumbission
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const symptomsSubDetails = this.createFromForm();
    if (symptomsSubDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomsSubDetailsService.update(symptomsSubDetails));
    } else {
      this.subscribeToSaveResponse(this.symptomsSubDetailsService.create(symptomsSubDetails));
    }
  }

  private createFromForm(): ISymptomsSubDetails {
    return {
      ...new SymptomsSubDetails(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      symptomSpec: this.editForm.get(['symptomSpec'])!.value,
      symptomsSumbission: this.editForm.get(['symptomsSumbission'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptomsSubDetails>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISymptomsSpecs, SymptomsSpecs } from 'app/shared/model/symptoms-specs.model';
import { SymptomsSpecsService } from './symptoms-specs.service';

@Component({
  selector: 'jhi-symptoms-specs-update',
  templateUrl: './symptoms-specs-update.component.html'
})
export class SymptomsSpecsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    descriptionAr: [null, [Validators.required]],
    descriptionEn: [null, [Validators.required]],
    specType: [null, [Validators.required]]
  });

  constructor(protected symptomsSpecsService: SymptomsSpecsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptomsSpecs }) => {
      this.updateForm(symptomsSpecs);
    });
  }

  updateForm(symptomsSpecs: ISymptomsSpecs): void {
    this.editForm.patchValue({
      id: symptomsSpecs.id,
      code: symptomsSpecs.code,
      descriptionAr: symptomsSpecs.descriptionAr,
      descriptionEn: symptomsSpecs.descriptionEn,
      specType: symptomsSpecs.specType
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const symptomsSpecs = this.createFromForm();
    if (symptomsSpecs.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomsSpecsService.update(symptomsSpecs));
    } else {
      this.subscribeToSaveResponse(this.symptomsSpecsService.create(symptomsSpecs));
    }
  }

  private createFromForm(): ISymptomsSpecs {
    return {
      ...new SymptomsSpecs(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      descriptionAr: this.editForm.get(['descriptionAr'])!.value,
      descriptionEn: this.editForm.get(['descriptionEn'])!.value,
      specType: this.editForm.get(['specType'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptomsSpecs>>): void {
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

import { ISymptomsSpecs } from 'app/shared/model/symptoms-specs.model';
import { ISymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

export interface ISymptomsSubDetails {
  id?: number;
  value?: string;
  symptomSpec?: ISymptomsSpecs;
  symptomsSumbission?: ISymptomsSumbission;
}

export class SymptomsSubDetails implements ISymptomsSubDetails {
  constructor(
    public id?: number,
    public value?: string,
    public symptomSpec?: ISymptomsSpecs,
    public symptomsSumbission?: ISymptomsSumbission
  ) {}
}

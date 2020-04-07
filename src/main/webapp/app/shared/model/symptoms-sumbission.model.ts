import { Moment } from 'moment';
import { ISymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

export interface ISymptomsSumbission {
  id?: number;
  userId?: number;
  submissionTime?: Moment;
  subDetails?: ISymptomsSubDetails[];
}

export class SymptomsSumbission implements ISymptomsSumbission {
  constructor(public id?: number, public userId?: number, public submissionTime?: Moment, public subDetails?: ISymptomsSubDetails[]) {}
}

import { Moment } from 'moment';

export interface IPersonInteracitonTracking {
  id?: number;
  userId?: number;
  interactedUserId?: number;
  locationLongitude?: number;
  locationLatitude?: number;
  locationTime?: Moment;
}

export class PersonInteracitonTracking implements IPersonInteracitonTracking {
  constructor(
    public id?: number,
    public userId?: number,
    public interactedUserId?: number,
    public locationLongitude?: number,
    public locationLatitude?: number,
    public locationTime?: Moment
  ) {}
}

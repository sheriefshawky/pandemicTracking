import { Moment } from 'moment';

export interface IPersonTracking {
  id?: number;
  userId?: number;
  locationLongitude?: number;
  locationLatitude?: number;
  locationTime?: Moment;
}

export class PersonTracking implements IPersonTracking {
  constructor(
    public id?: number,
    public userId?: number,
    public locationLongitude?: number,
    public locationLatitude?: number,
    public locationTime?: Moment
  ) {}
}

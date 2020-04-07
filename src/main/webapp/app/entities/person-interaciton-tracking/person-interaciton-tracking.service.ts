import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';

type EntityResponseType = HttpResponse<IPersonInteracitonTracking>;
type EntityArrayResponseType = HttpResponse<IPersonInteracitonTracking[]>;

@Injectable({ providedIn: 'root' })
export class PersonInteracitonTrackingService {
  public resourceUrl = SERVER_API_URL + 'api/person-interaciton-trackings';

  constructor(protected http: HttpClient) {}

  create(personInteracitonTracking: IPersonInteracitonTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInteracitonTracking);
    return this.http
      .post<IPersonInteracitonTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personInteracitonTracking: IPersonInteracitonTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInteracitonTracking);
    return this.http
      .put<IPersonInteracitonTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonInteracitonTracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonInteracitonTracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personInteracitonTracking: IPersonInteracitonTracking): IPersonInteracitonTracking {
    const copy: IPersonInteracitonTracking = Object.assign({}, personInteracitonTracking, {
      locationTime:
        personInteracitonTracking.locationTime && personInteracitonTracking.locationTime.isValid()
          ? personInteracitonTracking.locationTime.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.locationTime = res.body.locationTime ? moment(res.body.locationTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personInteracitonTracking: IPersonInteracitonTracking) => {
        personInteracitonTracking.locationTime = personInteracitonTracking.locationTime
          ? moment(personInteracitonTracking.locationTime)
          : undefined;
      });
    }
    return res;
  }
}

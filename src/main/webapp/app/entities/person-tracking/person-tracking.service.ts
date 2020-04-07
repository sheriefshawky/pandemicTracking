import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonTracking } from 'app/shared/model/person-tracking.model';

type EntityResponseType = HttpResponse<IPersonTracking>;
type EntityArrayResponseType = HttpResponse<IPersonTracking[]>;

@Injectable({ providedIn: 'root' })
export class PersonTrackingService {
  public resourceUrl = SERVER_API_URL + 'api/person-trackings';

  constructor(protected http: HttpClient) {}

  create(personTracking: IPersonTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personTracking);
    return this.http
      .post<IPersonTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personTracking: IPersonTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personTracking);
    return this.http
      .put<IPersonTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonTracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonTracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personTracking: IPersonTracking): IPersonTracking {
    const copy: IPersonTracking = Object.assign({}, personTracking, {
      locationTime: personTracking.locationTime && personTracking.locationTime.isValid() ? personTracking.locationTime.toJSON() : undefined
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
      res.body.forEach((personTracking: IPersonTracking) => {
        personTracking.locationTime = personTracking.locationTime ? moment(personTracking.locationTime) : undefined;
      });
    }
    return res;
  }
}

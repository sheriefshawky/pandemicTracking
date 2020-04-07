import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

type EntityResponseType = HttpResponse<ISymptomsSumbission>;
type EntityArrayResponseType = HttpResponse<ISymptomsSumbission[]>;

@Injectable({ providedIn: 'root' })
export class SymptomsSumbissionService {
  public resourceUrl = SERVER_API_URL + 'api/symptoms-sumbissions';

  constructor(protected http: HttpClient) {}

  create(symptomsSumbission: ISymptomsSumbission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(symptomsSumbission);
    return this.http
      .post<ISymptomsSumbission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(symptomsSumbission: ISymptomsSumbission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(symptomsSumbission);
    return this.http
      .put<ISymptomsSumbission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISymptomsSumbission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISymptomsSumbission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(symptomsSumbission: ISymptomsSumbission): ISymptomsSumbission {
    const copy: ISymptomsSumbission = Object.assign({}, symptomsSumbission, {
      submissionTime:
        symptomsSumbission.submissionTime && symptomsSumbission.submissionTime.isValid()
          ? symptomsSumbission.submissionTime.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.submissionTime = res.body.submissionTime ? moment(res.body.submissionTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((symptomsSumbission: ISymptomsSumbission) => {
        symptomsSumbission.submissionTime = symptomsSumbission.submissionTime ? moment(symptomsSumbission.submissionTime) : undefined;
      });
    }
    return res;
  }
}

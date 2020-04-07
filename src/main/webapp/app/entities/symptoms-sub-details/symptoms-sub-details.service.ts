import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

type EntityResponseType = HttpResponse<ISymptomsSubDetails>;
type EntityArrayResponseType = HttpResponse<ISymptomsSubDetails[]>;

@Injectable({ providedIn: 'root' })
export class SymptomsSubDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/symptoms-sub-details';

  constructor(protected http: HttpClient) {}

  create(symptomsSubDetails: ISymptomsSubDetails): Observable<EntityResponseType> {
    return this.http.post<ISymptomsSubDetails>(this.resourceUrl, symptomsSubDetails, { observe: 'response' });
  }

  update(symptomsSubDetails: ISymptomsSubDetails): Observable<EntityResponseType> {
    return this.http.put<ISymptomsSubDetails>(this.resourceUrl, symptomsSubDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISymptomsSubDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISymptomsSubDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

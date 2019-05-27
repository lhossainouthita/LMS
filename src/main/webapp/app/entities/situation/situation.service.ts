import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISituation } from 'app/shared/model/situation.model';

type EntityResponseType = HttpResponse<ISituation>;
type EntityArrayResponseType = HttpResponse<ISituation[]>;

@Injectable({ providedIn: 'root' })
export class SituationService {
  public resourceUrl = SERVER_API_URL + 'api/situations';

  constructor(protected http: HttpClient) {}

  create(situation: ISituation): Observable<EntityResponseType> {
    return this.http.post<ISituation>(this.resourceUrl, situation, { observe: 'response' });
  }

  update(situation: ISituation): Observable<EntityResponseType> {
    return this.http.put<ISituation>(this.resourceUrl, situation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISituation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISituation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

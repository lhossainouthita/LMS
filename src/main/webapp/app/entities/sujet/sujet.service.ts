import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISujet } from 'app/shared/model/sujet.model';

type EntityResponseType = HttpResponse<ISujet>;
type EntityArrayResponseType = HttpResponse<ISujet[]>;

@Injectable({ providedIn: 'root' })
export class SujetService {
  public resourceUrl = SERVER_API_URL + 'api/sujets';

  constructor(protected http: HttpClient) {}

  create(sujet: ISujet): Observable<EntityResponseType> {
    return this.http.post<ISujet>(this.resourceUrl, sujet, { observe: 'response' });
  }

  update(sujet: ISujet): Observable<EntityResponseType> {
    return this.http.put<ISujet>(this.resourceUrl, sujet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISujet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISujet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

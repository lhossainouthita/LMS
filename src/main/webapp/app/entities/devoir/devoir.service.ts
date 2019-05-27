import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDevoir } from 'app/shared/model/devoir.model';

type EntityResponseType = HttpResponse<IDevoir>;
type EntityArrayResponseType = HttpResponse<IDevoir[]>;

@Injectable({ providedIn: 'root' })
export class DevoirService {
  public resourceUrl = SERVER_API_URL + 'api/devoirs';

  constructor(protected http: HttpClient) {}

  create(devoir: IDevoir): Observable<EntityResponseType> {
    return this.http.post<IDevoir>(this.resourceUrl, devoir, { observe: 'response' });
  }

  update(devoir: IDevoir): Observable<EntityResponseType> {
    return this.http.put<IDevoir>(this.resourceUrl, devoir, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDevoir>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDevoir[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

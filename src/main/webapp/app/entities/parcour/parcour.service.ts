import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParcour } from 'app/shared/model/parcour.model';

type EntityResponseType = HttpResponse<IParcour>;
type EntityArrayResponseType = HttpResponse<IParcour[]>;

@Injectable({ providedIn: 'root' })
export class ParcourService {
  public resourceUrl = SERVER_API_URL + 'api/parcours';

  constructor(protected http: HttpClient) {}

  create(parcour: IParcour): Observable<EntityResponseType> {
    return this.http.post<IParcour>(this.resourceUrl, parcour, { observe: 'response' });
  }

  update(parcour: IParcour): Observable<EntityResponseType> {
    return this.http.put<IParcour>(this.resourceUrl, parcour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParcour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParcour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

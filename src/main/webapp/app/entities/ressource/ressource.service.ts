import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRessource } from 'app/shared/model/ressource.model';

type EntityResponseType = HttpResponse<IRessource>;
type EntityArrayResponseType = HttpResponse<IRessource[]>;

@Injectable({ providedIn: 'root' })
export class RessourceService {
  public resourceUrl = SERVER_API_URL + 'api/ressources';

  constructor(protected http: HttpClient) {}

  create(ressource: IRessource): Observable<EntityResponseType> {
    return this.http.post<IRessource>(this.resourceUrl, ressource, { observe: 'response' });
  }

  update(ressource: IRessource): Observable<EntityResponseType> {
    return this.http.put<IRessource>(this.resourceUrl, ressource, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRessource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRessource[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

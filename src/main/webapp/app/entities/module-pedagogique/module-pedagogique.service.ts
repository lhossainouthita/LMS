import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';

type EntityResponseType = HttpResponse<IModulePedagogique>;
type EntityArrayResponseType = HttpResponse<IModulePedagogique[]>;

@Injectable({ providedIn: 'root' })
export class ModulePedagogiqueService {
  public resourceUrl = SERVER_API_URL + 'api/module-pedagogiques';

  constructor(protected http: HttpClient) {}

  create(modulePedagogique: IModulePedagogique): Observable<EntityResponseType> {
    return this.http.post<IModulePedagogique>(this.resourceUrl, modulePedagogique, { observe: 'response' });
  }

  update(modulePedagogique: IModulePedagogique): Observable<EntityResponseType> {
    return this.http.put<IModulePedagogique>(this.resourceUrl, modulePedagogique, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModulePedagogique>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModulePedagogique[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

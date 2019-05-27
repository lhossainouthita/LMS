import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeriode } from 'app/shared/model/periode.model';

type EntityResponseType = HttpResponse<IPeriode>;
type EntityArrayResponseType = HttpResponse<IPeriode[]>;

@Injectable({ providedIn: 'root' })
export class PeriodeService {
  public resourceUrl = SERVER_API_URL + 'api/periodes';

  constructor(protected http: HttpClient) {}

  create(periode: IPeriode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periode);
    return this.http
      .post<IPeriode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(periode: IPeriode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periode);
    return this.http
      .put<IPeriode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPeriode>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPeriode[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(periode: IPeriode): IPeriode {
    const copy: IPeriode = Object.assign({}, periode, {
      dateDebut: periode.dateDebut != null && periode.dateDebut.isValid() ? periode.dateDebut.format(DATE_FORMAT) : null,
      dateFin: periode.dateFin != null && periode.dateFin.isValid() ? periode.dateFin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
      res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((periode: IPeriode) => {
        periode.dateDebut = periode.dateDebut != null ? moment(periode.dateDebut) : null;
        periode.dateFin = periode.dateFin != null ? moment(periode.dateFin) : null;
      });
    }
    return res;
  }
}

import { Moment } from 'moment';

export interface IPeriode {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  sujetId?: number;
}

export class Periode implements IPeriode {
  constructor(public id?: number, public dateDebut?: Moment, public dateFin?: Moment, public sujetId?: number) {}
}

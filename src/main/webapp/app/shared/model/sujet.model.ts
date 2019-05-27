import { IPeriode } from 'app/shared/model/periode.model';

export interface ISujet {
  id?: number;
  intituleSujet?: string;
  descriptionSujet?: string;
  modulePedagogiqueId?: number;
  competenceIntituleCompetence?: string;
  competenceId?: number;
  periodes?: IPeriode[];
}

export class Sujet implements ISujet {
  constructor(
    public id?: number,
    public intituleSujet?: string,
    public descriptionSujet?: string,
    public modulePedagogiqueId?: number,
    public competenceIntituleCompetence?: string,
    public competenceId?: number,
    public periodes?: IPeriode[]
  ) {}
}

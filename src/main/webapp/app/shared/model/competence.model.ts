export interface ICompetence {
  id?: number;
  codeCompetence?: string;
  intituleCompetence?: string;
  descriptionCompetence?: string;
  modulePedagogiqueId?: number;
}

export class Competence implements ICompetence {
  constructor(
    public id?: number,
    public codeCompetence?: string,
    public intituleCompetence?: string,
    public descriptionCompetence?: string,
    public modulePedagogiqueId?: number
  ) {}
}

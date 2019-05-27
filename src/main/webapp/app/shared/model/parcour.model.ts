import { IExercice } from 'app/shared/model/exercice.model';
import { ICours } from 'app/shared/model/cours.model';

export interface IParcour {
  id?: number;
  titreParcour?: string;
  niveauParcour?: number;
  exercices?: IExercice[];
  cours?: ICours[];
  tuteurFirstName?: string;
  tuteurId?: number;
  modulePedagogiqueIntituleModule?: string;
  modulePedagogiqueId?: number;
}

export class Parcour implements IParcour {
  constructor(
    public id?: number,
    public titreParcour?: string,
    public niveauParcour?: number,
    public exercices?: IExercice[],
    public cours?: ICours[],
    public tuteurFirstName?: string,
    public tuteurId?: number,
    public modulePedagogiqueIntituleModule?: string,
    public modulePedagogiqueId?: number
  ) {}
}

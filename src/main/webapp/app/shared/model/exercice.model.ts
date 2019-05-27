import { ISituation } from 'app/shared/model/situation.model';
import { IRessource } from 'app/shared/model/ressource.model';

export interface IExercice {
  id?: number;
  titreExercice?: string;
  contenuExercice?: string;
  questions?: ISituation[];
  ressources?: IRessource[];
  parcourTitreParcour?: string;
  parcourId?: number;
}

export class Exercice implements IExercice {
  constructor(
    public id?: number,
    public titreExercice?: string,
    public contenuExercice?: string,
    public questions?: ISituation[],
    public ressources?: IRessource[],
    public parcourTitreParcour?: string,
    public parcourId?: number
  ) {}
}

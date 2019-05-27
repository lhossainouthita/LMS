import { ISituation } from 'app/shared/model/situation.model';
import { IRessource } from 'app/shared/model/ressource.model';

export interface ICours {
  id?: number;
  titreCours?: string;
  contenuCours?: string;
  questions?: ISituation[];
  ressources?: IRessource[];
  parcourTitreParcour?: string;
  parcourId?: number;
}

export class Cours implements ICours {
  constructor(
    public id?: number,
    public titreCours?: string,
    public contenuCours?: string,
    public questions?: ISituation[],
    public ressources?: IRessource[],
    public parcourTitreParcour?: string,
    public parcourId?: number
  ) {}
}

import { IParcour } from 'app/shared/model/parcour.model';
import { ISujet } from 'app/shared/model/sujet.model';
import { ICompetence } from 'app/shared/model/competence.model';
import { IDevoir } from 'app/shared/model/devoir.model';

export interface IModulePedagogique {
  id?: number;
  codeModule?: string;
  intituleModule?: string;
  descriptionModule?: string;
  parcours?: IParcour[];
  sujets?: ISujet[];
  competences?: ICompetence[];
  devoirs?: IDevoir[];
  adminFirstName?: string;
  adminId?: number;
}

export class ModulePedagogique implements IModulePedagogique {
  constructor(
    public id?: number,
    public codeModule?: string,
    public intituleModule?: string,
    public descriptionModule?: string,
    public parcours?: IParcour[],
    public sujets?: ISujet[],
    public competences?: ICompetence[],
    public devoirs?: IDevoir[],
    public adminFirstName?: string,
    public adminId?: number
  ) {}
}

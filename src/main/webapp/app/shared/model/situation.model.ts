import { IQuestion } from 'app/shared/model/question.model';

export interface ISituation {
  id?: number;
  titreSituation?: string;
  contenuSituation?: string;
  exerciceId?: number;
  coursId?: number;
  questions?: IQuestion[];
}

export class Situation implements ISituation {
  constructor(
    public id?: number,
    public titreSituation?: string,
    public contenuSituation?: string,
    public exerciceId?: number,
    public coursId?: number,
    public questions?: IQuestion[]
  ) {}
}

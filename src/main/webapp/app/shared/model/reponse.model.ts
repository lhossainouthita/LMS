export interface IReponse {
  id?: number;
  contenuReponse?: string;
  questionContenuQuestion?: string;
  questionId?: number;
}

export class Reponse implements IReponse {
  constructor(public id?: number, public contenuReponse?: string, public questionContenuQuestion?: string, public questionId?: number) {}
}

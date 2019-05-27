export interface IQuestion {
  id?: number;
  numQuestion?: string;
  contenuQuestion?: string;
  situationId?: number;
}

export class Question implements IQuestion {
  constructor(public id?: number, public numQuestion?: string, public contenuQuestion?: string, public situationId?: number) {}
}

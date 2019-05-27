export interface IDevoir {
  id?: number;
  titreDevoir?: string;
  cheminDevoir?: string;
  modulePedagogiqueId?: number;
}

export class Devoir implements IDevoir {
  constructor(public id?: number, public titreDevoir?: string, public cheminDevoir?: string, public modulePedagogiqueId?: number) {}
}

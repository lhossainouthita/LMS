export const enum TypeRessource {
  VEDIO = 'VEDIO',
  IMAGE = 'IMAGE',
  ANIMATION = 'ANIMATION',
  PDF = 'PDF',
  PPT = 'PPT',
  LIEN = 'LIEN'
}

export interface IRessource {
  id?: number;
  titreRessource?: string;
  cheminRessource?: string;
  typeRessource?: TypeRessource;
  exerciceId?: number;
  coursId?: number;
}

export class Ressource implements IRessource {
  constructor(
    public id?: number,
    public titreRessource?: string,
    public cheminRessource?: string,
    public typeRessource?: TypeRessource,
    public exerciceId?: number,
    public coursId?: number
  ) {}
}

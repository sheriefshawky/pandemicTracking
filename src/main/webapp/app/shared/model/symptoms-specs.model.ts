export interface ISymptomsSpecs {
  id?: number;
  code?: string;
  descriptionAr?: string;
  descriptionEn?: string;
  specType?: number;
}

export class SymptomsSpecs implements ISymptomsSpecs {
  constructor(
    public id?: number,
    public code?: string,
    public descriptionAr?: string,
    public descriptionEn?: string,
    public specType?: number
  ) {}
}

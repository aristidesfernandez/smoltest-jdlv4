export interface ICountry {
  id: number;
  code?: string | null;
  name?: string | null;
  identifier?: string | null;
  defaultLanguage?: string | null;
}

export type NewCountry = Omit<ICountry, 'id'> & { id: null };

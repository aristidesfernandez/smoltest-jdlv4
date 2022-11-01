import { IEstablishment } from 'app/entities/establishment/establishment.model';

export interface IIsle {
  id: number;
  description?: string | null;
  name?: string | null;
  establishment?: Pick<IEstablishment, 'id'> | null;
}

export type NewIsle = Omit<IIsle, 'id'> & { id: null };

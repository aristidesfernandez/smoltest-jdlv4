import { IEstablishment } from 'app/entities/establishment/establishment.model';

export interface ICurrencyType {
  id: number;
  name?: string | null;
  code?: string | null;
  symbol?: string | null;
  isPriority?: boolean | null;
  location?: string | null;
  exchangeRate?: number | null;
  decimalPlaces?: number | null;
  decimalSeparator?: string | null;
  thousandSeparator?: string | null;
  description?: string | null;
  establishment?: Pick<IEstablishment, 'id'> | null;
}

export type NewCurrencyType = Omit<ICurrencyType, 'id'> & { id: null };

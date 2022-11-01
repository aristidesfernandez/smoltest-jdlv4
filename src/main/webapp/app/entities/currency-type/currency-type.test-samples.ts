import { ICurrencyType, NewCurrencyType } from './currency-type.model';

export const sampleWithRequiredData: ICurrencyType = {
  id: 51685,
  code: 'Chi',
  symbol: 'onlin',
};

export const sampleWithPartialData: ICurrencyType = {
  id: 6379,
  name: 'transmitting compressing',
  code: 'glo',
  symbol: 'schem',
  isPriority: false,
  location: 'Informática Berkshire experiences',
  exchangeRate: 64856,
  decimalPlaces: 70828,
  decimalSeparator: 's',
};

export const sampleWithFullData: ICurrencyType = {
  id: 14606,
  name: 'Borders Cantabria Interno',
  code: 'Inf',
  symbol: 'neura',
  isPriority: true,
  location: 'Polarizado',
  exchangeRate: 85454,
  decimalPlaces: 6655,
  decimalSeparator: 'I',
  thousandSeparator: 'P',
  description: 'Inteligente Bedfordshire',
};

export const sampleWithNewData: NewCurrencyType = {
  code: 'Inc',
  symbol: 'parsi',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

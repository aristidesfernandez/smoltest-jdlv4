import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  id: 4746,
};

export const sampleWithPartialData: ICountry = {
  id: 94903,
  code: 'Pakistan Ingeniero paymen',
  name: 'engineer Ordenador',
  identifier: 'interfaz deposit',
};

export const sampleWithFullData: ICountry = {
  id: 45927,
  code: 'Ringgit',
  name: 'Programa panel',
  identifier: 'objetos Polarizado',
  defaultLanguage: 'front',
};

export const sampleWithNewData: NewCountry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

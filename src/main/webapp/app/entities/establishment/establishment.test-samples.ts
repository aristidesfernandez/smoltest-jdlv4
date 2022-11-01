import dayjs from 'dayjs/esm';

import { EstablishmentType } from 'app/entities/enumerations/establishment-type.model';

import { IEstablishment, NewEstablishment } from './establishment.model';

export const sampleWithRequiredData: IEstablishment = {
  id: 60820,
  type: EstablishmentType['CASINO'],
  coljuegosCode: 'Acero synthesize',
  startTime: dayjs('2021-09-27T15:29'),
  closeTime: dayjs('2021-09-27T14:31'),
};

export const sampleWithPartialData: IEstablishment = {
  id: 96739,
  liquidationTime: dayjs('2021-09-27T08:23'),
  name: 'Metal',
  type: EstablishmentType['RUTA'],
  neighborhood: 'Edificio',
  coljuegosCode: 'invoice',
  startTime: dayjs('2021-09-27T07:32'),
  closeTime: dayjs('2021-09-27T11:34'),
  mercantileRegistration: 'Global deposit',
};

export const sampleWithFullData: IEstablishment = {
  id: 49162,
  liquidationTime: dayjs('2021-09-27T01:27'),
  name: 'éxito',
  type: EstablishmentType['CASINO'],
  neighborhood: 'Asistente Rústico',
  address: 'Puerta bus',
  coljuegosCode: 'Acero Videojuegos capacitor',
  startTime: dayjs('2021-09-27T03:20'),
  closeTime: dayjs('2021-09-27T05:36'),
  longitude: 82204,
  latitude: 13414,
  mercantileRegistration: 'Central',
};

export const sampleWithNewData: NewEstablishment = {
  type: EstablishmentType['RUTA'],
  coljuegosCode: 'Futuro Gerente',
  startTime: dayjs('2021-09-27T11:03'),
  closeTime: dayjs('2021-09-27T14:30'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

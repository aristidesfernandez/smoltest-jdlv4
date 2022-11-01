import { ICommand, NewCommand } from './command.model';

export const sampleWithRequiredData: ICommand = {
  id: 51722,
};

export const sampleWithPartialData: ICommand = {
  id: 66404,
  description: 'compressing',
};

export const sampleWithFullData: ICommand = {
  id: 39026,
  code: 'payment Parafarmacia SMS',
  description: 'EXE Balboa Hormigon',
  processor: 'empower Asociado Ordenador',
};

export const sampleWithNewData: NewCommand = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

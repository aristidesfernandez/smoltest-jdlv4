import { ICommandModel, NewCommandModel } from './command-model.model';

export const sampleWithRequiredData: ICommandModel = {
  id: 69080,
};

export const sampleWithPartialData: ICommandModel = {
  id: 11672,
  codeSAS: 'B2',
};

export const sampleWithFullData: ICommandModel = {
  id: 60678,
  codeSAS: 'Be',
};

export const sampleWithNewData: NewCommandModel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

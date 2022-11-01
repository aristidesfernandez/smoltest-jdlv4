import { IFormulaCounterType, NewFormulaCounterType } from './formula-counter-type.model';

export const sampleWithRequiredData: IFormulaCounterType = {
  id: 28992,
};

export const sampleWithPartialData: IFormulaCounterType = {
  id: 5533,
};

export const sampleWithFullData: IFormulaCounterType = {
  id: 72678,
};

export const sampleWithNewData: NewFormulaCounterType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

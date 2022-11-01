import { IEventTypeModel, NewEventTypeModel } from './event-type-model.model';

export const sampleWithRequiredData: IEventTypeModel = {
  id: 74059,
};

export const sampleWithPartialData: IEventTypeModel = {
  id: 63282,
  modelId: 14378,
};

export const sampleWithFullData: IEventTypeModel = {
  id: 88976,
  modelId: 11549,
};

export const sampleWithNewData: NewEventTypeModel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ICommandDevice, NewCommandDevice } from './command-device.model';

export const sampleWithRequiredData: ICommandDevice = {
  id: 84847,
};

export const sampleWithPartialData: ICommandDevice = {
  id: 56300,
};

export const sampleWithFullData: ICommandDevice = {
  id: 87899,
};

export const sampleWithNewData: NewCommandDevice = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

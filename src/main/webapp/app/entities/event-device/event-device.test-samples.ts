import dayjs from 'dayjs/esm';

import { IEventDevice, NewEventDevice } from './event-device.model';

export const sampleWithRequiredData: IEventDevice = {
  id: 'ca3b2a8e-a0f6-4789-abd5-1557a2d72303',
  createdAt: dayjs('2021-09-27T09:39'),
};

export const sampleWithPartialData: IEventDevice = {
  id: 'eed6a874-1aba-4ac6-9148-2b4b7a45c4e6',
  createdAt: dayjs('2021-09-27T06:51'),
  theoreticalPercentage: true,
};

export const sampleWithFullData: IEventDevice = {
  id: '8f801abd-6de2-4781-a119-0d234ce8deaa',
  createdAt: dayjs('2021-09-26T23:59'),
  theoreticalPercentage: true,
  moneyDenomination: 71574,
};

export const sampleWithNewData: NewEventDevice = {
  createdAt: dayjs('2021-09-27T12:11'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

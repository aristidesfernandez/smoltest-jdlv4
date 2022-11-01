import { IEventType } from 'app/entities/event-type/event-type.model';

export interface IEventTypeModel {
  id: number;
  modelId?: number | null;
  eventType?: Pick<IEventType, 'id'> | null;
}

export type NewEventTypeModel = Omit<IEventTypeModel, 'id'> & { id: null };

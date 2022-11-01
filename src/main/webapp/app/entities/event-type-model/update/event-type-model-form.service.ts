import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventTypeModel, NewEventTypeModel } from '../event-type-model.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventTypeModel for edit and NewEventTypeModelFormGroupInput for create.
 */
type EventTypeModelFormGroupInput = IEventTypeModel | PartialWithRequiredKeyOf<NewEventTypeModel>;

type EventTypeModelFormDefaults = Pick<NewEventTypeModel, 'id'>;

type EventTypeModelFormGroupContent = {
  id: FormControl<IEventTypeModel['id'] | NewEventTypeModel['id']>;
  modelId: FormControl<IEventTypeModel['modelId']>;
  eventType: FormControl<IEventTypeModel['eventType']>;
};

export type EventTypeModelFormGroup = FormGroup<EventTypeModelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventTypeModelFormService {
  createEventTypeModelFormGroup(eventTypeModel: EventTypeModelFormGroupInput = { id: null }): EventTypeModelFormGroup {
    const eventTypeModelRawValue = {
      ...this.getFormDefaults(),
      ...eventTypeModel,
    };
    return new FormGroup<EventTypeModelFormGroupContent>({
      id: new FormControl(
        { value: eventTypeModelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      modelId: new FormControl(eventTypeModelRawValue.modelId),
      eventType: new FormControl(eventTypeModelRawValue.eventType),
    });
  }

  getEventTypeModel(form: EventTypeModelFormGroup): IEventTypeModel | NewEventTypeModel {
    return form.getRawValue() as IEventTypeModel | NewEventTypeModel;
  }

  resetForm(form: EventTypeModelFormGroup, eventTypeModel: EventTypeModelFormGroupInput): void {
    const eventTypeModelRawValue = { ...this.getFormDefaults(), ...eventTypeModel };
    form.reset(
      {
        ...eventTypeModelRawValue,
        id: { value: eventTypeModelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventTypeModelFormDefaults {
    return {
      id: null,
    };
  }
}

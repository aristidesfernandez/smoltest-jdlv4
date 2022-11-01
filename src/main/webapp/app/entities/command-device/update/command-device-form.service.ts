import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICommandDevice, NewCommandDevice } from '../command-device.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommandDevice for edit and NewCommandDeviceFormGroupInput for create.
 */
type CommandDeviceFormGroupInput = ICommandDevice | PartialWithRequiredKeyOf<NewCommandDevice>;

type CommandDeviceFormDefaults = Pick<NewCommandDevice, 'id'>;

type CommandDeviceFormGroupContent = {
  id: FormControl<ICommandDevice['id'] | NewCommandDevice['id']>;
  command: FormControl<ICommandDevice['command']>;
  device: FormControl<ICommandDevice['device']>;
};

export type CommandDeviceFormGroup = FormGroup<CommandDeviceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommandDeviceFormService {
  createCommandDeviceFormGroup(commandDevice: CommandDeviceFormGroupInput = { id: null }): CommandDeviceFormGroup {
    const commandDeviceRawValue = {
      ...this.getFormDefaults(),
      ...commandDevice,
    };
    return new FormGroup<CommandDeviceFormGroupContent>({
      id: new FormControl(
        { value: commandDeviceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      command: new FormControl(commandDeviceRawValue.command, {
        validators: [Validators.required],
      }),
      device: new FormControl(commandDeviceRawValue.device, {
        validators: [Validators.required],
      }),
    });
  }

  getCommandDevice(form: CommandDeviceFormGroup): ICommandDevice | NewCommandDevice {
    return form.getRawValue() as ICommandDevice | NewCommandDevice;
  }

  resetForm(form: CommandDeviceFormGroup, commandDevice: CommandDeviceFormGroupInput): void {
    const commandDeviceRawValue = { ...this.getFormDefaults(), ...commandDevice };
    form.reset(
      {
        ...commandDeviceRawValue,
        id: { value: commandDeviceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommandDeviceFormDefaults {
    return {
      id: null,
    };
  }
}

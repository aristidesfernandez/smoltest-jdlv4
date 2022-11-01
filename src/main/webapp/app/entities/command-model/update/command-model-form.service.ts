import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICommandModel, NewCommandModel } from '../command-model.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommandModel for edit and NewCommandModelFormGroupInput for create.
 */
type CommandModelFormGroupInput = ICommandModel | PartialWithRequiredKeyOf<NewCommandModel>;

type CommandModelFormDefaults = Pick<NewCommandModel, 'id'>;

type CommandModelFormGroupContent = {
  id: FormControl<ICommandModel['id'] | NewCommandModel['id']>;
  codeSAS: FormControl<ICommandModel['codeSAS']>;
  command: FormControl<ICommandModel['command']>;
  model: FormControl<ICommandModel['model']>;
};

export type CommandModelFormGroup = FormGroup<CommandModelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommandModelFormService {
  createCommandModelFormGroup(commandModel: CommandModelFormGroupInput = { id: null }): CommandModelFormGroup {
    const commandModelRawValue = {
      ...this.getFormDefaults(),
      ...commandModel,
    };
    return new FormGroup<CommandModelFormGroupContent>({
      id: new FormControl(
        { value: commandModelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codeSAS: new FormControl(commandModelRawValue.codeSAS, {
        validators: [Validators.maxLength(2)],
      }),
      command: new FormControl(commandModelRawValue.command, {
        validators: [Validators.required],
      }),
      model: new FormControl(commandModelRawValue.model, {
        validators: [Validators.required],
      }),
    });
  }

  getCommandModel(form: CommandModelFormGroup): ICommandModel | NewCommandModel {
    return form.getRawValue() as ICommandModel | NewCommandModel;
  }

  resetForm(form: CommandModelFormGroup, commandModel: CommandModelFormGroupInput): void {
    const commandModelRawValue = { ...this.getFormDefaults(), ...commandModel };
    form.reset(
      {
        ...commandModelRawValue,
        id: { value: commandModelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommandModelFormDefaults {
    return {
      id: null,
    };
  }
}

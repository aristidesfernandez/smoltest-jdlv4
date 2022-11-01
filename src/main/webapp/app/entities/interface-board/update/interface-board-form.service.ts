import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInterfaceBoard, NewInterfaceBoard } from '../interface-board.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterfaceBoard for edit and NewInterfaceBoardFormGroupInput for create.
 */
type InterfaceBoardFormGroupInput = IInterfaceBoard | PartialWithRequiredKeyOf<NewInterfaceBoard>;

type InterfaceBoardFormDefaults = Pick<NewInterfaceBoard, 'id' | 'isAssigned'>;

type InterfaceBoardFormGroupContent = {
  id: FormControl<IInterfaceBoard['id'] | NewInterfaceBoard['id']>;
  isAssigned: FormControl<IInterfaceBoard['isAssigned']>;
  ipAddress: FormControl<IInterfaceBoard['ipAddress']>;
  hash: FormControl<IInterfaceBoard['hash']>;
  serial: FormControl<IInterfaceBoard['serial']>;
  version: FormControl<IInterfaceBoard['version']>;
  port: FormControl<IInterfaceBoard['port']>;
};

export type InterfaceBoardFormGroup = FormGroup<InterfaceBoardFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterfaceBoardFormService {
  createInterfaceBoardFormGroup(interfaceBoard: InterfaceBoardFormGroupInput = { id: null }): InterfaceBoardFormGroup {
    const interfaceBoardRawValue = {
      ...this.getFormDefaults(),
      ...interfaceBoard,
    };
    return new FormGroup<InterfaceBoardFormGroupContent>({
      id: new FormControl(
        { value: interfaceBoardRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      isAssigned: new FormControl(interfaceBoardRawValue.isAssigned),
      ipAddress: new FormControl(interfaceBoardRawValue.ipAddress, {
        validators: [Validators.maxLength(25)],
      }),
      hash: new FormControl(interfaceBoardRawValue.hash),
      serial: new FormControl(interfaceBoardRawValue.serial, {
        validators: [Validators.maxLength(50)],
      }),
      version: new FormControl(interfaceBoardRawValue.version, {
        validators: [Validators.maxLength(10)],
      }),
      port: new FormControl(interfaceBoardRawValue.port, {
        validators: [Validators.maxLength(10)],
      }),
    });
  }

  getInterfaceBoard(form: InterfaceBoardFormGroup): IInterfaceBoard | NewInterfaceBoard {
    return form.getRawValue() as IInterfaceBoard | NewInterfaceBoard;
  }

  resetForm(form: InterfaceBoardFormGroup, interfaceBoard: InterfaceBoardFormGroupInput): void {
    const interfaceBoardRawValue = { ...this.getFormDefaults(), ...interfaceBoard };
    form.reset(
      {
        ...interfaceBoardRawValue,
        id: { value: interfaceBoardRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InterfaceBoardFormDefaults {
    return {
      id: null,
      isAssigned: false,
    };
  }
}

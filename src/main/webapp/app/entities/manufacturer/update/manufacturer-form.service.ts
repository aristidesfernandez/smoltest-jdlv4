import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IManufacturer, NewManufacturer } from '../manufacturer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IManufacturer for edit and NewManufacturerFormGroupInput for create.
 */
type ManufacturerFormGroupInput = IManufacturer | PartialWithRequiredKeyOf<NewManufacturer>;

type ManufacturerFormDefaults = Pick<NewManufacturer, 'id'>;

type ManufacturerFormGroupContent = {
  id: FormControl<IManufacturer['id'] | NewManufacturer['id']>;
  code: FormControl<IManufacturer['code']>;
  name: FormControl<IManufacturer['name']>;
};

export type ManufacturerFormGroup = FormGroup<ManufacturerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ManufacturerFormService {
  createManufacturerFormGroup(manufacturer: ManufacturerFormGroupInput = { id: null }): ManufacturerFormGroup {
    const manufacturerRawValue = {
      ...this.getFormDefaults(),
      ...manufacturer,
    };
    return new FormGroup<ManufacturerFormGroupContent>({
      id: new FormControl(
        { value: manufacturerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(manufacturerRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      name: new FormControl(manufacturerRawValue.name, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getManufacturer(form: ManufacturerFormGroup): IManufacturer | NewManufacturer {
    return form.getRawValue() as IManufacturer | NewManufacturer;
  }

  resetForm(form: ManufacturerFormGroup, manufacturer: ManufacturerFormGroupInput): void {
    const manufacturerRawValue = { ...this.getFormDefaults(), ...manufacturer };
    form.reset(
      {
        ...manufacturerRawValue,
        id: { value: manufacturerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ManufacturerFormDefaults {
    return {
      id: null,
    };
  }
}

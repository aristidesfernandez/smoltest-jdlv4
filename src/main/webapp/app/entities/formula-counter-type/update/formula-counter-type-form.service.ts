import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFormulaCounterType, NewFormulaCounterType } from '../formula-counter-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormulaCounterType for edit and NewFormulaCounterTypeFormGroupInput for create.
 */
type FormulaCounterTypeFormGroupInput = IFormulaCounterType | PartialWithRequiredKeyOf<NewFormulaCounterType>;

type FormulaCounterTypeFormDefaults = Pick<NewFormulaCounterType, 'id'>;

type FormulaCounterTypeFormGroupContent = {
  id: FormControl<IFormulaCounterType['id'] | NewFormulaCounterType['id']>;
  formula: FormControl<IFormulaCounterType['formula']>;
  counterType: FormControl<IFormulaCounterType['counterType']>;
};

export type FormulaCounterTypeFormGroup = FormGroup<FormulaCounterTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormulaCounterTypeFormService {
  createFormulaCounterTypeFormGroup(formulaCounterType: FormulaCounterTypeFormGroupInput = { id: null }): FormulaCounterTypeFormGroup {
    const formulaCounterTypeRawValue = {
      ...this.getFormDefaults(),
      ...formulaCounterType,
    };
    return new FormGroup<FormulaCounterTypeFormGroupContent>({
      id: new FormControl(
        { value: formulaCounterTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      formula: new FormControl(formulaCounterTypeRawValue.formula),
      counterType: new FormControl(formulaCounterTypeRawValue.counterType),
    });
  }

  getFormulaCounterType(form: FormulaCounterTypeFormGroup): IFormulaCounterType | NewFormulaCounterType {
    return form.getRawValue() as IFormulaCounterType | NewFormulaCounterType;
  }

  resetForm(form: FormulaCounterTypeFormGroup, formulaCounterType: FormulaCounterTypeFormGroupInput): void {
    const formulaCounterTypeRawValue = { ...this.getFormDefaults(), ...formulaCounterType };
    form.reset(
      {
        ...formulaCounterTypeRawValue,
        id: { value: formulaCounterTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FormulaCounterTypeFormDefaults {
    return {
      id: null,
    };
  }
}

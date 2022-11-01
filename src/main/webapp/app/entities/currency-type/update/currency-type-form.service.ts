import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICurrencyType, NewCurrencyType } from '../currency-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICurrencyType for edit and NewCurrencyTypeFormGroupInput for create.
 */
type CurrencyTypeFormGroupInput = ICurrencyType | PartialWithRequiredKeyOf<NewCurrencyType>;

type CurrencyTypeFormDefaults = Pick<NewCurrencyType, 'id' | 'isPriority'>;

type CurrencyTypeFormGroupContent = {
  id: FormControl<ICurrencyType['id'] | NewCurrencyType['id']>;
  name: FormControl<ICurrencyType['name']>;
  code: FormControl<ICurrencyType['code']>;
  symbol: FormControl<ICurrencyType['symbol']>;
  isPriority: FormControl<ICurrencyType['isPriority']>;
  location: FormControl<ICurrencyType['location']>;
  exchangeRate: FormControl<ICurrencyType['exchangeRate']>;
  decimalPlaces: FormControl<ICurrencyType['decimalPlaces']>;
  decimalSeparator: FormControl<ICurrencyType['decimalSeparator']>;
  thousandSeparator: FormControl<ICurrencyType['thousandSeparator']>;
  description: FormControl<ICurrencyType['description']>;
  establishment: FormControl<ICurrencyType['establishment']>;
};

export type CurrencyTypeFormGroup = FormGroup<CurrencyTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CurrencyTypeFormService {
  createCurrencyTypeFormGroup(currencyType: CurrencyTypeFormGroupInput = { id: null }): CurrencyTypeFormGroup {
    const currencyTypeRawValue = {
      ...this.getFormDefaults(),
      ...currencyType,
    };
    return new FormGroup<CurrencyTypeFormGroupContent>({
      id: new FormControl(
        { value: currencyTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(currencyTypeRawValue.name, {
        validators: [Validators.maxLength(50)],
      }),
      code: new FormControl(currencyTypeRawValue.code, {
        validators: [Validators.required, Validators.maxLength(3)],
      }),
      symbol: new FormControl(currencyTypeRawValue.symbol, {
        validators: [Validators.required, Validators.maxLength(5)],
      }),
      isPriority: new FormControl(currencyTypeRawValue.isPriority),
      location: new FormControl(currencyTypeRawValue.location, {
        validators: [Validators.maxLength(50)],
      }),
      exchangeRate: new FormControl(currencyTypeRawValue.exchangeRate),
      decimalPlaces: new FormControl(currencyTypeRawValue.decimalPlaces),
      decimalSeparator: new FormControl(currencyTypeRawValue.decimalSeparator, {
        validators: [Validators.maxLength(1)],
      }),
      thousandSeparator: new FormControl(currencyTypeRawValue.thousandSeparator, {
        validators: [Validators.maxLength(1)],
      }),
      description: new FormControl(currencyTypeRawValue.description, {
        validators: [Validators.maxLength(100)],
      }),
      establishment: new FormControl(currencyTypeRawValue.establishment, {
        validators: [Validators.required],
      }),
    });
  }

  getCurrencyType(form: CurrencyTypeFormGroup): ICurrencyType | NewCurrencyType {
    return form.getRawValue() as ICurrencyType | NewCurrencyType;
  }

  resetForm(form: CurrencyTypeFormGroup, currencyType: CurrencyTypeFormGroupInput): void {
    const currencyTypeRawValue = { ...this.getFormDefaults(), ...currencyType };
    form.reset(
      {
        ...currencyTypeRawValue,
        id: { value: currencyTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CurrencyTypeFormDefaults {
    return {
      id: null,
      isPriority: false,
    };
  }
}

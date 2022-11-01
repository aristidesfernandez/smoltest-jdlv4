import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../formula-counter-type.test-samples';

import { FormulaCounterTypeFormService } from './formula-counter-type-form.service';

describe('FormulaCounterType Form Service', () => {
  let service: FormulaCounterTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormulaCounterTypeFormService);
  });

  describe('Service methods', () => {
    describe('createFormulaCounterTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFormulaCounterTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            formula: expect.any(Object),
            counterType: expect.any(Object),
          })
        );
      });

      it('passing IFormulaCounterType should create a new form with FormGroup', () => {
        const formGroup = service.createFormulaCounterTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            formula: expect.any(Object),
            counterType: expect.any(Object),
          })
        );
      });
    });

    describe('getFormulaCounterType', () => {
      it('should return NewFormulaCounterType for default FormulaCounterType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFormulaCounterTypeFormGroup(sampleWithNewData);

        const formulaCounterType = service.getFormulaCounterType(formGroup) as any;

        expect(formulaCounterType).toMatchObject(sampleWithNewData);
      });

      it('should return NewFormulaCounterType for empty FormulaCounterType initial value', () => {
        const formGroup = service.createFormulaCounterTypeFormGroup();

        const formulaCounterType = service.getFormulaCounterType(formGroup) as any;

        expect(formulaCounterType).toMatchObject({});
      });

      it('should return IFormulaCounterType', () => {
        const formGroup = service.createFormulaCounterTypeFormGroup(sampleWithRequiredData);

        const formulaCounterType = service.getFormulaCounterType(formGroup) as any;

        expect(formulaCounterType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFormulaCounterType should not enable id FormControl', () => {
        const formGroup = service.createFormulaCounterTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFormulaCounterType should disable id FormControl', () => {
        const formGroup = service.createFormulaCounterTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

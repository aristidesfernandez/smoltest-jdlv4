import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../command-device.test-samples';

import { CommandDeviceFormService } from './command-device-form.service';

describe('CommandDevice Form Service', () => {
  let service: CommandDeviceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommandDeviceFormService);
  });

  describe('Service methods', () => {
    describe('createCommandDeviceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommandDeviceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            command: expect.any(Object),
            device: expect.any(Object),
          })
        );
      });

      it('passing ICommandDevice should create a new form with FormGroup', () => {
        const formGroup = service.createCommandDeviceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            command: expect.any(Object),
            device: expect.any(Object),
          })
        );
      });
    });

    describe('getCommandDevice', () => {
      it('should return NewCommandDevice for default CommandDevice initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCommandDeviceFormGroup(sampleWithNewData);

        const commandDevice = service.getCommandDevice(formGroup) as any;

        expect(commandDevice).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommandDevice for empty CommandDevice initial value', () => {
        const formGroup = service.createCommandDeviceFormGroup();

        const commandDevice = service.getCommandDevice(formGroup) as any;

        expect(commandDevice).toMatchObject({});
      });

      it('should return ICommandDevice', () => {
        const formGroup = service.createCommandDeviceFormGroup(sampleWithRequiredData);

        const commandDevice = service.getCommandDevice(formGroup) as any;

        expect(commandDevice).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommandDevice should not enable id FormControl', () => {
        const formGroup = service.createCommandDeviceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommandDevice should disable id FormControl', () => {
        const formGroup = service.createCommandDeviceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

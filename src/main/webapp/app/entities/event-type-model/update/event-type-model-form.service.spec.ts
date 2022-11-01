import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-type-model.test-samples';

import { EventTypeModelFormService } from './event-type-model-form.service';

describe('EventTypeModel Form Service', () => {
  let service: EventTypeModelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventTypeModelFormService);
  });

  describe('Service methods', () => {
    describe('createEventTypeModelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventTypeModelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modelId: expect.any(Object),
            eventType: expect.any(Object),
          })
        );
      });

      it('passing IEventTypeModel should create a new form with FormGroup', () => {
        const formGroup = service.createEventTypeModelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modelId: expect.any(Object),
            eventType: expect.any(Object),
          })
        );
      });
    });

    describe('getEventTypeModel', () => {
      it('should return NewEventTypeModel for default EventTypeModel initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventTypeModelFormGroup(sampleWithNewData);

        const eventTypeModel = service.getEventTypeModel(formGroup) as any;

        expect(eventTypeModel).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventTypeModel for empty EventTypeModel initial value', () => {
        const formGroup = service.createEventTypeModelFormGroup();

        const eventTypeModel = service.getEventTypeModel(formGroup) as any;

        expect(eventTypeModel).toMatchObject({});
      });

      it('should return IEventTypeModel', () => {
        const formGroup = service.createEventTypeModelFormGroup(sampleWithRequiredData);

        const eventTypeModel = service.getEventTypeModel(formGroup) as any;

        expect(eventTypeModel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventTypeModel should not enable id FormControl', () => {
        const formGroup = service.createEventTypeModelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventTypeModel should disable id FormControl', () => {
        const formGroup = service.createEventTypeModelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

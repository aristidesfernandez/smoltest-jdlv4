import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventDeviceFormService } from './event-device-form.service';
import { EventDeviceService } from '../service/event-device.service';
import { IEventDevice } from '../event-device.model';
import { IEventType } from 'app/entities/event-type/event-type.model';
import { EventTypeService } from 'app/entities/event-type/service/event-type.service';

import { EventDeviceUpdateComponent } from './event-device-update.component';

describe('EventDevice Management Update Component', () => {
  let comp: EventDeviceUpdateComponent;
  let fixture: ComponentFixture<EventDeviceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventDeviceFormService: EventDeviceFormService;
  let eventDeviceService: EventDeviceService;
  let eventTypeService: EventTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventDeviceUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EventDeviceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventDeviceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventDeviceFormService = TestBed.inject(EventDeviceFormService);
    eventDeviceService = TestBed.inject(EventDeviceService);
    eventTypeService = TestBed.inject(EventTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventType query and add missing value', () => {
      const eventDevice: IEventDevice = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const eventType: IEventType = { id: 49086 };
      eventDevice.eventType = eventType;

      const eventTypeCollection: IEventType[] = [{ id: 60336 }];
      jest.spyOn(eventTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: eventTypeCollection })));
      const additionalEventTypes = [eventType];
      const expectedCollection: IEventType[] = [...additionalEventTypes, ...eventTypeCollection];
      jest.spyOn(eventTypeService, 'addEventTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventDevice });
      comp.ngOnInit();

      expect(eventTypeService.query).toHaveBeenCalled();
      expect(eventTypeService.addEventTypeToCollectionIfMissing).toHaveBeenCalledWith(
        eventTypeCollection,
        ...additionalEventTypes.map(expect.objectContaining)
      );
      expect(comp.eventTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventDevice: IEventDevice = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const eventType: IEventType = { id: 19201 };
      eventDevice.eventType = eventType;

      activatedRoute.data = of({ eventDevice });
      comp.ngOnInit();

      expect(comp.eventTypesSharedCollection).toContain(eventType);
      expect(comp.eventDevice).toEqual(eventDevice);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventDevice>>();
      const eventDevice = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(eventDeviceFormService, 'getEventDevice').mockReturnValue(eventDevice);
      jest.spyOn(eventDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventDevice }));
      saveSubject.complete();

      // THEN
      expect(eventDeviceFormService.getEventDevice).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventDeviceService.update).toHaveBeenCalledWith(expect.objectContaining(eventDevice));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventDevice>>();
      const eventDevice = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(eventDeviceFormService, 'getEventDevice').mockReturnValue({ id: null });
      jest.spyOn(eventDeviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventDevice: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventDevice }));
      saveSubject.complete();

      // THEN
      expect(eventDeviceFormService.getEventDevice).toHaveBeenCalled();
      expect(eventDeviceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventDevice>>();
      const eventDevice = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(eventDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventDeviceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEventType', () => {
      it('Should forward to eventTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eventTypeService, 'compareEventType');
        comp.compareEventType(entity, entity2);
        expect(eventTypeService.compareEventType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

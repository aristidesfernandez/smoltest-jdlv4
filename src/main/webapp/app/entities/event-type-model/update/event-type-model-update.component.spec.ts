import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventTypeModelFormService } from './event-type-model-form.service';
import { EventTypeModelService } from '../service/event-type-model.service';
import { IEventTypeModel } from '../event-type-model.model';
import { IEventType } from 'app/entities/event-type/event-type.model';
import { EventTypeService } from 'app/entities/event-type/service/event-type.service';

import { EventTypeModelUpdateComponent } from './event-type-model-update.component';

describe('EventTypeModel Management Update Component', () => {
  let comp: EventTypeModelUpdateComponent;
  let fixture: ComponentFixture<EventTypeModelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventTypeModelFormService: EventTypeModelFormService;
  let eventTypeModelService: EventTypeModelService;
  let eventTypeService: EventTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventTypeModelUpdateComponent],
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
      .overrideTemplate(EventTypeModelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventTypeModelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventTypeModelFormService = TestBed.inject(EventTypeModelFormService);
    eventTypeModelService = TestBed.inject(EventTypeModelService);
    eventTypeService = TestBed.inject(EventTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventType query and add missing value', () => {
      const eventTypeModel: IEventTypeModel = { id: 456 };
      const eventType: IEventType = { id: 22844 };
      eventTypeModel.eventType = eventType;

      const eventTypeCollection: IEventType[] = [{ id: 71739 }];
      jest.spyOn(eventTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: eventTypeCollection })));
      const additionalEventTypes = [eventType];
      const expectedCollection: IEventType[] = [...additionalEventTypes, ...eventTypeCollection];
      jest.spyOn(eventTypeService, 'addEventTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventTypeModel });
      comp.ngOnInit();

      expect(eventTypeService.query).toHaveBeenCalled();
      expect(eventTypeService.addEventTypeToCollectionIfMissing).toHaveBeenCalledWith(
        eventTypeCollection,
        ...additionalEventTypes.map(expect.objectContaining)
      );
      expect(comp.eventTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventTypeModel: IEventTypeModel = { id: 456 };
      const eventType: IEventType = { id: 21304 };
      eventTypeModel.eventType = eventType;

      activatedRoute.data = of({ eventTypeModel });
      comp.ngOnInit();

      expect(comp.eventTypesSharedCollection).toContain(eventType);
      expect(comp.eventTypeModel).toEqual(eventTypeModel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventTypeModel>>();
      const eventTypeModel = { id: 123 };
      jest.spyOn(eventTypeModelFormService, 'getEventTypeModel').mockReturnValue(eventTypeModel);
      jest.spyOn(eventTypeModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventTypeModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventTypeModel }));
      saveSubject.complete();

      // THEN
      expect(eventTypeModelFormService.getEventTypeModel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventTypeModelService.update).toHaveBeenCalledWith(expect.objectContaining(eventTypeModel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventTypeModel>>();
      const eventTypeModel = { id: 123 };
      jest.spyOn(eventTypeModelFormService, 'getEventTypeModel').mockReturnValue({ id: null });
      jest.spyOn(eventTypeModelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventTypeModel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventTypeModel }));
      saveSubject.complete();

      // THEN
      expect(eventTypeModelFormService.getEventTypeModel).toHaveBeenCalled();
      expect(eventTypeModelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventTypeModel>>();
      const eventTypeModel = { id: 123 };
      jest.spyOn(eventTypeModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventTypeModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventTypeModelService.update).toHaveBeenCalled();
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

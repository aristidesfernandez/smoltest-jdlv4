import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventTypeModelFormService, EventTypeModelFormGroup } from './event-type-model-form.service';
import { IEventTypeModel } from '../event-type-model.model';
import { EventTypeModelService } from '../service/event-type-model.service';
import { IEventType } from 'app/entities/event-type/event-type.model';
import { EventTypeService } from 'app/entities/event-type/service/event-type.service';

@Component({
  selector: 'jhi-event-type-model-update',
  templateUrl: './event-type-model-update.component.html',
})
export class EventTypeModelUpdateComponent implements OnInit {
  isSaving = false;
  eventTypeModel: IEventTypeModel | null = null;

  eventTypesSharedCollection: IEventType[] = [];

  editForm: EventTypeModelFormGroup = this.eventTypeModelFormService.createEventTypeModelFormGroup();

  constructor(
    protected eventTypeModelService: EventTypeModelService,
    protected eventTypeModelFormService: EventTypeModelFormService,
    protected eventTypeService: EventTypeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventType = (o1: IEventType | null, o2: IEventType | null): boolean => this.eventTypeService.compareEventType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventTypeModel }) => {
      this.eventTypeModel = eventTypeModel;
      if (eventTypeModel) {
        this.updateForm(eventTypeModel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventTypeModel = this.eventTypeModelFormService.getEventTypeModel(this.editForm);
    if (eventTypeModel.id !== null) {
      this.subscribeToSaveResponse(this.eventTypeModelService.update(eventTypeModel));
    } else {
      this.subscribeToSaveResponse(this.eventTypeModelService.create(eventTypeModel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventTypeModel>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(eventTypeModel: IEventTypeModel): void {
    this.eventTypeModel = eventTypeModel;
    this.eventTypeModelFormService.resetForm(this.editForm, eventTypeModel);

    this.eventTypesSharedCollection = this.eventTypeService.addEventTypeToCollectionIfMissing<IEventType>(
      this.eventTypesSharedCollection,
      eventTypeModel.eventType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventTypeService
      .query()
      .pipe(map((res: HttpResponse<IEventType[]>) => res.body ?? []))
      .pipe(
        map((eventTypes: IEventType[]) =>
          this.eventTypeService.addEventTypeToCollectionIfMissing<IEventType>(eventTypes, this.eventTypeModel?.eventType)
        )
      )
      .subscribe((eventTypes: IEventType[]) => (this.eventTypesSharedCollection = eventTypes));
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventTypeModel } from '../event-type-model.model';

@Component({
  selector: 'jhi-event-type-model-detail',
  templateUrl: './event-type-model-detail.component.html',
})
export class EventTypeModelDetailComponent implements OnInit {
  eventTypeModel: IEventTypeModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventTypeModel }) => {
      this.eventTypeModel = eventTypeModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

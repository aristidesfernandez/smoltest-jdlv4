import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventTypeModel } from '../event-type-model.model';
import { EventTypeModelService } from '../service/event-type-model.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-type-model-delete-dialog.component.html',
})
export class EventTypeModelDeleteDialogComponent {
  eventTypeModel?: IEventTypeModel;

  constructor(protected eventTypeModelService: EventTypeModelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventTypeModelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

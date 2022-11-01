import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandDevice } from '../command-device.model';
import { CommandDeviceService } from '../service/command-device.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './command-device-delete-dialog.component.html',
})
export class CommandDeviceDeleteDialogComponent {
  commandDevice?: ICommandDevice;

  constructor(protected commandDeviceService: CommandDeviceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandDeviceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommandDeviceComponent } from './list/command-device.component';
import { CommandDeviceDetailComponent } from './detail/command-device-detail.component';
import { CommandDeviceUpdateComponent } from './update/command-device-update.component';
import { CommandDeviceDeleteDialogComponent } from './delete/command-device-delete-dialog.component';
import { CommandDeviceRoutingModule } from './route/command-device-routing.module';

@NgModule({
  imports: [SharedModule, CommandDeviceRoutingModule],
  declarations: [CommandDeviceComponent, CommandDeviceDetailComponent, CommandDeviceUpdateComponent, CommandDeviceDeleteDialogComponent],
})
export class CommandDeviceModule {}

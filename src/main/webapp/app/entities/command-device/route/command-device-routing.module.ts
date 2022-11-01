import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommandDeviceComponent } from '../list/command-device.component';
import { CommandDeviceDetailComponent } from '../detail/command-device-detail.component';
import { CommandDeviceUpdateComponent } from '../update/command-device-update.component';
import { CommandDeviceRoutingResolveService } from './command-device-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const commandDeviceRoute: Routes = [
  {
    path: '',
    component: CommandDeviceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommandDeviceDetailComponent,
    resolve: {
      commandDevice: CommandDeviceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommandDeviceUpdateComponent,
    resolve: {
      commandDevice: CommandDeviceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommandDeviceUpdateComponent,
    resolve: {
      commandDevice: CommandDeviceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commandDeviceRoute)],
  exports: [RouterModule],
})
export class CommandDeviceRoutingModule {}

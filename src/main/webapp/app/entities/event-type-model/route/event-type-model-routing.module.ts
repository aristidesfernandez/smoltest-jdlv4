import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventTypeModelComponent } from '../list/event-type-model.component';
import { EventTypeModelDetailComponent } from '../detail/event-type-model-detail.component';
import { EventTypeModelUpdateComponent } from '../update/event-type-model-update.component';
import { EventTypeModelRoutingResolveService } from './event-type-model-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventTypeModelRoute: Routes = [
  {
    path: '',
    component: EventTypeModelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventTypeModelDetailComponent,
    resolve: {
      eventTypeModel: EventTypeModelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventTypeModelUpdateComponent,
    resolve: {
      eventTypeModel: EventTypeModelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventTypeModelUpdateComponent,
    resolve: {
      eventTypeModel: EventTypeModelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventTypeModelRoute)],
  exports: [RouterModule],
})
export class EventTypeModelRoutingModule {}

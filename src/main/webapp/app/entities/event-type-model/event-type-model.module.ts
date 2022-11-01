import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventTypeModelComponent } from './list/event-type-model.component';
import { EventTypeModelDetailComponent } from './detail/event-type-model-detail.component';
import { EventTypeModelUpdateComponent } from './update/event-type-model-update.component';
import { EventTypeModelDeleteDialogComponent } from './delete/event-type-model-delete-dialog.component';
import { EventTypeModelRoutingModule } from './route/event-type-model-routing.module';

@NgModule({
  imports: [SharedModule, EventTypeModelRoutingModule],
  declarations: [
    EventTypeModelComponent,
    EventTypeModelDetailComponent,
    EventTypeModelUpdateComponent,
    EventTypeModelDeleteDialogComponent,
  ],
})
export class EventTypeModelModule {}

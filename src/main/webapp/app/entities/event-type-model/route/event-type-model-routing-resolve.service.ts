import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventTypeModel } from '../event-type-model.model';
import { EventTypeModelService } from '../service/event-type-model.service';

@Injectable({ providedIn: 'root' })
export class EventTypeModelRoutingResolveService implements Resolve<IEventTypeModel | null> {
  constructor(protected service: EventTypeModelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventTypeModel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventTypeModel: HttpResponse<IEventTypeModel>) => {
          if (eventTypeModel.body) {
            return of(eventTypeModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

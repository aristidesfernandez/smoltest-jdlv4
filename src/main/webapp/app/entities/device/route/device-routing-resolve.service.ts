import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDevice } from '../device.model';
import { DeviceService } from '../service/device.service';

@Injectable({ providedIn: 'root' })
export class DeviceRoutingResolveService implements Resolve<IDevice | null> {
  constructor(protected service: DeviceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevice | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((device: HttpResponse<IDevice>) => {
          if (device.body) {
            return of(device.body);
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

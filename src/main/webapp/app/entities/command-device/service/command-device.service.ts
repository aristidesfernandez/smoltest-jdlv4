import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommandDevice, NewCommandDevice } from '../command-device.model';

export type PartialUpdateCommandDevice = Partial<ICommandDevice> & Pick<ICommandDevice, 'id'>;

export type EntityResponseType = HttpResponse<ICommandDevice>;
export type EntityArrayResponseType = HttpResponse<ICommandDevice[]>;

@Injectable({ providedIn: 'root' })
export class CommandDeviceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/command-devices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(commandDevice: NewCommandDevice): Observable<EntityResponseType> {
    return this.http.post<ICommandDevice>(this.resourceUrl, commandDevice, { observe: 'response' });
  }

  update(commandDevice: ICommandDevice): Observable<EntityResponseType> {
    return this.http.put<ICommandDevice>(`${this.resourceUrl}/${this.getCommandDeviceIdentifier(commandDevice)}`, commandDevice, {
      observe: 'response',
    });
  }

  partialUpdate(commandDevice: PartialUpdateCommandDevice): Observable<EntityResponseType> {
    return this.http.patch<ICommandDevice>(`${this.resourceUrl}/${this.getCommandDeviceIdentifier(commandDevice)}`, commandDevice, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommandDevice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommandDevice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommandDeviceIdentifier(commandDevice: Pick<ICommandDevice, 'id'>): number {
    return commandDevice.id;
  }

  compareCommandDevice(o1: Pick<ICommandDevice, 'id'> | null, o2: Pick<ICommandDevice, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommandDeviceIdentifier(o1) === this.getCommandDeviceIdentifier(o2) : o1 === o2;
  }

  addCommandDeviceToCollectionIfMissing<Type extends Pick<ICommandDevice, 'id'>>(
    commandDeviceCollection: Type[],
    ...commandDevicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const commandDevices: Type[] = commandDevicesToCheck.filter(isPresent);
    if (commandDevices.length > 0) {
      const commandDeviceCollectionIdentifiers = commandDeviceCollection.map(
        commandDeviceItem => this.getCommandDeviceIdentifier(commandDeviceItem)!
      );
      const commandDevicesToAdd = commandDevices.filter(commandDeviceItem => {
        const commandDeviceIdentifier = this.getCommandDeviceIdentifier(commandDeviceItem);
        if (commandDeviceCollectionIdentifiers.includes(commandDeviceIdentifier)) {
          return false;
        }
        commandDeviceCollectionIdentifiers.push(commandDeviceIdentifier);
        return true;
      });
      return [...commandDevicesToAdd, ...commandDeviceCollection];
    }
    return commandDeviceCollection;
  }
}

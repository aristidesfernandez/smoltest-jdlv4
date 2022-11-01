import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommandDeviceFormService, CommandDeviceFormGroup } from './command-device-form.service';
import { ICommandDevice } from '../command-device.model';
import { CommandDeviceService } from '../service/command-device.service';
import { ICommand } from 'app/entities/command/command.model';
import { CommandService } from 'app/entities/command/service/command.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';

@Component({
  selector: 'jhi-command-device-update',
  templateUrl: './command-device-update.component.html',
})
export class CommandDeviceUpdateComponent implements OnInit {
  isSaving = false;
  commandDevice: ICommandDevice | null = null;

  commandsSharedCollection: ICommand[] = [];
  devicesSharedCollection: IDevice[] = [];

  editForm: CommandDeviceFormGroup = this.commandDeviceFormService.createCommandDeviceFormGroup();

  constructor(
    protected commandDeviceService: CommandDeviceService,
    protected commandDeviceFormService: CommandDeviceFormService,
    protected commandService: CommandService,
    protected deviceService: DeviceService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCommand = (o1: ICommand | null, o2: ICommand | null): boolean => this.commandService.compareCommand(o1, o2);

  compareDevice = (o1: IDevice | null, o2: IDevice | null): boolean => this.deviceService.compareDevice(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandDevice }) => {
      this.commandDevice = commandDevice;
      if (commandDevice) {
        this.updateForm(commandDevice);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandDevice = this.commandDeviceFormService.getCommandDevice(this.editForm);
    if (commandDevice.id !== null) {
      this.subscribeToSaveResponse(this.commandDeviceService.update(commandDevice));
    } else {
      this.subscribeToSaveResponse(this.commandDeviceService.create(commandDevice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandDevice>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(commandDevice: ICommandDevice): void {
    this.commandDevice = commandDevice;
    this.commandDeviceFormService.resetForm(this.editForm, commandDevice);

    this.commandsSharedCollection = this.commandService.addCommandToCollectionIfMissing<ICommand>(
      this.commandsSharedCollection,
      commandDevice.command
    );
    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing<IDevice>(
      this.devicesSharedCollection,
      commandDevice.device
    );
  }

  protected loadRelationshipsOptions(): void {
    this.commandService
      .query()
      .pipe(map((res: HttpResponse<ICommand[]>) => res.body ?? []))
      .pipe(
        map((commands: ICommand[]) => this.commandService.addCommandToCollectionIfMissing<ICommand>(commands, this.commandDevice?.command))
      )
      .subscribe((commands: ICommand[]) => (this.commandsSharedCollection = commands));

    this.deviceService
      .query()
      .pipe(map((res: HttpResponse<IDevice[]>) => res.body ?? []))
      .pipe(map((devices: IDevice[]) => this.deviceService.addDeviceToCollectionIfMissing<IDevice>(devices, this.commandDevice?.device)))
      .subscribe((devices: IDevice[]) => (this.devicesSharedCollection = devices));
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandDevice } from '../command-device.model';

@Component({
  selector: 'jhi-command-device-detail',
  templateUrl: './command-device-detail.component.html',
})
export class CommandDeviceDetailComponent implements OnInit {
  commandDevice: ICommandDevice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandDevice }) => {
      this.commandDevice = commandDevice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

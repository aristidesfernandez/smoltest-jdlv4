import { ICommand } from 'app/entities/command/command.model';
import { IDevice } from 'app/entities/device/device.model';

export interface ICommandDevice {
  id: number;
  command?: Pick<ICommand, 'id'> | null;
  device?: Pick<IDevice, 'id'> | null;
}

export type NewCommandDevice = Omit<ICommandDevice, 'id'> & { id: null };

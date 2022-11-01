import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommandDeviceFormService } from './command-device-form.service';
import { CommandDeviceService } from '../service/command-device.service';
import { ICommandDevice } from '../command-device.model';
import { ICommand } from 'app/entities/command/command.model';
import { CommandService } from 'app/entities/command/service/command.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';

import { CommandDeviceUpdateComponent } from './command-device-update.component';

describe('CommandDevice Management Update Component', () => {
  let comp: CommandDeviceUpdateComponent;
  let fixture: ComponentFixture<CommandDeviceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commandDeviceFormService: CommandDeviceFormService;
  let commandDeviceService: CommandDeviceService;
  let commandService: CommandService;
  let deviceService: DeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommandDeviceUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CommandDeviceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommandDeviceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commandDeviceFormService = TestBed.inject(CommandDeviceFormService);
    commandDeviceService = TestBed.inject(CommandDeviceService);
    commandService = TestBed.inject(CommandService);
    deviceService = TestBed.inject(DeviceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Command query and add missing value', () => {
      const commandDevice: ICommandDevice = { id: 456 };
      const command: ICommand = { id: 89614 };
      commandDevice.command = command;

      const commandCollection: ICommand[] = [{ id: 53260 }];
      jest.spyOn(commandService, 'query').mockReturnValue(of(new HttpResponse({ body: commandCollection })));
      const additionalCommands = [command];
      const expectedCollection: ICommand[] = [...additionalCommands, ...commandCollection];
      jest.spyOn(commandService, 'addCommandToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commandDevice });
      comp.ngOnInit();

      expect(commandService.query).toHaveBeenCalled();
      expect(commandService.addCommandToCollectionIfMissing).toHaveBeenCalledWith(
        commandCollection,
        ...additionalCommands.map(expect.objectContaining)
      );
      expect(comp.commandsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Device query and add missing value', () => {
      const commandDevice: ICommandDevice = { id: 456 };
      const device: IDevice = { id: '2d38542f-d380-402f-9a9d-989b19865ff7' };
      commandDevice.device = device;

      const deviceCollection: IDevice[] = [{ id: '660c4cb2-653c-49fc-9040-10c22e6a082e' }];
      jest.spyOn(deviceService, 'query').mockReturnValue(of(new HttpResponse({ body: deviceCollection })));
      const additionalDevices = [device];
      const expectedCollection: IDevice[] = [...additionalDevices, ...deviceCollection];
      jest.spyOn(deviceService, 'addDeviceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commandDevice });
      comp.ngOnInit();

      expect(deviceService.query).toHaveBeenCalled();
      expect(deviceService.addDeviceToCollectionIfMissing).toHaveBeenCalledWith(
        deviceCollection,
        ...additionalDevices.map(expect.objectContaining)
      );
      expect(comp.devicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commandDevice: ICommandDevice = { id: 456 };
      const command: ICommand = { id: 29604 };
      commandDevice.command = command;
      const device: IDevice = { id: 'f89e8e4d-e21f-4a62-b711-3dbe727b9c5f' };
      commandDevice.device = device;

      activatedRoute.data = of({ commandDevice });
      comp.ngOnInit();

      expect(comp.commandsSharedCollection).toContain(command);
      expect(comp.devicesSharedCollection).toContain(device);
      expect(comp.commandDevice).toEqual(commandDevice);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommandDevice>>();
      const commandDevice = { id: 123 };
      jest.spyOn(commandDeviceFormService, 'getCommandDevice').mockReturnValue(commandDevice);
      jest.spyOn(commandDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commandDevice }));
      saveSubject.complete();

      // THEN
      expect(commandDeviceFormService.getCommandDevice).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commandDeviceService.update).toHaveBeenCalledWith(expect.objectContaining(commandDevice));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommandDevice>>();
      const commandDevice = { id: 123 };
      jest.spyOn(commandDeviceFormService, 'getCommandDevice').mockReturnValue({ id: null });
      jest.spyOn(commandDeviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandDevice: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commandDevice }));
      saveSubject.complete();

      // THEN
      expect(commandDeviceFormService.getCommandDevice).toHaveBeenCalled();
      expect(commandDeviceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommandDevice>>();
      const commandDevice = { id: 123 };
      jest.spyOn(commandDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commandDeviceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCommand', () => {
      it('Should forward to commandService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(commandService, 'compareCommand');
        comp.compareCommand(entity, entity2);
        expect(commandService.compareCommand).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDevice', () => {
      it('Should forward to deviceService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(deviceService, 'compareDevice');
        comp.compareDevice(entity, entity2);
        expect(deviceService.compareDevice).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

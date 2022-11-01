export interface ICommand {
  id: number;
  code?: string | null;
  description?: string | null;
  processor?: string | null;
}

export type NewCommand = Omit<ICommand, 'id'> & { id: null };

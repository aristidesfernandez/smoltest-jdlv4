import { IFormula } from 'app/entities/formula/formula.model';
import { ICounterType } from 'app/entities/counter-type/counter-type.model';

export interface IFormulaCounterType {
  id: number;
  formula?: Pick<IFormula, 'id'> | null;
  counterType?: Pick<ICounterType, 'counterCode'> | null;
}

export type NewFormulaCounterType = Omit<IFormulaCounterType, 'id'> & { id: null };

export interface ICategory {
  id?: string;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ICategory> = {};

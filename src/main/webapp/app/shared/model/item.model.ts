export interface IItem {
  id?: string;
  name?: string | null;
  price?: string | null;
}

export const defaultValue: Readonly<IItem> = {};

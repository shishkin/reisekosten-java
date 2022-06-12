export type Travel = {
  start: Date;
  reason: string;
  destination: string;
  allowance: number;
};

export type Report = {
  travels: Travel[];
  totalSum: number;
};

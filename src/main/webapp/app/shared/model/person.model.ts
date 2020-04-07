export interface IPerson {
  id?: number;
  userId?: number;
  mobileNo?: string;
  deviceId?: string;
  verificationCode?: string;
  status?: number;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public userId?: number,
    public mobileNo?: string,
    public deviceId?: string,
    public verificationCode?: string,
    public status?: number
  ) {}
}

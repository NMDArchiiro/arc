import {AdministrativeUnit} from "./administrative-unit.model";

export class HealthOrganization {
  id: string;
  name: string;
  code: string;
  orgType: number;
  level: number;
  parentId: string;
  parentDto: HealthOrganization;
  childrenDto: HealthOrganization[];
  regionUnit: AdministrativeUnit;
  nationalUnit: AdministrativeUnit;
  provinceUnit: AdministrativeUnit;
  districtUnit: AdministrativeUnit;
  communeUnit: AdministrativeUnit;
  administrativeUnitList: AdministrativeUnit[];

  constructor() {
  }

}

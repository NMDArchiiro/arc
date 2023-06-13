import { AdministrativeUnit } from "./administrative-unit.model";
import { HealthOrganization } from "./health-organization.model";
import { ValueCode } from "./value_code.model";

export class User {
  roles?: string[];
  accountType?: number;
  admin?: boolean;
  canEdit?: boolean;
  phoneNumber?: number;
  provinceUnit?: AdministrativeUnit;
  districtUnit?: AdministrativeUnit;
  communeUnit?: AdministrativeUnit;
  healthOrganization?: HealthOrganization;
  administrativeUnitList?: AdministrativeUnit[];
  serviceType?: ValueCode[];
  hivInfo?: boolean;
  vaacReport?: boolean;
  hasHriReport?: boolean;
  hasAdviseTestingHivReport?: boolean;
  hasMethadoneReport?: boolean;
  hasARVTreatmentReport?: boolean;
  hasCoInfectionReport?: boolean;
  hasMotherToChildReport?: boolean;
  hasChildrenReport?: boolean;
  hasPrepReport?: boolean;
  hasPreventionOfCommunicationReport?: boolean;
  hasRemainPrepReport?: boolean;
  hasHighRiskGroupReport?: boolean;
  hasServiceProvidedReport?: boolean;
  hasImplementationCostsReport?: boolean;
  hasCoInfectionHepatitisCReport?: boolean;
  hasHealthInsuranceReport?: boolean;
  displayName?: string;
}
export interface Role {
  name?: string;
  id?: number;
}

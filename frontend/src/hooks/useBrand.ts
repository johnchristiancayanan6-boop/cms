import {
  apiUrl,
  BrandRawConfig,
  brandRawConfig,
  customLogoPaths
} from '../config';
import { useSelector } from '../store';
import { useLicenseEntitlement } from './useLicenseEntitlement';

const DEFAULT_WHITE_LOGO = '/static/images/logo/eastwest-logo-white.svg';
const DEFAULT_DARK_LOGO = '/static/images/logo/eastwest-logo.svg';
const CUSTOM_DARK_LOGO = `${apiUrl}images/custom-logo.png`;
const CUSTOM_WHITE_LOGO = `${apiUrl}images/custom-logo-white.png`;

interface BrandConfig extends BrandRawConfig {
  logo: { white: string; dark: string };
}
export function useBrand(): BrandConfig {
  const defaultBrand: Omit<BrandConfig, 'logo'> = {
    name: 'EastWest BPO - MCI',
    shortName: 'EWBPO',
    website: 'https://eastwest-bpo-mci.com',
    mail: 'support@eastwest-bpo-mci.com',
    phone: '+63 000 000 0000',
    addressStreet: 'Philippines',
    addressCity: 'Philippines'
  };
  const isLicenseValid = useLicenseEntitlement('BRANDING');
  return {
    logo: {
      white: customLogoPaths
        ? isLicenseValid == null
          ? null
          : isLicenseValid
          ? CUSTOM_WHITE_LOGO
          : DEFAULT_WHITE_LOGO
        : DEFAULT_WHITE_LOGO,
      dark: customLogoPaths
        ? isLicenseValid == null
          ? null
          : isLicenseValid
          ? CUSTOM_DARK_LOGO
          : DEFAULT_DARK_LOGO
        : DEFAULT_DARK_LOGO
    },
    ...(isLicenseValid && brandRawConfig ? brandRawConfig : defaultBrand)
  };
}

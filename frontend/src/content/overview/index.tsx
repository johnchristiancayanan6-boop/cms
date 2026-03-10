import {
  Box,
  Container,
  Grid,
  Link,
  styled,
  Typography,
  Stack
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import Hero from './Hero';
import Highlights from './Highlights';
import NavBar from '../../components/NavBar';
import { useEffect } from 'react';
import { IS_ORIGINAL_CLOUD, isCloudVersion } from '../../config';
import { useBrand } from '../../hooks/useBrand';
import { useSelector } from '../../store';
import {
  Facebook,
  Twitter,
  Instagram,
  Phone,
  Mail,
  Sms,
  LinkedIn
} from '@mui/icons-material';
import { Footer } from 'src/components/Footer';
import CompanyLogos from '../landing/components/CompanyLogos';

const OverviewWrapper = styled(Box)(
  ({ theme }) => `
    overflow: auto;
    background: ${theme.palette.common.white};
    flex: 1;
    overflow-x: hidden;
`
);

const ldJson = [
  {
    '@context': 'https://schema.org',
    '@type': 'SoftwareApplication',
    name: 'EastWest BPO - MCI',
    description:
      'EastWest BPO - MCI is a free, open-source CMMS to manage work orders, preventive maintenance, assets, and facilities.',
    applicationCategory: 'BusinessApplication',
    operatingSystem: 'Web',
    url: 'https://eastwest-bpo-mci.com/',
    screenshot:
      'https://eastwest-bpo-mci.com/static/images/overview/work_orders_screenshot.png',
    // aggregateRating: {
    //   '@type': 'AggregateRating',
    //   ratingValue: '4.5',
    //   reviewCount: '5',
    //   bestRating: '5',
    //   worstRating: '1'
    // },
    publisher: {
      '@type': 'Organization',
      name: 'EastWest BPO - MCI',
      url: 'https://eastwest-bpo-mci.com/'
    },
    offers: {
      '@type': 'Offer',
      price: '0',
      priceCurrency: 'USD'
    }
  },
  {
    '@context': 'https://schema.org',
    '@type': 'MobileApplication',
    name: 'EastWest BPO - MCI for iOS',
    operatingSystem: 'iOS',
    applicationCategory: 'BusinessApplication',
    downloadUrl: 'https://apps.apple.com/us/app/id6751547284',
    offers: {
      '@type': 'Offer',
      price: '0',
      priceCurrency: 'USD'
    }
  },
  {
    '@context': 'https://schema.org',
    '@type': 'MobileApplication',
    name: 'EastWest BPO - MCI for Android',
    operatingSystem: 'Android',
    applicationCategory: 'BusinessApplication',
    downloadUrl: 'https://play.google.com/store/apps/details?id=com.eastwest.bpomci',
    offers: {
      '@type': 'Offer',
      price: '0',
      priceCurrency: 'USD'
    }
  }
];

function Overview() {
  const { t }: { t: any } = useTranslation();
  const navigate = useNavigate();
  const brandConfig = useBrand();

  useEffect(() => {
    if (!isCloudVersion) navigate('/account/login');
  }, [isCloudVersion]);

  return (
    <OverviewWrapper>
      <Helmet>
        <title>{IS_ORIGINAL_CLOUD ? t('main.title') : brandConfig.name}</title>
        <meta
          name="description"
          content="EastWest BPO - MCI is a free, open-source CMMS to manage work orders, preventive maintenance, assets, and facilities. Streamline maintenance operations today."
        />
        <meta
          name="keywords"
          content="CMMS, computerized maintenance management system, EAM, enterprise asset management, open source CMMS, free maintenance software, work order management, preventive maintenance, asset tracking, facility management, maintenance tracking software, equipment maintenance, EastWest BPO - MCI"
        />
        <link rel="canonical" href="https://eastwest-bpo-mci.com/" />
        <link
          rel="preload"
          as="image"
          href="/static/mobile_app.jpeg"
          //@ts-ignore
          fetchpriority="high"
        />
        <link
          rel="preload"
          as="image"
          href="/static/images/overview/work_orders_screenshot.png"
          //@ts-ignore
          fetchpriority="high"
        />
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{
            __html: JSON.stringify(ldJson)
          }}
        />
      </Helmet>
      <NavBar />
      <Hero />
      <CompanyLogos sx={{ mt: { xs: '150px', md: '100px' } }} />
      <Highlights />
      <Footer />
    </OverviewWrapper>
  );
}

export default Overview;

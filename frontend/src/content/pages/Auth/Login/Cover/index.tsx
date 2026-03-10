import { Link as RouterLink } from 'react-router-dom';
import { Box, Card, Container, Link, styled, Typography } from '@mui/material';
import { Helmet } from 'react-helmet-async';
import JWTLogin from '../LoginJWT';

import { useTranslation } from 'react-i18next';

const Content = styled(Box)(
  () => `
    display: flex;
    flex: 1;
    width: 100%;
    min-height: 100vh;
    align-items: center;
    justify-content: center;
    background: radial-gradient(circle at 18% 12%, rgba(28, 138, 216, 0.18) 0%, transparent 45%),
      radial-gradient(circle at 82% 80%, rgba(255, 122, 0, 0.14) 0%, transparent 40%),
      #eef3f9;
`
);

function LoginCover() {
  const { t }: { t: any } = useTranslation();

  return (
    <>
      <Helmet>
        <title>{t('Login')}</title>
        <meta name="robots" content="noindex, nofollow" />
      </Helmet>
      <Content>
        <Container
          sx={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            flexDirection: 'column'
          }}
          maxWidth="sm"
        >
          <Card
            sx={{
              p: 4,
              my: 0,
              width: '100%',
              borderRadius: 3,
              border: '1px solid rgba(16, 49, 92, 0.12)',
              boxShadow: '0 22px 45px rgba(16, 40, 77, 0.12)'
            }}
          >
            <Box textAlign="center">
              <Box
                component="img"
                src="/static/images/logo/logo.webp"
                alt="EastWest BPO - MCI"
                sx={{
                  width: '100%',
                  maxWidth: 76,
                  height: 'auto',
                  objectFit: 'contain',
                  mx: 'auto',
                  mb: 1.2
                }}
              />
              <Typography
                variant="h3"
                sx={{
                  fontWeight: 800,
                  color: '#1459A6',
                  mb: 1.2
                }}
              >
                EastWest BPO - MCI (CMMS System)
              </Typography>
              <Typography
                variant="h2"
                sx={{
                  mb: 1
                }}
              >
                {t('login')}
              </Typography>
              <Typography
                variant="h4"
                color="text.secondary"
                fontWeight="normal"
                sx={{
                  mb: 3
                }}
              >
                {t('login_description')}
              </Typography>
            </Box>
            <JWTLogin />
            <Box my={4}>
              <Typography
                component="span"
                variant="subtitle2"
                color="text.primary"
                fontWeight="bold"
              >
                {t('no_account_yet')}
              </Typography>{' '}
              <Box display={{ xs: 'block', md: 'inline-block' }}>
                <Link component={RouterLink} to="/account/register">
                  <b>{t('signup_here')}</b>
                </Link>
              </Box>
            </Box>
          </Card>
        </Container>
      </Content>
    </>
  );
}

export default LoginCover;

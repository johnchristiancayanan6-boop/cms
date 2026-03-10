import { Link as RouterLink, useSearchParams } from 'react-router-dom';
import {
  alpha,
  Box,
  Card,
  Container,
  Divider,
  Link,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  styled,
  Typography
} from '@mui/material';
import { Helmet } from 'react-helmet-async';
import JWTRegister from '../RegisterJWT';
import { useTranslation } from 'react-i18next';
import CheckCircleOutlineTwoToneIcon from '@mui/icons-material/CheckCircleOutlineTwoTone';
import Scrollbar from 'src/components/Scrollbar';
import CompanyLogos from '../../../../landing/components/CompanyLogos';
import { isCloudVersion } from '../../../../../config';

const Content = styled(Box)(
  () => `
    display: flex;
    flex: 1;
    width: 100%;
    background: radial-gradient(circle at 18% 12%, rgba(28, 138, 216, 0.16) 0%, transparent 45%),
      radial-gradient(circle at 82% 80%, rgba(255, 122, 0, 0.12) 0%, transparent 40%),
      #eef3f9;
`
);

const MainContent = styled(Box)(
  ({ theme }) => `
    @media (min-width: ${theme.breakpoints.values.md}px) {
      padding: 0 0 0 500px;
    }
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
`
);

const SidebarWrapper = styled(Box)(
  ({ theme }) => `
  position: fixed;
  left: 0;
  top: 0;
  height: 100%;
  width: 500px;
  background: radial-gradient(circle at 8% 15%, rgba(59, 158, 236, 0.2) 0%, transparent 42%),
    linear-gradient(160deg, #0C2040 0%, #17355E 58%, #1D4A7B 100%);
`
);

const SidebarContent = styled(Box)(
  ({ theme }) => `
  display: flex;
  flex-direction: column;
  padding: ${theme.spacing(2, 6)};
`
);

const CardImg = styled(Card)(
  ({ theme }) => `
    border-radius: 100%;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    position: relative;
    border: 8px solid ${alpha(theme.colors.alpha.trueWhite[100], 0.18)};
    transition: none;
    width: ${theme.spacing(13)};
    height: ${theme.spacing(13)};
    margin-bottom: ${theme.spacing(3)};
    overflow: hidden;
`
);

const TypographyPrimary = styled(Typography)(
  ({ theme }) => `
      color: ${theme.colors.alpha.trueWhite[100]};
`
);

const TypographySecondary = styled(Typography)(
  ({ theme }) => `
      color: ${theme.colors.alpha.trueWhite[70]};
`
);

const DividerWrapper = styled(Divider)(
  ({ theme }) => `
      background: ${theme.colors.alpha.trueWhite[10]};
`
);

const ListItemTextWrapper = styled(ListItemText)(
  ({ theme }) => `
      color: ${theme.colors.alpha.trueWhite[70]};
`
);
const ListItemIconWrapper = styled(ListItemIcon)(
  ({ theme }) => `
      color: ${theme.colors.success.main};
      min-width: 32px;
`
);

function RegisterCover() {
  const { t }: { t: any } = useTranslation();
  const [searchParams] = useSearchParams();
  return (
    <>
      <Helmet>
        <title>{t('register')}</title>
        <meta name="robots" content="noindex, nofollow" />
      </Helmet>
      <Content>
        <SidebarWrapper
          sx={{
            display: { xs: 'none', md: 'inline-block' }
          }}
        >
          <Scrollbar>
            <SidebarContent>
              <TypographyPrimary
                align="center"
                variant="h3"
                sx={{
                  mb: 4,
                  px: 8
                }}
              >
                {t('perfect_tool')}
              </TypographyPrimary>
              <Box textAlign="center" mb={5}>
                <CardImg>
                  <img
                    alt="EastWest BPO - MCI"
                    src="/static/images/logo/logo.webp"
                    style={{
                      width: '78%',
                      height: '78%',
                      objectFit: 'contain',
                      display: 'block'
                    }}
                  />
                </CardImg>
                <TypographyPrimary align="center" variant="h3" sx={{ mb: 2 }}>
                  {t('work_orders')}
                </TypographyPrimary>
                <TypographySecondary align="center" variant="subtitle2">
                  {t('work-orders.description.short')}
                </TypographySecondary>
              </Box>
              {isCloudVersion && (
                <>
                  <DividerWrapper
                    sx={{
                      my: 1
                    }}
                  />
                  <Box>
                    <List
                      dense
                      sx={{
                        mb: 1
                      }}
                    >
                      <ListItem disableGutters>
                        <ListItemIconWrapper>
                          <CheckCircleOutlineTwoToneIcon />
                        </ListItemIconWrapper>
                        <ListItemTextWrapper
                          primaryTypographyProps={{ variant: 'h6' }}
                          primary={t('premium_included')}
                        />
                      </ListItem>
                      <ListItem disableGutters>
                        <ListItemIconWrapper>
                          <CheckCircleOutlineTwoToneIcon />
                        </ListItemIconWrapper>
                        <ListItemTextWrapper
                          primaryTypographyProps={{ variant: 'h6' }}
                          primary={t('no_credit_card')}
                        />
                      </ListItem>
                    </List>
                  </Box>
                  <CompanyLogos white sx={{ mt: 2 }} compact />
                </>
              )}
            </SidebarContent>
          </Scrollbar>
        </SidebarWrapper>
        <MainContent>
          <Container maxWidth="sm">
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
                <Typography
                  variant="h2"
                  sx={{
                    mb: 1
                  }}
                >
                  {t('create_account')}
                </Typography>
                <Typography
                  variant="h4"
                  color="text.secondary"
                  fontWeight="normal"
                  sx={{
                    mb: 3
                  }}
                >
                  {t('signup_description')}
                </Typography>
              </Box>
              <JWTRegister
                email={searchParams.get('email')}
                role={Number(searchParams.get('role'))}
                subscriptionPlanId={searchParams.get('subscription-plan-id')}
              />
              <Box mt={4}>
                <Typography
                  component="span"
                  variant="subtitle2"
                  color="text.primary"
                  fontWeight="bold"
                >
                  {t('account_already')}
                </Typography>{' '}
                <Box display={{ xs: 'block', md: 'inline-block' }}>
                  <Link component={RouterLink} to="/account/login">
                    <b>{t('signin_here')}</b>
                  </Link>
                </Box>
              </Box>
            </Card>
          </Container>
        </MainContent>
      </Content>
    </>
  );
}

export default RegisterCover;

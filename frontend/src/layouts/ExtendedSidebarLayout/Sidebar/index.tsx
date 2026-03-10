import { useContext } from 'react';
import Scrollbar from 'src/components/Scrollbar';
import { SidebarContext } from 'src/contexts/SidebarContext';

import {
  alpha,
  Box,
  Divider,
  Drawer,
  styled,
  Typography,
  useTheme
} from '@mui/material';
import SidebarMenu from './SidebarMenu';
import SidebarFooter from './SidebarFooter';

const SidebarWrapper = styled(Box)(
  ({ theme }) => `
        width: ${theme.sidebar.width};
        min-width: ${theme.sidebar.width};
        color: ${theme.colors.alpha.trueWhite[70]};
        position: relative;
        z-index: 7;
        height: 100%;
        padding-bottom: 61px;
        overflow: hidden;
`
);

const SidebarBrandLogo = styled('img')(
  () => `
    display: block;
    width: 100%;
    max-width: 190px;
    height: auto;
    object-fit: contain;
  `
);

function Sidebar() {
  const { sidebarToggle, toggleSidebar } = useContext(SidebarContext);
  const closeSidebar = () => toggleSidebar();
  const theme = useTheme();

  return (
    <>
      <SidebarWrapper
        sx={{
          display: {
            xs: 'none',
            lg: 'inline-block'
          },
          position: 'fixed',
          left: 0,
          top: 0,
          background:
            'radial-gradient(circle at 15% 0%, rgba(52, 144, 223, 0.22) 0%, transparent 45%), linear-gradient(165deg, #0C2040 0%, #17355E 55%, #1D4A7B 100%)',
          boxShadow:
            theme.palette.mode === 'dark' ? theme.sidebar.boxShadow : 'none'
        }}
      >
        <Scrollbar>
          <Box mt={3}>
            <Box
              sx={{
                display: 'flex',
                justifyContent: 'center',
                flexDirection: 'row'
              }}
            >
              <Box sx={{ px: 2, width: '100%', display: 'flex', justifyContent: 'center' }}>
                <SidebarBrandLogo
                  src="/static/images/logo/sidelogo.png"
                  alt="EastWest BPO - MCI"
                />
              </Box>
            </Box>
            <Typography
              variant="caption"
              sx={{
                mt: 0.5,
                textAlign: 'center',
                display: 'block',
                color: alpha(theme.colors.alpha.trueWhite[100], 0.72),
                letterSpacing: 1.2
              }}
            >
              CMMS SYSTEM
            </Typography>
          </Box>
          <Divider
            sx={{
              mt: theme.spacing(1),
              mx: theme.spacing(2),
              background: theme.colors.alpha.trueWhite[10]
            }}
          />
          <SidebarMenu />
        </Scrollbar>
        <Divider
          sx={{
            background: theme.colors.alpha.trueWhite[10]
          }}
        />
        <SidebarFooter />
      </SidebarWrapper>
      <Drawer
        sx={{
          boxShadow: `${theme.sidebar.boxShadow}`
        }}
        anchor={theme.direction === 'rtl' ? 'right' : 'left'}
        open={sidebarToggle}
        onClose={closeSidebar}
        variant="temporary"
        elevation={9}
      >
        <SidebarWrapper
          sx={{
            background:
              'radial-gradient(circle at 15% 0%, rgba(52, 144, 223, 0.22) 0%, transparent 45%), linear-gradient(165deg, #0C2040 0%, #17355E 55%, #1D4A7B 100%)'
          }}
        >
          <Scrollbar>
            <Box mt={3}>
              <Box
                sx={{
                  display: 'flex',
                  justifyContent: 'center',
                  flexDirection: 'row'
                }}
              >
                <Box sx={{ px: 2, width: '100%', display: 'flex', justifyContent: 'center' }}>
                  <SidebarBrandLogo
                    src="/static/images/logo/sidelogo.png"
                    alt="EastWest BPO - MCI"
                  />
                </Box>
              </Box>
              <Typography
                variant="caption"
                sx={{
                  mt: 0.5,
                  textAlign: 'center',
                  display: 'block',
                  color: alpha(theme.colors.alpha.trueWhite[100], 0.72),
                  letterSpacing: 1.2
                }}
              >
                CMMS SYSTEM
              </Typography>
            </Box>
            <Divider
              sx={{
                mt: theme.spacing(1),
                mx: theme.spacing(2),
                background: theme.colors.alpha.trueWhite[10]
              }}
            />
            <SidebarMenu />
          </Scrollbar>
          <SidebarFooter />
        </SidebarWrapper>
      </Drawer>
    </>
  );
}

export default Sidebar;

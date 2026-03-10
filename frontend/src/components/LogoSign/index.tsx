import {
  Box,
  styled,
  useMediaQuery,
  useTheme
} from '@mui/material';
import { Link } from 'react-router-dom';

const LogoWrapper = styled(Link)(
  ({ theme }) => `
        color: ${theme.palette.text.primary};
        display: inline-flex;
        max-width: 100%;
        justify-content: center;
        text-decoration: none;
        align-items: center;
        margin: 0 auto;
        font-weight: ${theme.typography.fontWeightBold};
`
);

const LogoSignWrapper = styled(Box)(
  () => `
        display: flex;
        align-items: center;
        justify-content: center;
        max-width: 100%;
`
);

interface OwnProps {
  white?: boolean;
}

function Logo({ white }: OwnProps) {
  const theme = useTheme();
  const mobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <LogoWrapper to="/" title="">
      <LogoSignWrapper
        sx={{
          width: '100%'
        }}
      >
        <Box
          component="img"
          src="/static/images/logo/sidelogo.png"
          alt="EastWest BPO - MCI"
          sx={{
            width: '100%',
            maxWidth: mobile ? 180 : 220,
            height: 'auto',
            objectFit: 'contain',
            display: 'block'
          }}
        />
      </LogoSignWrapper>
    </LogoWrapper>
  );
}

export default Logo;

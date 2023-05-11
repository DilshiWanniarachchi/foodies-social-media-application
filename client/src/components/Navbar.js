import { useState } from "react";
import {
  Box,
  IconButton,
  InputBase,
  Typography,
  Select,
  MenuItem,
  FormControl,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import {
  Search,
  PeopleAlt,
  Restaurant,
  Notifications,
  Menu,
  Close,
} from "@mui/icons-material";
import HomeIcon from '@mui/icons-material/Home';
import { Outlet, useNavigate } from "react-router-dom";
import FlexBox from "components/FlexBox";
import { useSelector, useDispatch } from "react-redux";
import { setLogout } from "state/auth-hook";

const Navbar = () => {
  const [isMobileMenuToggled, setIsMobileMenuToggled] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const user = useSelector((state) => state.auth.user);
  const isAuth = useSelector((state) => state.auth.isAuth);
  const isNonMobileScreens = useMediaQuery("(min-width: 1000px)");

  const theme = useTheme();
  const neutralLight = theme.palette.neutral.light;
  const background = theme.palette.background.default;
  const primaryDark = theme.palette.primary.dark;
  const primary = theme.palette.primary.main;
  const alt = theme.palette.background.alt;

  const handleLogout = () => {
    dispatch(setLogout());
    navigate("/login");
  };

  return (
    <>
      <FlexBox padding="1rem 6%" backgroundColor={alt}>
        <FlexBox gap="1.75rem">
          <Typography
            fontWeight="bold"
            fontSize="clamp(1rem, 2rem, 2.25rem)"
            color="primary"
            onClick={() => navigate("/feed")}
            sx={{
              "&:hover": {
                color: primaryDark,
                cursor: "pointer",
              },
            }}
          >
            Foodies
          </Typography>
          {isNonMobileScreens && (
            <FlexBox
              backgroundColor={neutralLight}
              borderRadius="9px"
              gap="3rem"
              padding="0.1rem 1.5rem"
            >
              <InputBase placeholder="Search..." />
              <IconButton>
                <Search />
              </IconButton>
            </FlexBox>
          )}
        </FlexBox>

        {/* DESKTOP NAV */}
        {isNonMobileScreens ? (
          <FlexBox gap="2rem">
            <HomeIcon
              sx={{
                fontSize: "28px",
                "&:hover": {
                  color: primary,
                  cursor: "pointer",
                },
              }}
              onClick={() => navigate("/feed")}
            />
            <Restaurant
              sx={{
                fontSize: "25px",
                "&:hover": {
                  color: primary,
                  cursor: "pointer",
                },
              }}
              onClick={() => navigate("/recipes")}
            />
            <Notifications
              sx={{
                fontSize: "25px",
                "&:hover": {
                  color: primary,
                  cursor: "pointer",
                },
              }}
              onClick={() => navigate("/notifications")}
            />
            {isAuth && (
              <FormControl variant="standard" value={user.username}>
                <Select
                  value={user.username}
                  sx={{
                    backgroundColor: neutralLight,
                    width: "150px",
                    borderRadius: "0.25rem",
                    p: "0.25rem 1rem",
                    "& .MuiSvgIcon-root": {
                      pr: "0.25rem",
                      width: "3rem",
                    },
                    "& .MuiSelect-select:focus": {
                      backgroundColor: neutralLight,
                    },
                  }}
                  input={<InputBase />}
                >
                  <MenuItem value={user.username}>
                    <Typography>{user.username}</Typography>
                  </MenuItem>
                  <MenuItem onClick={handleLogout}>Log Out</MenuItem>
                </Select>
              </FormControl>
            )}
          </FlexBox>
        ) : (
          <IconButton
            onClick={() => setIsMobileMenuToggled(!isMobileMenuToggled)}
          >
            <Menu />
          </IconButton>
        )}

        {/* MOBILE NAV */}
        {!isNonMobileScreens && isMobileMenuToggled && (
          <Box
            position="fixed"
            right="0"
            bottom="0"
            height="100%"
            zIndex="10"
            maxWidth="500px"
            minWidth="300px"
            backgroundColor={background}
          >
            {/* CLOSE ICON */}
            <Box display="flex" justifyContent="flex-end" p="1rem">
              <IconButton
                onClick={() => setIsMobileMenuToggled(!isMobileMenuToggled)}
              >
                <Close />
              </IconButton>
            </Box>

            {/* MENU ITEMS */}
            <FlexBox
              display="flex"
              flexDirection="column"
              justifyContent="center"
              alignItems="center"
              gap="3rem"
            >
              <HomeIcon
                sx={{
                  fontSize: "28px",
                  "&:hover": {
                    color: primary,
                    cursor: "pointer",
                  },
                }}
                onClick={() => navigate("/feed")}
              />
              <Restaurant
                sx={{
                  fontSize: "25px",
                  "&:hover": {
                    color: primary,
                    cursor: "pointer",
                  },
                }}
                onClick={() => navigate("/recipes")}
              />
              <Notifications
                sx={{
                  fontSize: "25px",
                  "&:hover": {
                    color: primary,
                    cursor: "pointer",
                  },
                }}
              />
              {isAuth && (
                <FormControl variant="standard" value={user.username}>
                  <Select
                    value={user.username}
                    sx={{
                      backgroundColor: neutralLight,
                      width: "150px",
                      borderRadius: "0.25rem",
                      p: "0.25rem 1rem",
                      "& .MuiSvgIcon-root": {
                        pr: "0.25rem",
                        width: "3rem",
                      },
                      "& .MuiSelect-select:focus": {
                        backgroundColor: neutralLight,
                      },
                    }}
                    input={<InputBase />}
                  >
                    <MenuItem value={user.username}>
                      <Typography>{user.username}</Typography>
                    </MenuItem>
                    <MenuItem onClick={handleLogout}>Log Out</MenuItem>
                  </Select>
                </FormControl>
              )}
            </FlexBox>
          </Box>
        )}
      </FlexBox>
      <Outlet />
    </>
  );
};

export default Navbar;

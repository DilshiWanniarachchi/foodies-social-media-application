import React from "react";
import WidgetWrapper from "components/WidgetWrapper";
import FlexBox from "components/FlexBox";
import ProfileImage from "components/ProfileImage";
import { useGetFollowersQuery, useGetFollowingQuery, useGetUserByIdQuery } from "state/api-hook";

import {
  Box,
  Typography,
  useTheme,
  Divider,
  Paper,
  MenuList,
  MenuItem,
} from "@mui/material";
import { useNavigate } from "react-router";
import {
  ManageAccountsOutlined,
  DriveFileRenameOutlineOutlined,
  AttachEmailOutlined,
  Feed,
  Restaurant,
  TurnedIn,
} from "@mui/icons-material";

const UserProfileWidget = ({
  user,
  isSavedItems,
  setIsSavedItems,
  isPost,
  setIsPost,
  isRecipes,
  setIsRecipes,
  isFollowers,
  setIsFollowers,
  isFollowing,
  setIsFollowing
}) => {
  const { palette } = useTheme();
  const navigate = useNavigate();
  const dark = palette.neutral.dark;
  const medium = palette.neutral.medium;
  const main = palette.neutral.main;

  const { data } = useGetUserByIdQuery(
    { user },
    { refetchOnMountOrArgChange: true }
  );

  const { data:followers } = useGetFollowersQuery({ user });
  console.log(followers)
  const numberOfFollowers = followers ? followers.length : 0;
  console.log(numberOfFollowers)

  const { data:following } = useGetFollowingQuery({ user });
  const numberOfFollowing = following ? following.length : 0;
  console.log(numberOfFollowing)

  const postHandler = () => {
    setIsPost(true);
    setIsRecipes(false);
    setIsSavedItems(false);
    setIsFollowers(false);
    setIsFollowing(false);
  };
  const recipeHandler = () => {
    setIsRecipes(true);
    setIsPost(false);
    setIsSavedItems(false);
    setIsFollowers(false);
    setIsFollowing(false);
  };
  const savedItemsHandler = () => {
    setIsSavedItems(true);
    setIsRecipes(false);
    setIsPost(false);
    setIsFollowers(false);
    setIsFollowing(false);
  };
  const followersHandler = () => {
    setIsFollowers(true);
    setIsSavedItems(false);
    setIsRecipes(false);
    setIsPost(false);
    setIsFollowing(false);
  };
  const followingHandler = () => {
    setIsFollowing(true);
    setIsSavedItems(false);
    setIsRecipes(false);
    setIsPost(false);
    setIsFollowers(false);
  };

  return (
    <WidgetWrapper>
      {/* FIRST ROW */}
      {data && (
        <FlexBox
          gap="0.5rem"
          pb="1.1rem"
          onClick={() => navigate(`/profile/${user}`)}
        >
          <FlexBox gap="1rem">
            <ProfileImage image={data.picturePath} />
            <Box>
              <Typography
                variant="h4"
                color={dark}
                fontWeight="500"
                sx={{
                  "&:hover": {
                    color: palette.primary.light,
                    cursor: "pointer",
                  },
                }}
              >
                {data.fullName}
              </Typography>
              {/* <Typography color={medium}>{friends.length}</Typography> */}
            </Box>
          </FlexBox>
          <ManageAccountsOutlined />
        </FlexBox>
      )}
      <Divider />

      {/* SECOND ROW */}
      {data && (
        <Box p="1rem 0">
          <Box display="flex" alignItems="center" gap="1rem" mb="0.5rem">
            <AttachEmailOutlined fontSize="large" sx={{ color: main }} />
            <Typography color={medium}>{data.email}</Typography>
          </Box>
          <Box display="flex" alignItems="center" gap="1rem" mb="0.5rem">
            <DriveFileRenameOutlineOutlined
              fontSize="large"
              sx={{ color: main }}
            />
            <Typography color={medium}>{data.username}</Typography>
          </Box>
        </Box>
      )}
      <Divider />

      {/* THIRD ROW */}
      <Box p="1rem 0">
        <FlexBox mb="0.5rem">
          <Typography onClick={followersHandler} color={medium} fontWeight="500" fontSize="1rem"
            sx={{
              "&:hover": {
                cursor: "pointer",
                color : "black"
              },
            }}
          >Followers</Typography>
          <Typography color={main} fontWeight="500" fontSize="1rem">
            {numberOfFollowers}
          </Typography>
        </FlexBox>
        <FlexBox>
          <Typography onClick={followingHandler} color={medium} fontWeight="500" fontSize="1rem"
            sx={{
              "&:hover": {
                cursor: "pointer",
                color : "black"
              },
            }}
          >Following</Typography>
          <Typography color={main} fontWeight="500" fontSize="1rem">
            {numberOfFollowing}
          </Typography>
        </FlexBox>
      </Box>
      <Divider />

      {/* FOURTH ROW */}
      <Box p="1rem 0">
        <Typography fontSize="1rem" color={medium} fontWeight="500" mb="1rem">
          My Items
        </Typography>

        <MenuList>
          <MenuItem onClick={postHandler} sx={{ borderRadius: "1rem" }}>
            <Feed fontSize="large" sx={{ color: main }} />
            <Typography fontSize="1rem" color={main} fontWeight="500" ml="1rem">
              My Posts
            </Typography>
          </MenuItem>
          <MenuItem
            onClick={recipeHandler}
            sx={{ borderRadius: "1rem", mt: "1rem" }}
          >
            <Restaurant fontSize="large" sx={{ color: main }} />
            <Typography fontSize="1rem" color={main} fontWeight="500" ml="1rem">
              My Recipes
            </Typography>
          </MenuItem>
          <MenuItem
            onClick={savedItemsHandler}
            sx={{ borderRadius: "1rem", mt: "1rem" }}
          >
            <TurnedIn fontSize="large" sx={{ color: main }} />
            <Typography fontSize="1rem" color={main} fontWeight="500" ml="1rem">
              Saved Items
            </Typography>
          </MenuItem>
        </MenuList>
      </Box>
    </WidgetWrapper>
  );
};

export default UserProfileWidget;

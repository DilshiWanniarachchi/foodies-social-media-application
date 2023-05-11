import React, { useState } from "react";
import {
  Box,
  Typography,
  useTheme,
  Chip,
} from "@mui/material";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import ProfileImage from "components/ProfileImage";
import FlexBox from "components/FlexBox";
import WidgetWrapper from "components/WidgetWrapper";
import { setFollowing } from "state/auth-hook";

const Widget = ({
  friendId,
  email,
  username,
  userPicturePath,
  refetch,
  type,
}) => {
  const { palette } = useTheme();
  const main = palette.neutral.main;
  const medium = palette.neutral.medium;
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.accessToken);
  const loggedInUserId = useSelector((state) => state.auth.user.id);

  let chipLabel;
  let chipOnClick;

  const followUser = async () => {
    const response = await fetch(
      `http://localhost:8080/api/users/${loggedInUserId}/follow?followingId=${friendId}`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    const updatedUser = await response.json();
    if (!response.ok) {
      alert("Not Successful");
    } else {
      console.log(updatedUser);
      const following = updatedUser.following;
      dispatch(setFollowing({ following: following }));
    }
  }; 

  if (type === "suggestions") {
    chipLabel = "Follow back";
    chipOnClick = async () => {
      followUser();
    };
  } else if (type === "followers") {
    chipLabel = "View Profile";
    chipOnClick = async () => {
      navigate(`/profile/${friendId}`);
    };
  } else if (type === "following") {
    chipLabel = "Unfollow";
    chipOnClick = () => {
      followUser();
    };
  }

  return (
    <WidgetWrapper mb="1rem">
      <FlexBox>
        <FlexBox gap="1rem">
          <ProfileImage image={userPicturePath} size="55px" />
          <Box
            onClick={() => {
              navigate(`/profile/${friendId}`);
              navigate(0);
            }}
          >
            <Typography
              color={main}
              variant="h5"
              fontWeight="500"
              sx={{
                "&:hover": {
                  color: palette.primary.light,
                  cursor: "pointer",
                },
              }}
            >
              {username}
            </Typography>
            <Typography color={medium} fontSize="0.75rem">
              {email}
            </Typography>
          </Box>
        </FlexBox>
        <Chip label={chipLabel} onClick={chipOnClick}/>
      </FlexBox>
    </WidgetWrapper>
  );
};

export default Widget;
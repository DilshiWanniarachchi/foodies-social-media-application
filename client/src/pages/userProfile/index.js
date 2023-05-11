import React, { useState } from "react";
import { Box, useMediaQuery } from "@mui/material";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import PostsWidget from "pages/posts/PostsWidget";
import { useGetAllPostsByUserQuery } from "state/api-hook";
import UserProfileWidget from "./UserProfileWidget";
import MyRecipes from "./MyRecipes";
import SavedItems from "./SavedItems";
import Followers from "pages/friends/followers";
import Following from "pages/friends/following";

const Profile = () => {
  const [isPost, setIsPost] = useState(true);
  const [isRecipes, setIsRecipes] = useState(false);
  const [isSavedItems, setIsSavedItems] = useState(false);
  const [isFollowers, setIsFollowers] = useState(false);
  const [isFollowing, setIsFollowing] = useState(false);
  const isNonMobileScreens = useMediaQuery("(min-width:1000px)");
  const userId = useParams().id;
  const { data, refetch } = useGetAllPostsByUserQuery(
    { userId },
    { refetchOnMountOrArgChange: true }
  );

  return (
    <Box>
      <Box
        width="100%"
        padding="2rem 6%"
        display={isNonMobileScreens ? "flex" : "block"}
        gap="5rem"
        justifyContent="space-between"
      >
        <Box flexBasis={isNonMobileScreens ? "40%" : undefined}>
          <UserProfileWidget
            user={userId}
            isSavedItems={isSavedItems}
            setIsSavedItems={setIsSavedItems}
            isPost={isPost}
            setIsPost={setIsPost}
            isRecipes={isRecipes}
            setIsRecipes={setIsRecipes}
            isFollowers={isFollowers}
            setIsFollowers={setIsFollowers}
            isFollowing={isFollowing}
            setIsFollowing={setIsFollowing}
          />
        </Box>
        <Box
          flexBasis={isNonMobileScreens ? "60%" : undefined}
          sx={{
            height: 700,
            overflow: "hidden",
            overflowY: "scroll",
            "&::-webkit-scrollbar": {
              width: 0,
            },
          }}
        >
          {isPost && data && <PostsWidget posts={data} refetch={refetch} />}
          {isRecipes && <MyRecipes />}
          {isSavedItems && <SavedItems />}
          {isFollowers && <Followers />}
          {isFollowing && <Following />}
        </Box>
      </Box>
    </Box>
  );
};

export default Profile;

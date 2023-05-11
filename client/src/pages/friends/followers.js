import React from "react";
import {
  Box,
  useMediaQuery
} from "@mui/material";
import { useSelector } from "react-redux";
import { useGetFollowersQuery } from 'state/api-hook'
import Widget from "./Widget";

const Followers = () => {
  const isNonMobile = useMediaQuery("(min-width: 1000px");  

  const user = useSelector((state) => state.auth.user.id);
  const { data, refetch } = useGetFollowersQuery({ user });
  console.log(user)
  console.log(data)

  return (
    <>
      {data ? (
          <Box
            justifyContent="space-between"
            sx={{
              "& > div": { gridColumn: isNonMobile ? undefined : "span 3" },
            }}
          >
            {data && data.map(
              ({
                id,
                email,
                username,
                picturePath,
                refetch,
              }) => (
                <Widget
                  key={id}
                  friendId={id}
                  email={email}
                  username={username}
                  userPicturePath={picturePath}
                  refetch={refetch}
                  type="followers"
                />
              )
            )}
          </Box>
        ) : (
          <div justifyContent="center">No Followers</div>
        )}
    </>
  );
};

export default Followers;

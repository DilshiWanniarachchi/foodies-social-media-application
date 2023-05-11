import React from "react";
import {
  Box,
  useMediaQuery
} from "@mui/material";
import { useSelector } from "react-redux";
import { useGetFollowingQuery } from 'state/api-hook'
import Widget from "./Widget";

const Following = () => {
  const isNonMobile = useMediaQuery("(min-width: 1000px");  

  const user = useSelector((state) => state.auth.user.id);
  const { data, refetch } = useGetFollowingQuery({ user });
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
                  type="following"
                />
              )
            )}
          </Box>
        ) : (
          <div justifyContent="center">No Followings</div>
        )}
    </>
  );
};

export default Following;


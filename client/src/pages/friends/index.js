import React from 'react'
import { useSelector } from "react-redux";
import { useGetFollowersQuery, useGetFollowingQuery } from 'state/api-hook';

const Friends = () => {
  const user = useSelector((state) => state.auth.user.id);
  
  

  return (
    <>
  
    </>
  )
}

export default Friends

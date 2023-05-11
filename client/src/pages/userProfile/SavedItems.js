import React from 'react'
import { useSelector } from "react-redux";
import { useGetSavedPostsQuery } from 'state/api-hook'
import SavedItemsWidget from './SavedItemsWidget';

const SavedItems = () => {
  const user = useSelector((state) => state.auth.user.id);
  const { data, refetch } = useGetSavedPostsQuery({ user });

  return (
    <SavedItemsWidget data={data} refetch={refetch} />
  )
}

export default SavedItems
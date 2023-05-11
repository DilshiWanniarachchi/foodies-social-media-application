import React from 'react'
import { Box, useMediaQuery } from '@mui/material'
import PostsWidget from 'pages/posts/PostsWidget';

const SavedItemsWidget = ({ data, refetch }) => {
  
  return (
    <PostsWidget posts={data} refetch={refetch} />
  )
}

export default SavedItemsWidget

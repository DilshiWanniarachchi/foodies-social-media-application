import React from 'react'
import { useSelector } from "react-redux";
import { useGetRecipesByUserQuery } from 'state/api-hook'
import RecipesWidget from 'pages/recipes/RecipesWidget'

const MyRecipes = () => {
  const user = useSelector((state) => state.auth.user.id);
  const { data, refetch } = useGetRecipesByUserQuery({ user });

  return (
    <RecipesWidget data={data} refetch={refetch} />
  )
}

export default MyRecipes
import React from "react";
import RecipeWidget from "./RecipeWidget";
import { Box, useMediaQuery } from "@mui/material";

const RecipesWidget = ({ data, refetch }) => {
  const isNonMobile = useMediaQuery("(min-width: 1000px");
  return (
    <div>
      {data ? (
        <Box
          mt="20px"
          display="grid"
          gridTemplateColumns="repeat(3, minmax(0, 1fr))"
          justifyContent="space-between"
          rowGap="20px"
          columnGap="1.33%"
          sx={{
            "& > div": { gridColumn: isNonMobile ? undefined : "span 3" },
          }}
        >
          {data.map(
            ({
              id,
              user,
              title,
              imagePath,
              description,
              ingredients,
              servings,
              prepTime,
              prepMethod,
            }) => (
              <RecipeWidget
                key={id}
                _id={id}
                user={user}
                title={title}
                servings={servings}
                prepTime={prepTime}
                imagePath={imagePath}
                description={description}
                ingredients={ingredients}
                prepMethod={prepMethod}
                refetch={refetch}
              />
            )
          )}
        </Box>
      ) : (
        <div justifyContent="center">No Recipes available</div>
      )}
    </div>
  );
};

export default RecipesWidget;

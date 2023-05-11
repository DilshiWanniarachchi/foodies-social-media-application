import { useParams } from "react-router-dom";
import React from "react";
import { Box, Grid, useMediaQuery, Typography, Button, List, ListItem, ListItemText } from "@mui/material";
import { useGetRecipeByIdQuery } from "state/api-hook";
import AccessAlarmsIcon from '@mui/icons-material/AccessAlarms';
import RestaurantIcon from '@mui/icons-material/Restaurant';

const ViewRecipe = () => {
  const isNonMobileScreens = useMediaQuery("(min-width:1000px)");
  const id = useParams().recipeId;
  const { data } = useGetRecipeByIdQuery({ id });
  const [dense, setDense] = React.useState(false);

  return (
    <Box
      width="100%"
      padding="2rem 6%"
      display={isNonMobileScreens ? "flex" : "block"}
      gap="0.5rem"
      justifyContent="space-between"
    >
      {data && (
          <Grid container spacing={10}>
            <Grid item xs={6} md={6}>
              <Box sx={{ boxShadow: 3, backgroundColor:"white", p:"3%"}}>
                <img
                  height="100%"
                  width="100%"
                  alt="user"
                  src={`http://localhost:8080/uploads/images/${data.imagePath}`}
                />
                <Button sx={{mt:"1rem", fontSize: "15px"}} variant="contained" startIcon={<RestaurantIcon />}>{data.servings} servings</Button>
                <Button sx={{ml: "1rem", mt:"1rem", fontSize: "15px"}} variant="contained" startIcon={<AccessAlarmsIcon />}>{data.prepTime}</Button>
              </Box>  
            </Grid>
            <Grid item xs={6} md={6}>
              <Box sx={{ p: "2rem", backgroundColor:"white", borderRadius:"10px" }}>
                <Typography
                  paragraph
                  textAlign="center"
                  fontWeight="bold"
                  variant="h2"
                >
                  {data.title}
                </Typography>
                <Typography paragraph textAlign="justify" variant="h4">
                  {data.description}
                </Typography>

                <Typography
                  paragraph
                  textAlign="justify"
                  fontWeight="bold"
                  variant="h4"
                  mt="2rem"
                >
                  Ingredients :
                </Typography>

                <Box width="50%">
                  <List 
                    dense={dense}
                  >
                    {data.ingredients.map((ingredient, index) => {
                      const [name, amount] = ingredient.split("-");
                      return (
                        <ListItem
                          secondaryAction={
                            <Typography variant="h4">{amount}</Typography>
                          }
                        >
                          <ListItemText fontSize="100px" key={index}>
                            <Typography variant="h4">{name}</Typography>
                          </ListItemText>
                        </ListItem>
                      );
                    })}
                  </List>
                </Box>  


              </Box>
            </Grid>
            <Grid item xs={6} md={12}>
              <Box sx={{ p: "2rem", backgroundColor:"white", borderRadius:"10px" }}>
                <Typography
                  paragraph
                  textAlign="justify"
                  fontWeight="bold"
                  variant="h4"
                >
                  Preporation Method :
                </Typography>
                <Typography paragraph textAlign="justify" variant="h4">
                  {data.prepMethod}
                </Typography>
              </Box>  
            </Grid>
          </Grid>
      )}
    </Box>
  );
};

export default ViewRecipe;

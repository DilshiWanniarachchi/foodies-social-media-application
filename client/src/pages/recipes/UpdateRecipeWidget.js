import React, { useState } from "react";
import {
  Grid,
  Box,
  Typography,
  Button,
  TextField,
  Divider,
  useMediaQuery,
  IconButton,
} from "@mui/material";
import ControlPointIcon from "@mui/icons-material/ControlPoint";
import { DeleteOutline } from "@mui/icons-material";
import { useSelector } from "react-redux";
import { Formik } from "formik";
import * as yup from "yup";
import FlexBox from "components/FlexBox";

const recipeSchema = yup.object().shape({
  title: yup.string().required("This field cannot be empty"),
  description: yup.string().required("This field cannot be empty"),
  prepMethod: yup.string().required("This field cannot be empty"),
  servings: yup.string().required("This field cannot be empty"),
  prepTime: yup.string().required("This field cannot be empty"),
});

const initialValuesRecipe = {
  title: "",
  description: "",
  prepMethod: "",
  servings: "",
  prepTime: "",
};

const UpdateRecipeWidget = ({
  refetch,
  setIsForm,
  title,
  description,
  prepMethod,
  servings,
  ingredientsPrev,
  prepTime,
  id,
}) => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [error, setError] = useState("");
  const accessToken = useSelector((state) => state.auth.accessToken);

  //handle ingredients
  const [ingredient, setIngredient] = useState("");
  const [ingredients, setIngredients] = useState(ingredientsPrev);

  const handleAddIngredient = () => {
    if (ingredient.length > 0) {
      ingredients.push(ingredient);
      console.log(ingredients);
      setIngredient("");
    }
  };
  const ingredientDeleteHandler = (ingredient) => {
    setIngredients(ingredients.filter((e) => e !== ingredient));
  };

  //setting up the values
  initialValuesRecipe.description = description;
  initialValuesRecipe.prepMethod = prepMethod;
  initialValuesRecipe.title = title;
  initialValuesRecipe.prepTime = prepTime;
  initialValuesRecipe.servings = servings;
  //handle submit
  const recipeSubmit = async (values, onSubmitProps) => {
    const serverResponse = await fetch(
      `http://localhost:8080/api/recipes/updateRecipe/${id}`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...values, ingredients }),
      }
    );

    const savedRecipe = await serverResponse.json();
    if (serverResponse.ok) {
      if (savedRecipe) {
        onSubmitProps.resetForm();
        refetch();
        setIsForm(false);
      }
    }
  };

  const handleRecipeSubmit = async (values, onSubmitProps) => {
    try {
      await recipeSubmit(values, onSubmitProps);
    } catch (err) {
      setError(err.message);
      alert(error);
    }
  };

  return (
    <>
      <Formik
        onSubmit={handleRecipeSubmit}
        initialValues={initialValuesRecipe}
        validationSchema={recipeSchema}
      >
        {({
          values,
          errors,
          touched,
          handleBlur,
          handleChange,
          handleSubmit,
          setFieldValue,
          resetForm,
        }) => (
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              {/* IMAGE & INGREDIENTS GRID */}
              <Grid item xs={5}>
                <Box
                  display="grid"
                  m="2rem"
                  gridTemplateColumns="repeat(4, minmax(0, 1fr))"
                  sx={{
                    "& > div": {
                      gridColumn: isNonMobile ? undefined : "span 4",
                    },
                  }}
                >
                  <TextField
                    label="Ingredient - amount"
                    value={ingredient}
                    onChange={(e) => setIngredient(e.target.value)}
                    sx={{ gridColumn: "span 2" }}
                  />
                  <IconButton onClick={handleAddIngredient}>
                    <ControlPointIcon fontSize="0px" />
                  </IconButton>
                </Box>
                {ingredients &&
                  ingredients.map((item) => (
                    <p>
                      {item}
                      <IconButton onClick={() => ingredientDeleteHandler(item)}>
                        <DeleteOutline />
                      </IconButton>
                    </p>
                  ))}
              </Grid>

              {/* FORM GRID */}
              <Grid item xs={7}>
                <Box
                  display="grid"
                  gap="30px"
                  gridTemplateColumns="repeat(4, minmax(0, 1fr))"
                  sx={{
                    "& > div": {
                      gridColumn: isNonMobile ? undefined : "span 4",
                    },
                  }}
                >
                  <TextField
                    label="Recipe Title"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    value={values.title}
                    name="title"
                    error={Boolean(touched.title) && Boolean(errors.title)}
                    helperText={touched.title && errors.title}
                    sx={{ gridColumn: "span 4" }}
                  />
                  <TextField
                    label="Desctiption"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    value={values.description}
                    name="description"
                    error={
                      Boolean(touched.description) &&
                      Boolean(errors.description)
                    }
                    helperText={touched.description && errors.description}
                    sx={{ gridColumn: "span 4" }}
                  />
                  <TextField
                    label="Servings"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    value={values.servings}
                    name="servings"
                    error={
                      Boolean(touched.servings) && Boolean(errors.servings)
                    }
                    helperText={touched.servings && errors.servings}
                    sx={{ gridColumn: "span 2" }}
                  />
                  <TextField
                    label="Prep Time"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    value={values.prepTime}
                    name="prepTime"
                    error={
                      Boolean(touched.prepTime) && Boolean(errors.prepTime)
                    }
                    helperText={touched.prepTime && errors.prepTime}
                    sx={{ gridColumn: "span 2" }}
                  />
                  <TextField
                    label="Preporation Method"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    value={values.prepMethod}
                    name="prepMethod"
                    error={
                      Boolean(touched.prepMethod) && Boolean(errors.prepMethod)
                    }
                    helperText={touched.prepMethod && errors.prepMethod}
                    sx={{ gridColumn: "span 4" }}
                  />
                </Box>
                <Divider sx={{ margin: "1.25rem 0" }} />
                <FlexBox textAlign="right" gap="1rem">
                  <Button
                    variant="contained"
                    type="submit"
                    sx={{
                      backgroundColor: "green",
                      m: "2rem 0",
                      p: "0.8rem",
                      width: "8rem",
                    }}
                  >
                    <Typography color="white">Save</Typography>
                  </Button>
                </FlexBox>
              </Grid>
            </Grid>
          </form>
        )}
      </Formik>
    </>
  );
};

export default UpdateRecipeWidget;

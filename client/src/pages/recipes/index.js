import React from "react";
import {
  Box,
  Typography,
  Fab,
  Modal,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useGetRecipesQuery } from "state/api-hook";
import NewRecipeWidget from "./NewRecipeWidget";
import RecipesWidget from "./RecipesWidget";

const Recipes = () => {
  const { data, refetch } = useGetRecipesQuery({
    refetchOnMountOrArgChange: true,
  });
  //modal
  const [open, setOpen] = React.useState(false);

  return (
    <>
      <Box textAlign="right" sx={{ "& > :not(style)": { m: 3 } }}>
        <Fab variant="extended" color="primary" onClick={() => setOpen(!open)}>
          <AddIcon sx={{ mr: 1 }} />
          Add Recipe
        </Fab>
      </Box>
      <Box m="1.5rem 2.5rem">
        <RecipesWidget data={data} refetch={refetch} />
      </Box>
      {open && (
        <Modal
          open={open}
          onClose={() => setOpen(!open)}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box
            sx={{
              position: "absolute",
              top: "50%",
              left: "50%",
              transform: "translate(-50%, -50%)",
              width: "60%",
              bgcolor: "background.paper",
              boxShadow: 24,
              p: 4,
            }}
          >
            <Typography id="modal-modal-title" variant="h6" component="h2">
              ADD NEW RECIPE
            </Typography>
            <Typography id="modal-modal-description" sx={{ mt: 3 }}>
              <NewRecipeWidget refetch={refetch} setOpen={setOpen} />
            </Typography>
          </Box>
        </Modal>
      )}
    </>
  );
};

export default Recipes;

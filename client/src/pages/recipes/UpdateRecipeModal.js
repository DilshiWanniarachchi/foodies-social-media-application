import React from "react";
import {
  Box,
  Modal,
  Typography,
} from "@mui/material";

const UpdateRecipeModal = (prop) => {
  const handleClose = () => {
    prop.setOpen(false);
  };

  return (
      <Modal
        open={prop.open}
        onClose={handleClose}
        aria-labelledby="scroll-dialog-title"
        aria-describedby="scroll-dialog-description"
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
            UPDATE RECIPE
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 3 }}>
            {prop.children}
          </Typography>
        </Box>
      </Modal>
  );
};
export default UpdateRecipeModal;

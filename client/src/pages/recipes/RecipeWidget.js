import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "@mui/material/styles";
import {
  Card,
  CardHeader,
  CardActionArea,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
  IconButton,
  Collapse,
  Menu,
  MenuItem,
  Modal,
  Box,
  Fade,
} from "@mui/material";
import { useSelector } from "react-redux";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { useGetUserByIdQuery } from "state/api-hook";
import ProfileImage from "components/ProfileImage";
import FlexBox from "components/FlexBox";
import DeleteModal from "components/DeleteModal";
import UpdateRecipeModal from "./UpdateRecipeModal";
import UpdateRecipeWidget from "./UpdateRecipeWidget";

const RecipeWidget = ({
  _id,
  user,
  title,
  imagePath,
  description,
  ingredients,
  prepMethod,
  prepTime,
  servings,
  refetch,
}) => {
  const { data } = useGetUserByIdQuery({ user });
  const navigate = useNavigate();

  const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
  })(({ theme, expand }) => ({
    transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest,
    }),
  }));

  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  //Delete option menu
  const loggedInUser = useSelector((state) => state.auth.user.id);
  const isOwner = loggedInUser === user;

  const accessToken = useSelector((state) => state.auth.accessToken);

  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  //delete modal
  const [isDeleteModal, setIsDeleteModal] = useState(false);

  //updateModal
  const [isForm, setIsForm] = useState(false);

  const onDeleteClick = (e) => {
    setIsDeleteModal(!isDeleteModal);
  };

  const handleDelete = async () => {
    const serverResponse = await fetch(
      `http://localhost:8080/api/recipes/deleteRecipe/${_id}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );

    if (!serverResponse.ok) {
      alert("unsuccessful!");
    }

    if (serverResponse.ok) {
      const response = await serverResponse.json();
      console.log(response);
      refetch();
    }
  };

  return (
    <Card
      sx={{
        borderRadius: "0.55rem",
        backgroundColor: "#ffffff",
      }}
    >
      <FlexBox>
        {data && (
          <CardHeader
            avatar={<ProfileImage image={data.picturePath} size="55px" />}
            title={data.fullName}
            subheader={data.username}
          />
        )}
        <IconButton aria-label="settings">
          <MoreVertIcon
            id="fade-button"
            aria-controls={open ? "fade-menu" : undefined}
            aria-haspopup="true"
            aria-expanded={open ? "true" : undefined}
            onClick={handleClick}
          />
          <Menu
            id="fade-menu"
            MenuListProps={{
              "aria-labelledby": "fade-button",
            }}
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            TransitionComponent={Fade}
          >
            {isOwner && (
              <Box>
                <MenuItem onClick={onDeleteClick}>Delete Recipe</MenuItem>
                <MenuItem onClick={() => setIsForm(!isForm)}>
                  Update Recipe
                </MenuItem>
                {isForm && (
                  <UpdateRecipeModal open={isForm} setOpen={setIsForm}>
                    <Typography id="modal-modal-description" sx={{ mt: 3 }}>
                      <UpdateRecipeWidget
                        id={_id}
                        title={title}
                        description={description}
                        prepMethod={prepMethod}
                        servings={servings}
                        refetch={refetch}
                        ingredientsPrev={ingredients}
                        prepTime={prepTime}
                        setIsForm={setIsForm}
                      />
                    </Typography>
                  </UpdateRecipeModal>
                )}
              </Box>
            )}
            {isDeleteModal && (
              <DeleteModal
                open={isDeleteModal}
                setOpen={setIsDeleteModal}
                title={"Delete Recipe"}
                item={"recipe"}
                deleteFunction={handleDelete}
              />
            )}
            <MenuItem>Share</MenuItem>
          </Menu>
        </IconButton>
      </FlexBox>
      <CardActionArea onClick={() => navigate(`/recipes/${_id}`)}>
        <CardMedia
          sx={{
            height: "200px",
          }}
          image={`http://localhost:8080/uploads/images/${imagePath}`}
          title={title}
        />
        <CardContent>
          <Typography
            gutterBottom
            variant="h5"
            component="div"
            fontWeight="bold"
          >
            {title}
          </Typography>
          <Typography variant="h6" color="textSecondary" component="p">
            {description}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          <Typography paragraph fontWeight="bold">
            Preporation Method:
          </Typography>
          <Typography paragraph>{prepMethod}</Typography>
        </CardContent>
      </Collapse>
    </Card>
  );
};

export default RecipeWidget;

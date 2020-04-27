import axios from "axios"
export const loadSuggestions = () => {
  return dispatch => {
    axios.get("/api/search/bookmarks/data").then(response => {
      dispatch({
        type: "MAYBE_UPDATE_SUGGESTIONS",
        suggestions: response.data.searchResult
      })
    })
  }
}

export const updateStarred = object => {
  return dispatch => {
    axios
      .post("api/update/bookmarks", {
        obj: object
      })
      .then(() => {
        dispatch({
          type: "UPDATE_STARRED",
          object
        })
        dispatch(loadSuggestions())
      })
  }
}

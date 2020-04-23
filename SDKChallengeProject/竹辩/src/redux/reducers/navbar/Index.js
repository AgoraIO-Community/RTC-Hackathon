import { searchResult } from "../../../@fake-db/navbar/navbarBookmarkSearch"

const defaultStarred = searchResult.filter(item => {
  return item.starred === true
})

const initialState = {
  suggestions: [],
  isLoading: false,
  value: "",
  starred: defaultStarred,
  noSuggestions: false,
  extraStarred: []
}

const navbarReducer = (state = initialState, action) => {
  switch (action.type) {
    case "MAYBE_UPDATE_SUGGESTIONS":
      return {
        ...state,
        suggestions: action.suggestions,
        isLoading: false
      }
    case "UPDATE_STARRED":
      const starredState = state.starred.map(i => i.id)
      const extraStarredState = state.extraStarred.map(j => j.id)
      // Update main suggestions
      if (state) {
        state.suggestions.find(i => {
          if (i.id === action.object.id) {
            i.starred = !action.object.starred
            return { ...state }
          } else {
            return null
          }
        })
      }
      // checks if it includes and length is 0 < 10 then pushes object
      if (
        !starredState.includes(action.object.id) &&
        state.starred.length < 10
      ) {
        let newState = state.starred.push(action.object)
        return { ...state, newState }
      }

      // checks if it already includes and then removes it
      else if (starredState.includes(action.object.id)) {
        if (state.extraStarred.length === 0) {
          let newState = state.starred.splice(
            starredState.indexOf(action.object.id),
            1
          )
          return { ...state, newState }
        } else if (state.extraStarred.length > 0) {
          let getBookmark = state.extraStarred.splice(0, 1)
          state.starred.splice(starredState.indexOf(action.object.id), 1)
          let updatedState = state.starred.push(getBookmark[0])
          return { ...state, updatedState }
        } else {
          return { ...state }
        }
      }

      // checks if extraState doesn't includes and main starred length is 10+
      else if (
        !extraStarredState.includes(action.object.id) &&
        state.starred.length >= 10
      ) {
        let extraStarred = state.extraStarred.concat(action.object)
        return { ...state, extraStarred }
      } else {
        return { ...state }
      }

    default:
      return state
  }
}

export default navbarReducer

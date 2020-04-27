import { combineReducers } from "redux"
import customizer from "./customizer/"
import auth from "./auth/"
import navbar from "./navbar/Index"

const rootReducer = combineReducers({
  customizer: customizer,
  auth: auth,
  navbar: navbar
})

export default rootReducer

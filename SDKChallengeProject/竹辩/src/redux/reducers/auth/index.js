import { combineReducers } from "redux"
import { login } from "./loginReducer"

const authReducers = combineReducers({
  login
})

export default authReducers

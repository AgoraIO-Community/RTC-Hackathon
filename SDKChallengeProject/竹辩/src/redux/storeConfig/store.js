import { createStore, applyMiddleware, compose } from "redux"
import createDebounce from "redux-debounced"
import thunk from "redux-thunk"
import rootReducer from "../reducers/rootReducer"


import {persistStore, persistReducer} from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';

const middlewares = [thunk, createDebounce()]
const persistConfig = {
    key: 'root',
    storage: storage,
    stateReconciler: autoMergeLevel2 // 查看 'Merge Process' 部分的具体情况
};

const myPersistReducer = persistReducer(persistConfig, rootReducer)
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose
const store = createStore(
  myPersistReducer,
  {},
  composeEnhancers(applyMiddleware(...middlewares))
)
export const persistor = persistStore(store)
export { store }

import React, { Suspense, lazy } from "react"
import ReactDOM from "react-dom"
import { Provider } from "react-redux"
import { Layout } from "./utility/context/Layout"
import * as serviceWorker from "./serviceWorker"
import { store } from "./redux/storeConfig/store"
import Spinner from "./components/@vuexy/spinner/Fallback-spinner"
import "./index.scss"
import "./@fake-db"
import {persistor} from './redux/storeConfig/store'
import {PersistGate} from 'redux-persist/lib/integration/react';

const LazyApp = lazy(() => import("./App"))


// configureDatabase()
ReactDOM.render(
    <Provider store={store}>
      <Suspense fallback={<Spinner />}>
        <Layout>
          <PersistGate loading={null} persistor={persistor}>
            <LazyApp />
            </PersistGate>
        </Layout>
      </Suspense>
    </Provider>,
  document.getElementById("root")
)
// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister()

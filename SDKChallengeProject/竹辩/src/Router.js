import React, { Suspense, lazy } from "react"
import { Router, Switch, Route } from "react-router-dom"
import { history } from "./history"
import { connect } from "react-redux"
import Spinner from "./components/@vuexy/spinner/Loading-spinner"
import { ContextLayout } from "./utility/context/Layout"

// Route-based code splitting
const Home = lazy(() =>
  import("./views/pages/Home")
)

const gameList = lazy(() =>
  import("./views/pages/gameList")
)

const login = lazy(() =>
  import("./views/pages/authentication/login/Login")
)
const register = lazy(() =>
  import("./views/pages/authentication/register/Register")
)
const compList = lazy(() =>
  import("./views/pages/compList")
)
const compShow = lazy(() =>
  import("./views/pages/compShow/compShow")
)
const faceAuth = lazy(() =>
  import("./views/pages/faceAuth/faceAuth")
)
const accountSettings = lazy(() =>
  import("./views/pages/account/accountSettings")
)
const calculate = lazy(() =>
  import("./views/pages/calculate/overview")
)
// Set Layout and Component Using App Route
const RouteConfig = ({
  component: Component,
  fullLayout,
  permission,
  user,
  ...rest
}) => (
  <Route
    {...rest}
    render={props => {
      return (
        <ContextLayout.Consumer>
          {context => {
            let LayoutTag =
              fullLayout === true
                ? context.fullLayout
                : context.state.activeLayout === "horizontal"
                ? context.horizontalLayout
                : context.VerticalLayout
              return (
                <LayoutTag {...props} permission={props.user}>
                  <Suspense fallback={<Spinner />}>
                    <Component {...props} />
                  </Suspense>
                </LayoutTag>
              )
          }}
        </ContextLayout.Consumer>
      )
    }}
  />
)
const mapStateToProps = state => {
  return {
    user: state.auth.login.userRole
  }
}

const AppRoute = connect(mapStateToProps)(RouteConfig)

class AppRouter extends React.Component {
  render() {
    return (
      // Set the directory path if you are deploying in sub-folder
      <Router history={history}>
        <Switch>
          <AppRoute
            exact
            path="/"
            component={Home}
          />
          <AppRoute
            path="/gameList/:id"
            component={gameList}
          />
          <AppRoute
            path="/gameList"
            component={gameList}
          />
          <AppRoute
            path="/login"
            component={login}
            fullLayout
          />
          <AppRoute
            path="/reg"
            component={register}
            fullLayout
          />
          <AppRoute
            path="/compList"
            component={compList}
          />
          <AppRoute
            path="/comp/:id"
            component={compShow}
          />
          <AppRoute
            path="/comp"
            component={compShow}
          />
          <AppRoute
            path="/auth"
            component={faceAuth}
          />
          <AppRoute
            path="/account"
            component={accountSettings}
          />
          <AppRoute
            path="/analyze"
            component={calculate}
          />
        </Switch>
      </Router>
    )
  }
}

export default AppRouter

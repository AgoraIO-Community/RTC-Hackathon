import React from "react"
import "../../../assets/scss/pages/compShow.scss"
import 'react-toastify/dist/ReactToastify.css';
import Breadcrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import WizardBasic from "./WizardBasic"

class faceAuth extends React.Component {
  render() {
    return (
      <div>
      <Breadcrumbs
          breadCrumbTitle="辩手身份验证"
          breadCrumbParent="个人面板"
          breadCrumbActive="辩手身份验证"
      />
      <WizardBasic />
      </div>
  )}
}

export default faceAuth;
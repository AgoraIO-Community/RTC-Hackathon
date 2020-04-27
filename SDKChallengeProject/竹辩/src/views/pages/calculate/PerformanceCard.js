import React from "react"
import { Card, CardBody } from "reactstrap"
import { Award } from "react-feather"

import decorLeft from "../../../assets/img/elements/decore-left.png"
import decorRight from "../../../assets/img/elements/decore-right.png"

class SalesCard extends React.Component {
  render() {
    return (
      <Card className="bg-analytics text-white sales-card">
        <CardBody className="text-center">
          <img src={decorLeft} alt="card-img-left" className="img-left" />
          <img src={decorRight} alt="card-img-right" className="img-right" />
          <div className="avatar avatar-xl bg-primary shadow avatar-dashboard mt-0">
            <div className="avatar-content">
              <Award className="text-white" size={28} />
            </div>
          </div>
          <div className="award-info text-center">
            <h1 className="mb-2 text-white">欢迎回来，{localStorage.getItem("realname")}。</h1>
            <p className="m-auto mb-0 w-75">
              恭喜你！经过对专业表现以及仪表的加权综合评分，你在上场比赛中的综合评分超过了<strong>80.0%</strong>的辩手，继续努力吧。
            </p>
          </div>
        </CardBody>
      </Card>
    )
  }
}
export default SalesCard

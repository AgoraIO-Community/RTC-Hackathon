import React from "react"
import StatisticsCard from "../../../components/@vuexy/statisticsCard/StatisticsCard"
import { Package, User } from "react-feather"
import appGlobal from "./config.js";
import getData from "./getData.js";
const ordersReceived = {
  chart: {
    id: "revenue",
    toolbar: {
      show: false
    },
    sparkline: {
      enabled: true
    }
  },
  grid: {
    show: false
  },
  colors: ["#FF9F43"],
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: "smooth",
    width: 2.5
  },
  fill: {
    type: "gradient",
    gradient: {
      shadeIntensity: 0.9,
      opacityFrom: 0.7,
      opacityTo: 0.5,
      stops: [0, 80, 100]
    }
  },

  xaxis: {
    labels: {
      show: false
    },
    axisBorder: {
      show: false
    }
  },
  yaxis: {
    labels: {
      show: false
    }
  },
  tooltip: {
    x: { show: false }
  }
}


class OrdersReceived extends React.Component {
  state = {
    refAvg: "",
    refGrade: []
  }
  async componentWillMount() {
    await getData()
    console.log(appGlobal.RefGrade.slice(1, appGlobal.RefGrade.length))
    this.setState({
      refAvg: appGlobal.RefGrade[0],
      refGrade: [
        {
          name: "环节得分",
          data: appGlobal.RefGrade.slice(1, appGlobal.RefGrade.length)
        }
      ]
    })
  }
  render() {
    return (
      <StatisticsCard
        icon={<User className="warning" size={22} />}
        stat={this.state.refAvg}
        statTitle="裁判评分得分曲线"
        options={ordersReceived}
        series={this.state.refGrade}
        type="area"
      />
    )
  }
}
export default OrdersReceived

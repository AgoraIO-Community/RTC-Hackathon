import React from "react"
import StatisticsCard from "../../../components/@vuexy/statisticsCard/StatisticsCard"
import { Users } from "react-feather"
import appGlobal from "./config.js";
import getData from "./getData.js";

const subscribersGained = {
  chart: {
    id: "subscribers",
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
  colors: ["#7367F0"],
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




class SubscriberGained extends React.Component {
  state = {
    aiAvg: "",
    aiGrade: []
  }
  async componentWillMount() {
    await getData()
    console.log(appGlobal.AIGrade.slice(1, appGlobal.AIGrade.length))
    this.setState({
      aiAvg: appGlobal.AIGrade[0],
      aiGrade: [
        {
          name: "环节得分",
          data: appGlobal.AIGrade.slice(1, appGlobal.AIGrade.length)
        }
      ]
    })
  }
  render() {
    return (
      <StatisticsCard
        icon={<Users className="primary" size={22} />}
        stat={this.state.aiAvg}
        statTitle="AI评分得分曲线"
        options={subscribersGained}
        series={this.state.aiGrade}
        type="area"
      />
    )
  }
}
export default SubscriberGained

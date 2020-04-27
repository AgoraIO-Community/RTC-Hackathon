import React from "react"
import { Card, CardHeader, CardTitle, CardBody } from "reactstrap"
import Chart from "react-apexcharts"
import { HelpCircle } from "react-feather"
import appGlobal from "./config.js";
import getData from "./getData.js";

class GoalOverview extends React.Component {
  state = {
    options: {
      chart: {
        sparkline: {
          enabled: true
        },
        dropShadow: {
          enabled: true,
          blur: 3,
          left: 1,
          top: 1,
          opacity: 0.1
        }
      },
      colors: [this.props.success],
      plotOptions: {
        radialBar: {
          size: 110,
          startAngle: -140,
          endAngle: 150,
          hollow: {
            size: "77%"
          },
          track: {
            background: this.props.strokeColor,
            strokeWidth: "50%"
          },
          dataLabels: {
            name: {
              show: false
            },
            value: {
              offsetY: 18,
              color: this.props.strokeColor,
              fontSize: "4rem"
            }
          }
        }
      },
      fill: {
        type: "gradient",
        gradient: {
          shade: "dark",
          type: "horizontal",
          shadeIntensity: 0.5,
          gradientToColors: ["#00b5b5"],
          inverseColors: true,
          opacityFrom: 1,
          opacityTo: 1,
          stops: [0, 100]
        }
      },
      stroke: {
        lineCap: "round"
      }
    },
    finalGrade: [0],
    profGrade: "",
    AIGrade: ""
  }
  async componentWillMount() {
    await getData()
    this.setState({
      finalGrade: [parseInt((appGlobal.RefGrade[0]*0.7 + appGlobal.AIGrade[0]*0.3))],
      profGrade: appGlobal.RefGrade[0],
      AIGrade: appGlobal.AIGrade[0],
    })
  }
  render() {
    return (
      <Card>
        <CardHeader>
          <CardTitle>综合加权得分</CardTitle>
          <HelpCircle size={20} className="cursor-pointer text-muted" />
        </CardHeader>
        <CardBody>
          <Chart
            options={this.state.options}
            series={this.state.finalGrade}
            type="radialBar"
            height={260}
          />
        </CardBody>
        <div className="d-flex mt-2">
          <div className="completed border-top border-right text-center w-50 py-1">
            <p className="mb-50">专业度</p>
            <p className="font-large-1 text-bold-600 mb-50">{this.state.profGrade}</p>
          </div>
          <div className="in-progress border-top border-right text-center w-50 py-1">
            <p className="mb-50">临场表现(AI)</p>
            <p className="font-large-1 text-bold-600 mb-50">{this.state.AIGrade}</p>
          </div>
        </div>
      </Card>
    )
  }
}
export default GoalOverview

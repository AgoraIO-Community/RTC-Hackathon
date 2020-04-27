import React from "react";
import yesapi from "../../webServices/yes3";
import { checkIfLogin } from "../../webServices/auth";
import { Card, CardBody, CardHeader, CardTitle, Input, Button } from "reactstrap";
import DataTable from "react-data-table-component";
import { Search } from "react-feather";
import Breadcrumbs from "../../components/@vuexy/breadCrumbs/BreadCrumb";
import {history} from "../../history"
const CustomHeader = props => {
  return (
    <div className="d-flex flex-wrap justify-content-between">
      <div className="position-relative has-icon-left mb-1">
        <Input value={props.value} onChange={e => props.handleFilter(e)} />{" "}
        <div className="form-control-position">
          <Search size="15" />
        </div>{" "}
      </div>{" "}
    </div>
  );
};
function openShowPage(gid) {
  if(gid === '0') {history.push("/compList")}
  else history.push("/comp/"+gid)
  // console.log(arguments)
}

class CompList extends React.Component {



  state = {
    columns: [
      {
        name: "辩题",
        selector: "com_name",
        sortable: true,
        minWidth: "200px",
        cell: row => (
          <div className="d-flex flex-xl-row flex-column align-items-xl-center align-items-start py-xl-0 py-1">
            <div className="user-info text-truncate ml-xl-50 ml-0">
              <span
                title={row.game_title}
                className="d-block text-bold-500 text-truncate mb-0"
              >
                {row.game_title}{" "}
              </span>{" "}
            </div>{" "}
          </div>
        )
      },
      {
        name: "开始时间",
        selector: "orga_name",
        cell: row => (
          <p className="text-bold-500 text-truncate mb-0"> {row.time_s} </p>
        )
      },
      {
        name: "跳转",
        selector: "jump",
        cell: row => (
          <Button color="success" onClick={openShowPage.bind(this,row.id)}>{row.id === '0'?"返回比赛列表":"打开页面"}</Button>
        )
      }
    ],
    data: [
      {
        game_title: "参数为空，请返回【比赛列表】页面重新选择",
        time_s: "NaN",
        image: "https://jdc.jd.com/img/200",
        id: '0'
      }
    ],
    filteredData: [],
    value: ""
  };

  async componentDidMount () {
    document.title = "场次列表 | 云辩论"
    await checkIfLogin();
    let dataSet = null;
    let _this = this;
    console.log(this.props)
    let id = this.props.match.params.id;
    if(id !== undefined) {
      let {data} = await yesapi.table.readPageSelect("game",[["com_id","=",id]],"and",1,50,["game_title","time_s","id"])
      dataSet = data.list;
      console.log(data);
      _this.setState({
        data: dataSet
      });
    }
  }

  handleFilter = e => {
    let value = e.target.value;
    let data = this.state.data;
    let filteredData = this.state.filteredData;
    this.setState({
      value
    });

    if (value.length) {
      filteredData = data.filter(item => {
        //{com_name: "测试比赛1", orga_name: "小于和小王", com_desc: "这是一个测试这是测试", com_rule: "2312312", logo: "https://puluter.cn/img/avatar.jpg", …}
        let startsWithCondition =
          item.com_name.toLowerCase().startsWith(value.toLowerCase()) ||
          item.orga_name.toLowerCase().startsWith(value.toLowerCase());
        let includesCondition =
          item.com_name.toLowerCase().includes(value.toLowerCase()) ||
          item.orga_name.toLowerCase().includes(value.toLowerCase());

        if (startsWithCondition) {
          return startsWithCondition;
        } else if (!startsWithCondition && includesCondition) {
          return includesCondition;
        } else return null;
      });
      this.setState({
        filteredData
      });
    }
  };

  render () {
    let { data, columns, value, filteredData } = this.state;
    return (
      <div>
        <Breadcrumbs
          breadCrumbTitle="场次列表"
          breadCrumbParent="场次列表"
          breadCrumbActive="查看"
        />
        <Card>
          <CardHeader>
            <CardTitle> 场次列表 </CardTitle>{" "}
          </CardHeader>{" "}
          <CardBody className="rdt_Wrapper">
            <DataTable
              className="dataTable-custom"
              data={value.length ? filteredData : data}
              columns={columns}
              noHeader
              pagination
              subHeader
              subHeaderComponent={
                <CustomHeader value={value} handleFilter={this.handleFilter} />
              }
            />{" "}
          </CardBody>{" "}
        </Card>{" "}
      </div>
    );
  }
}

export default CompList;

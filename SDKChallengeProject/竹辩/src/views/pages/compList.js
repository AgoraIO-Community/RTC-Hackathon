import React from "react";
import { checkIfLogin } from "../../webServices/auth";
import { Card, CardBody, CardHeader, CardTitle, Input, Button } from "reactstrap";
import DataTable from "react-data-table-component";
import { Search } from "react-feather";
import Breadcrumbs from "../../components/@vuexy/breadCrumbs/BreadCrumb";
import {history} from "../../history"
import yesapi from "../../webServices/yes3";
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
  history.push("gameList/"+gid)
  // console.log(arguments)
}

class CompList extends React.Component {



  state = {
    columns: [
      {
        name: "赛事名称",
        selector: "com_name",
        sortable: true,
        minWidth: "200px",
        cell: row => (
          <div className="d-flex flex-xl-row flex-column align-items-xl-center align-items-start py-xl-0 py-1">
            <div className="user-img ml-xl-0 ml-2">
              <img
                className="img-fluid rounded-circle"
                height="36"
                width="36"
                src={row.image}
                alt={row.com_name}
              />{" "}
            </div>{" "}
            <div className="user-info text-truncate ml-xl-50 ml-0">
              <span
                title={row.com_name}
                className="d-block text-bold-500 text-truncate mb-0"
              >
                {row.com_name}{" "}
              </span>{" "}
            </div>{" "}
          </div>
        )
      },
      {
        name: "主办方",
        selector: "orga_name",
        cell: row => (
          <p className="text-bold-500 text-truncate mb-0"> {row.orga_name} </p>
        )
      },
      {
        name: "一句话描述",
        selector: "com_desc",
        cell: row => (
          <p className="text-bold-500 text-truncate mb-0"> {row.com_desc} </p>
        )
      },
      {
        name: "跳转",
        selector: "jump",
        cell: row => (
          <Button color="success" onClick={openShowPage.bind(this,row.id)}>打开对应场次列表</Button>
        )
      }
    ],
    data: [
      {
        com_name: "Loading",
        orga_name: "Loading",
        com_desc: "Loading",
        image: "https://jdc.jd.com/img/200",
        id: '0'
      }
    ],
    filteredData: [],
    value: ""
  };

  async componentDidMount () {
    document.title = "比赛列表 | 云辩论"
    await checkIfLogin();
    let dataSet = null;
    let data1 = [];
    let {data} = await yesapi.table.readPageSelect("competition",[["id","<>","-1"]],"and",1,50,"id,com_name,orga_name,com_desc,logo")
    console.log(data)
    dataSet = data.list;
    dataSet.map((item, index) => {
      item.image = item.logo;
      data1.push(item);
      return 0;
    });
    this.setState({
      data: data1
    });
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
          breadCrumbTitle="赛事列表"
          breadCrumbParent="赛事列表"
          breadCrumbActive="查看"
        />
        <Card>
          <CardHeader>
            <CardTitle> 赛事列表 </CardTitle>{" "}
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

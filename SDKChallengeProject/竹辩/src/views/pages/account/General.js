import React from "react"
import {
  Alert,
  Button,
  Media,
  Form,
  FormGroup,
  Input,
  Label,
  Row,
  Col
} from "reactstrap"
import chroma from "chroma-js"
import Select from "react-select"
import { toast } from 'react-toastify';
toast.configure({
  autoClose: 5000,
  draggable: false,
});

const notifySuccess = (e) => {
  toast.success(e, {
    position: toast.POSITION.TOP_LEFT
  });
}



const position = [
  { value: "1", label: "一辩", color: "#7367f0" },
  { value: "2", label: "二辩", color: "#7367f0" },
  { value: "3", label: "三辩", color: "#7367f0" },
  { value: "4", label: "四辩", color: "#7367f0" }
]
const colourStyles = {
  control: styles => ({ ...styles, backgroundColor: "white" }),
  option: (styles, { data, isDisabled, isFocused, isSelected }) => {
    const color = data.color ? chroma(data.color) : "#7367f0"
    return {
      ...styles,
      backgroundColor: isDisabled
        ? null
        : isSelected
        ? data.color
        : isFocused
        ? color.alpha(0.1).css()
        : null,
      color: isDisabled
        ? "#ccc"
        : isSelected
        ? chroma.contrast(color, "white") > 2
          ? "white"
          : "black"
        : data.color,
      cursor: isDisabled ? "not-allowed" : "default",

      ":active": {
        ...styles[":active"],
        backgroundColor: !isDisabled && (isSelected ? data.color : "#7367f0")
      }
    }
  },
  multiValue: (styles, { data }) => {
    const color = data.color ? chroma(data.color) : "#7367f0"
    return {
      ...styles,
      backgroundColor: color.alpha(0.1).css()
    }
  },
  multiValueLabel: (styles, { data }) => ({
    ...styles,
    color: data.color ? data.color : "#7367f0"
  }),
  multiValueRemove: (styles, { data }) => ({
    ...styles,
    color: data.color,
    ":hover": {
      backgroundColor: data.color ? data.color : "#7367f0",
      color: "white"
    }
  })
}
class General extends React.Component {
  state = {
    visible: true
  }

  dismissAlert = () => {
    this.setState({
      visible: false
    })
  }

  render() {
    return (
      <React.Fragment>
        <Media>
          <Media className="mr-1" left href="#">
            <Media
              className="rounded-circle"
              object
              src={localStorage.getItem("avatar")}
              alt="User"
              height="64"
              width="64"
            />
          </Media>
          <Media className="mt-25" body>
            <div className="d-flex flex-sm-row flex-column justify-content-start px-0">
              <Button.Ripple
                tag="label"
                className="mr-50 cursor-pointer"
                color="primary"
                outline
              >
                上传照片
                <Input type="file" name="file" id="uploadImg" hidden />
              </Button.Ripple>
              <Button.Ripple color="flat-danger">移除</Button.Ripple>
            </div>
            <p className="text-muted mt-50">
              <small>请上传 JPG 或 PNG 格式的照片。 小于 1mB 以内。</small>
            </p>
          </Media>
        </Media>
        <Form className="mt-2" onSubmit={e => e.preventDefault()}>
          <Row>
            <Col sm="12">
              <FormGroup>
                <Label for="userName">用户昵称</Label>
                <Input id="userName" defaultValue={localStorage.getItem("username")} />
              </FormGroup>
            </Col>
            <Col sm="12">
              <FormGroup>
                <Label for="name">真实姓名</Label>
                <Input id="name" defaultValue={localStorage.getItem("realname")} />
              </FormGroup>
            </Col>
            <Col sm="12">
              <FormGroup>
                <Label for="email">角色</Label>
                <Input id="email" defaultValue="工作人员" disabled="disabled" />
              </FormGroup>
            </Col>
            <Col sm="12">
              <FormGroup>
                <Label for="company">团队</Label>
                <Input
                  id="company"
                  disabled="disabled"
                  defaultValue="你尚未加入任何队伍"
                />
              </FormGroup>
            </Col>
            <Col sm="12">
              <Alert
                className="mb-2"
                color="warning"
                isOpen={this.state.visible}
                toggle={this.dismissAlert}
              >
                <p className="mb-0">
                  你尚未加入任何队伍，到队伍列表看看吧。
                  <span className="text-primary"> 队伍列表</span>
                </p>
              </Alert>
            </Col>
            <Col sm="12">
              <FormGroup>
                <Label for="position">擅长辩位</Label>
                <Select
                  isMulti
                  defaultValue={[position[0], position[1]]}
                  isClearable={true}
                  styles={colourStyles}
                  options={position}
                  className="React"
                  classNamePrefix="select"
                  id="position"
                />
              </FormGroup>
            </Col>

            <Col className="d-flex justify-content-start flex-wrap" sm="12">
              <Button.Ripple className="mr-50" type="submit" color="primary" onClick={() => notifySuccess("保存成功，请退出后重新登录。")}>
                保存更改
              </Button.Ripple>
              <Button.Ripple type="submit" color="danger">
                取消
              </Button.Ripple>
            </Col>
          </Row>
        </Form>
      </React.Fragment>
    )
  }
}
export default General

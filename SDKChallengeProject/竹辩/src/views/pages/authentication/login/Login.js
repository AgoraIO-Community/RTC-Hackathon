import React from "react";
import {
  Button,
  Card,
  CardBody,
  Row,
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Alert,
} from "reactstrap";
import { User, Lock, Check } from "react-feather";
import { history } from "../../../../history";
import Checkbox from "../../../../components/@vuexy/checkbox/CheckboxesVuexy";
import loginImg from "../../../../assets/img/pages/login.png";
import "../../../../assets/scss/pages/authentication.scss";
import yesapi from "../../../../webServices/yes3";
import { ROLE_NAME } from "../../../../webServices/const";
import { connect } from "react-redux"
import {changeRole} from "../../../../redux/actions/auth/loginActions"

class Login extends React.Component {
  state = {
    activeTab: "1",
    email: "",
    password: "",
    b显示登录错误: false,
    b显示登录成功: false,
  };
  toggle = (tab) => {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab,
      });
    }
  };

  componentDidMount() {
    document.title = "登录 | 云辩论"
  }

  async submitLogin() {
    let _this = this;
    console.log(this.state);
    let { email, password } = this.state;
    let { data } = await yesapi.user.login(email, password);
    let { uuid, token, role } = data;
    console.log(data);
    if (role === ROLE_NAME) {
      localStorage.setItem("uuid", uuid);
      localStorage.setItem("token", token);
      let ret2 = await yesapi.user.getSelfInfo();
      let { username, ext_info } = ret2.data.info;
      localStorage.setItem("avatar", ext_info.yesapi_avatar);
      localStorage.setItem("realname", ext_info.yesapi_real_name);
      localStorage.setItem("username", username);
    } else if (role === "user") {
      await yesapi.user.changeOtherRole(uuid, ROLE_NAME);
      console.log("初始化role成功");
      localStorage.setItem("uuid", uuid);
      localStorage.setItem("token", token);
    } else {
      this.setState({ b显示登录错误: true });
      return ;
    }
    _this.setState({ b显示登录成功: true });
    let selfInfoRet = await yesapi.user.getSelfInfo();
    let {ext_info} = selfInfoRet.data.info;
    let cd_role = "debator";
    if("cd_role" in ext_info === false) await yesapi.user.updateExtInfo({"cd_role":"debator"});
    else cd_role = ext_info.cd_role;
    this.props.changeRole(cd_role)
    setTimeout(() => {
      history.push("/");
    }, 1000);
  }

  render() {
    return (
      <Row className="m-0 justify-content-center">
        <Col
          sm="8"
          xl="7"
          lg="10"
          md="8"
          className="d-flex justify-content-center"
        >
          <Card className="bg-authentication login-card rounded-0 mb-0 w-100">
            <Row className="m-0">
              <Col
                lg="6"
                className="d-lg-block d-none text-center align-self-center px-1 py-0"
              >
                <img src={loginImg} alt="loginImg" />
              </Col>
              <Col lg="6" md="12" className="p-0">
                <Card className="rounded-0 mb-0 px-2">
                  <CardBody>
                    <h4>登录账户</h4>
                    <p>欢迎回来，请在这里登录。</p>
                    <div>
                      <Alert
                        color="danger"
                        isOpen={this.state.b显示登录错误}
                        toggle={() => {
                          this.setState({ b显示登录错误: false });
                        }}
                      >
                        用户名或密码错误，请重试
                      </Alert>
                      <Alert
                        color="success"
                        isOpen={this.state.b显示登录成功}
                        toggle={() => {
                          this.setState({ b显示登录成功: false });
                        }}
                      >
                        成功！页面即将跳转……
                      </Alert>
                    </div>
                    <div style={{ marginTop: "20px" }}>
                      <Form onSubmit={(e) => e.preventDefault()}>
                        <FormGroup className="form-label-group position-relative has-icon-left">
                          <Input
                            type="text"
                            placeholder="用户名"
                            value={this.state.email}
                            onChange={(e) =>
                              this.setState({ email: e.target.value })
                            }
                          />
                          <div className="form-control-position">
                            <User size={15} />
                          </div>
                          <Label>用户名</Label>
                        </FormGroup>
                        <FormGroup className="form-label-group position-relative has-icon-left">
                          <Input
                            type="password"
                            placeholder="密码"
                            value={this.state.password}
                            onChange={(e) =>
                              this.setState({ password: e.target.value })
                            }
                          />
                          <div className="form-control-position">
                            <Lock size={15} />
                          </div>
                          <Label>密码</Label>
                        </FormGroup>
                        <FormGroup className="d-flex justify-content-between align-items-center">
                          <Checkbox
                            color="primary"
                            icon={<Check className="vx-icon" size={16} />}
                            label="记住我"
                          />
                          <div className="float-right">忘记密码?</div>
                        </FormGroup>
                        <div className="d-flex justify-content-between">
                          <Button.Ripple
                            color="primary"
                            outline
                            onClick={() => {
                              history.push("/reg");
                            }}
                          >
                            没有账户？
                          </Button.Ripple>
                          <Button.Ripple
                            color="primary"
                            type="submit"
                            onClick={this.submitLogin.bind(this)}
                          >
                            登录
                          </Button.Ripple>
                        </div>
                      </Form>
                    </div>
                  </CardBody>
                </Card>
              </Col>
            </Row>
          </Card>
        </Col>
      </Row>
    );
  }
}
// export Login;
const mapStateToProps = state => {
  return {
    role: state.auth.login.userRole
  }
}
export default connect(mapStateToProps, { changeRole })(Login)
import React from "react"
import { Form, FormGroup, Input, Label, Button } from "reactstrap"
import Checkbox from "../../../../components/@vuexy/checkbox/CheckboxesVuexy"
import { User, UserCheck,Mail, Lock, Codepen, Check } from "react-feather"
import { history } from "../../../../history"
import yesapi from "../../../../webServices/yesAPI_v2";

class RegisterJWT extends React.Component {
  state = {
    email: "",
    password: "",
    name: "",
    confirmPass: "",
    realname: "",
    inviteCode: ""
  }

  handleRegister = e => {
    e.preventDefault()
    let {email,password,name,realname,inviteCode} = this.state;
    let ext = {
      "yesapi_email":email,
      "yesapi_real_name":realname,
      "inviteCode":inviteCode
    };
    yesapi.user.register(name,password,ext,{
      success: ret => {
        console.log(ret)
        if(ret.data.data.err_code === 0) {
          alert("注册成功！")
          history.push("/login")
        }
      },
      fail: err => {
        console.log(err);
      }
    })
  }

  checkIfAvailable(username) {

  }

  render() {
    return (
      <Form action="/" onSubmit={this.handleRegister}>
        <FormGroup className="form-label-group">
          <Input
            type="text"
            placeholder="用户名"
            required
            value={this.state.name}
            onChange={e => this.setState({ name: e.target.value })}
          />
          <div className="form-control-position">
            <User size={15} />
          </div>
          <Label>用户名</Label>
        </FormGroup>
        <FormGroup className="form-label-group">
          <Input
            type="text"
            placeholder="真实姓名"
            required
            value={this.state.realname}
            onChange={e => this.setState({ realname: e.target.value })}
          />
          <div className="form-control-position">
            <UserCheck size={15} />
          </div>
          <Label>真实姓名</Label>
        </FormGroup>
        <FormGroup className="form-label-group">
          <Input
            type="email"
            placeholder="邮箱"
            required
            value={this.state.email}
            onChange={e => this.setState({ email: e.target.value })}
          />
          <div className="form-control-position">
            <Mail size={15} />
          </div>
          <Label>邮箱</Label>
        </FormGroup>
        <FormGroup className="form-label-group">
          <Input
            type="text"
            placeholder="邀请码"
            required
            value={this.state.inviteCode}
            onChange={e => this.setState({ inviteCode: e.target.value })}
          />
          <div className="form-control-position">
            <Codepen size={15} />
          </div>
          <Label>邀请码</Label>
        </FormGroup>
        <FormGroup className="form-label-group">
          <Input
            type="password"
            placeholder="密码"
            required
            value={this.state.password}
            onChange={e => this.setState({ password: e.target.value })}
          />
          <div className="form-control-position">
            <Lock size={15} />
          </div>
          <Label>密码</Label>
        </FormGroup>
        <FormGroup className="form-label-group">
          <Input
            type="password"
            placeholder="请重复密码"
            required
            value={this.state.confirmPass}
            onChange={e => this.setState({ confirmPass: e.target.value })}
          />
          <div className="form-control-position">
            <Lock size={15} />
          </div>
          <Label>请重复密码</Label>
        </FormGroup>
        <FormGroup>
          <Checkbox
            color="primary"
            icon={<Check className="vx-icon" size={16} />}
            label=" 我已阅读并同意《用户协议》（并不存在）"
            defaultChecked={true}
          />
        </FormGroup>
        <div className="d-flex justify-content-between">
          <Button.Ripple
            color="primary"
            outline
            onClick={() => {
              history.push("/login")
            }}
          >
            已有账户？
          </Button.Ripple>
          <Button.Ripple color="primary" type="submit">
            提交注册
          </Button.Ripple>
        </div>
      </Form>
    )
  }
}
export default RegisterJWT
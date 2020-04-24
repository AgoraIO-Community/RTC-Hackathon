import React from "react"
import { Button, FormGroup, Row, Col } from "reactstrap"
import { Formik, Field, Form } from "formik"
import * as Yup from "yup"
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


const formSchema = Yup.object().shape({
  oldpass: Yup.string().required("请正确填写"),
  newpass: Yup.string().required("请正确填写"),
  confirmpass: Yup.string()
    .oneOf([Yup.ref("新密码"), null], "密码必须相同")
    .required("请正确填写")
})
class ChangePassword extends React.Component {
  render() {
    return (
      <React.Fragment>
        <Row className="pt-1">
          <Col sm="12">
            <Formik
              initialValues={{
                oldpass: "",
                newpass: "",
                confirmpass: ""
              }}
              validationSchema={formSchema}
            >
              {({ errors, touched }) => (
                <Form>
                  <FormGroup>
                    <Field
                      name="oldpass"
                      id="oldpass"
                      className={`form-control ${errors.oldpass &&
                        touched.oldpass &&
                        "is-invalid"}`}
                      placeholder="原密码"
                    />
                    {errors.oldpass && touched.oldpass ? (
                      <div className="text-danger">{errors.oldpass}</div>
                    ) : null}
                  </FormGroup>
                  <FormGroup>
                    <Field
                      name="newpass"
                      placeholder="新密码"
                      id="newpass"
                      className={`form-control ${errors.newpass &&
                        touched.newpass &&
                        "is-invalid"}`}
                    />
                    {errors.newpass && touched.newpass ? (
                      <div className="text-danger">{errors.newpass}</div>
                    ) : null}
                  </FormGroup>
                  <FormGroup>
                    <Field
                      name="confirmpass"
                      id="confirmpass"
                      className={`form-control ${errors.confirmpass &&
                        touched.confirmpass &&
                        "is-invalid"}`}
                      placeholder="确认新密码"
                    />
                    {errors.confirmpass && touched.confirmpass ? (
                      <div className="text-danger">{errors.confirmpass}</div>
                    ) : null}
                  </FormGroup>
                  <div className="d-flex justify-content-start flex-wrap">
                    <Button.Ripple
                      className="mr-1 mb-1"
                      color="primary"
                      type="submit"
                      onClick={() => notifySuccess("保存成功，请退出后重新登录。")}
                    >
                      保存更改
                    </Button.Ripple>
                    <Button.Ripple
                      className="mb-1"
                      color="danger"
                      type="reset"
                      outline
                    >
                      取消
                    </Button.Ripple>
                  </div>
                </Form>
              )}
            </Formik>
          </Col>
        </Row>
      </React.Fragment>
    )
  }
}
export default ChangePassword

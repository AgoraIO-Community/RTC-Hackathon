import toast from './toast'
const Toast = {}
Toast.install = vue => {
  const ToastCon = vue.extend(toast)
  const ins = new ToastCon()
  ins.$mount(document.createElement('div'))
  document.body.appendChild(ins.$el)
  vue.prototype.$toast = (msg, duration = 3000) => {
    ins.message = msg
    ins.visible = true
    setTimeout(() => {
      ins.visible = false
    }, duration)
  }
}
export default Toast

import {history} from "../history"
import yesapi from "./yes3"
export async function checkIfLogin(){
    let uuid = localStorage.getItem("uuid")
    let token = localStorage.getItem("token")
    if (uuid === null||token === null) {
        history.push("/login")
        return 0
    }
    let i上一次查询登录的时间戳 = localStorage.getItem("lastCheckTimestamp")
    if(i上一次查询登录的时间戳 === null) {
        i上一次查询登录的时间戳 = new Date().getTime()
        localStorage.setItem("lastCheckTimestamp",i上一次查询登录的时间戳)
    }
    if(new Date().getTime() - i上一次查询登录的时间戳 > 1800000) {
        let ret = await yesapi.user.checkIfLogin()
        if(ret.ret !== 200 || ret.data.err_code !== 0) history.push("/login")
        localStorage.setItem("lastCheckTimestamp",new Date().getTime())
    }
}

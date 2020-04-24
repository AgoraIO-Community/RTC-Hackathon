let timeout = null
const debounce = (fn, wait) => { // 防抖，
  if (timeout !== null) clearTimeout(timeout)
  timeout = setTimeout(() => {
    timeout = null
    fn && fn()
  }, wait)
}

let getObjectURL = (file) => { //  获取本地图片url
  var url = null
  if (window.createObjectURL !== undefined) {
    url = window.createObjectURL(file)
  } else if (window.URL !== undefined) {
    url = window.URL.createObjectURL(file)
  } else if (window.webkitURL !== undefined) {
    url = window.webkitURL.createObjectURL(file)
  }
  return url
}
let validateFile = (file) => { // 图片格式
  var validExts = new Array('.jpg', '.jpeg', '.png')
  var fileExt = file.name
  fileExt = fileExt.substring(fileExt.lastIndexOf('.'))
  if (validExts.indexOf(fileExt) < 0) {
    console.log('无效的格式，只支持' +
           validExts.toString() + '这些类型')
    return false
  } else return true
}
let validateVideo = (file) => { // 上传的视频只支持mp4格式
  var validExts = new Array('.mp4')
  var fileExt = file.name
  fileExt = fileExt.substring(fileExt.lastIndexOf('.'))
  if (validExts.indexOf(fileExt) < 0) {
    console.log('无效的格式，暂时只支持' +
           validExts.toString())
    return false
  } else return true
}
const util = {
  debounce,
  getObjectURL,
  validateFile,
  validateVideo
}

export default util

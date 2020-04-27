
function isYestday (theDate, nowDate) {
  var today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate()).getTime() // 今天凌晨
  var yestday = new Date(today - 24 * 3600 * 1000).getTime()
  return theDate.getTime() < today && yestday <= theDate.getTime()
}

function isTheDayBeforeYestday (theDate, nowDate) {
  var today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate()).getTime() // 今天凌晨
  var yestday = new Date(today - 24 * 3600 * 1000).getTime()
  var theDayBeforeYestday = new Date(today - 48 * 3600 * 1000).getTime()
  return theDate.getTime() < yestday && theDayBeforeYestday <= theDate.getTime()
}

export function calcGMTTimeDiff (GMTString, GMTString2) {
  var minutes = Math.round(Math.abs((GMTString2 - GMTString) / 1000 / 60))
  return minutes
};

function descHourMin (hour, min) {
  let res = hour + ':' + ('0' + min).slice(-2)
  if (hour < 6) { return '凌晨 ' + res }
  if (hour < 11) { return '早上 ' + res }
  if (hour < 13) { return '中午 ' + res }
  if (hour < 18) { return '下午 ' + res }
  return '晚上 ' + res
}

export function getGMTTimeDiff (GMTString, type) {
  var dateTime = new Date(GMTString)
  var year = dateTime.getFullYear()
  var month = dateTime.getMonth() + 1
  var day = dateTime.getDate()
  var hour = dateTime.getHours()
  var minute = dateTime.getMinutes()
  var now = new Date()
  var nowNew = Date.parse(now.toUTCString())
  var yearNew = now.getFullYear()
  var dayNew = now.getDate()
  var minutes = Math.round((nowNew - GMTString) / 1000 / 60)
  if (minutes < 1) { return '刚刚' }
  if (minutes < 60) { return minutes + '分钟前' }
  var hours = Math.round(minutes / 60)
  var hourMin = descHourMin(hour, minute)
  if (hours < 24 && day === dayNew) {
    return hourMin
  }
  if (isYestday(dateTime, now)) { return '昨天 ' + hour + ':' + ('0' + minute).slice(-2) }
  if (isTheDayBeforeYestday(dateTime, now)) { return '前天 ' + hour + ':' + ('0' + minute).slice(-2) }
  if (year === yearNew) { return `${month}月${day}日` }
  return `${year}年${month}月${day}日`
};

export function getPassedTime (GMTString) {
  var dateTime = new Date(GMTString)
  var day = dateTime.getDate()
  var now = new Date()
  var nowNew = Date.parse(now.toUTCString())
  var dayNew = now.getDate()
  var minutes = Math.round((nowNew - GMTString) / 1000 / 60)
  if (minutes < 1) { return '刚刚' }
  if (minutes < 60) { return minutes + '分钟前' }
  var hours = Math.round(minutes / 60)
  if (hours < 24 && day === dayNew) {
    return hours + '小时前'
  }
  if (isYestday(dateTime, now)) { return '昨天' }
  if (isTheDayBeforeYestday(dateTime, now)) { return '前天' }
  var days = Math.round(hours / 24)
  if (days <= 30) { return days + '天前' }
  var months = Math.round(days / 30)
  if (months <= 12) { return months + '个月前' }
  var years = Math.round(months / 12)
  return years + '年前'
};

export function getSaleTime (GMTString) {
  var timespan = Date.parse(GMTString)
  var dateTime = new Date(timespan)
  var day = dateTime.getDate()
  var now = new Date()
  var nowNew = Date.parse(now.toUTCString())
  var dayNew = now.getDate()
  var minutes = Math.round((nowNew - timespan) / 1000 / 60)
  if (minutes < 5) { return '刚刚擦亮' }
  if (minutes < 60) { return minutes + '分钟前' }
  var hours = Math.round(minutes / 60)
  if (hours < 24 && day === dayNew) {
    return hours + '小时前'
  }
  if (isYestday(dateTime, now)) { return '昨天' }
  if (isTheDayBeforeYestday(dateTime, now)) { return '前天' }
  var days = Math.round(hours / 24)
  if (days <= 30) { return days + '天前' }
  var months = Math.round(days / 30)
  if (months <= 12) { return months + '个月前' }
  var years = Math.round(months / 12)
  return years + '年前'
};

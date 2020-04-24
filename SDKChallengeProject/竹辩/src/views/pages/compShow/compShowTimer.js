import * as $ from 'jquery';
import appGlobal from './config.js';
import yesapi from "../../../webServices/yes3";

let statusNowLocal=0,BigTimerId=-1, bigTimerTime=0,freeTimerTime = [0,0];
let CTimerId=-1,CCTimerId=-1,timerStatus=[0,-1];

let nowTeam=-1;
let titleArr = ["","","",""];

let rules=[],lastRecId=0;

let lastId = 0, timerIsRunning = 0;

let recordingPoint =[] 


function changeStatusHTML(currentStatus,nextStatus) {
    $("#status").html(currentStatus);
    $("#statusnext").html(nextStatus);
  }


export async function roundSync() {
  if(appGlobal.catagory === 1) {
    let roundInfo = '[ { "currentRound":'+ appGlobal.statusNow +', "bigTime":'+ bigTimerTime +', "freeTimeLeft":'+ freeTimerTime[0] +', "freeTimeRight":'+ freeTimerTime[1] +',"currentStatus":'+ timerIsRunning +'  } ]'
    yesapi.table.updateViaID("game", {current_round:roundInfo}, appGlobal.gid);
  }
  else {
    let { data } = await yesapi.table.read(
      "game",
      [["id", "=", appGlobal.gid]],
      "and",
    );
    let {
      current_round,
    } = data.data;
    current_round = JSON.parse(current_round);
    changeStatusTo(current_round[0].currentRound);
    setTime(current_round[0].bigTime,current_round[0].freeTimeLeft,current_round[0].freeTimeRight)
  }
}

export function setTime(bigTimer,freeTimerLeft,freeTimerRight) {
  bigTimerTime = bigTimer;
  freeTimerTime[0] = freeTimerLeft;
  freeTimerTime[1] = freeTimerRight
}

export function checkTimer() {

    let min = Math.floor(bigTimerTime/60);
    let sec = bigTimerTime - min*60;
    if ( min < 10 ) min = "0" + min;
    if ( sec < 10 ) sec = "0" + sec;
    let str = min+" : "+sec;
    $("#mainTimer").html(str);

    if(bigTimerTime<=30&&rules[statusNowLocal][0]!=="3") $("#mainTimer").attr("class","text-center timer-main");
    else $("#mainTimer").attr("class","text-center timer-main"); //dangertime 未完成

    if(rules[statusNowLocal][0]==="1"){
      $("#mainTimer").attr("class","timer-hide");
      $("#freeTimerLeft").attr("class","text-left timer-free text-info");
      $("#freeTimerRight").attr("class","text-right timer-free text-danger");
    }else{
      $("#freeTimerLeft").attr("class","timer-hide");
      $("#freeTimerRight").attr("class","timer-hide");
      $("#mainTimer").attr("class","text-center timer-main");
    }
  
    min = Math.floor(freeTimerTime[0]/60);
    sec = freeTimerTime[0] - min*60;
    if ( min < 10 ) min = "0" + min;
    if ( sec < 10 ) sec = "0" + sec;
    str = min+" : "+sec;
    $("#freeTimerLeft").html(str);
  
    min = Math.floor(freeTimerTime[1]/60);
    sec = freeTimerTime[1] - min*60;
    if ( min < 10 ) min = "0" + min;
    if ( sec < 10 ) sec = "0" + sec;
    str = min+" : "+sec;
    $("#freeTimerRight").html(str);
  
  }

  function bigTimerDecline() {
    if(bigTimerTime === 0){
        stopBigTimer();
        if(rules[statusNowLocal][0]==="1") {
            titleArr = ["","","",""];
        }
        return;
    }
    bigTimerTime--;
  }
  function startBigTimer() {
    timerStatus[0]=1;
    BigTimerId = setInterval(bigTimerDecline,1000);

  }
  function stopBigTimer() {
    timerStatus[0]=0;
    clearInterval(BigTimerId);
  }
  function CTimerDecline() {
    if(freeTimerTime[1] === 0){
        stopCTimer();
        startCCTimer();
        return;
    }
    freeTimerTime[1]--;
  }
  function startCTimer() {
    if(freeTimerTime[1] === 0) {
        return ;
    }
    timerStatus[1]=0;
    CTimerId = setInterval(CTimerDecline,1000);
  }
  function stopCTimer() {
    timerStatus[1]=1;
    clearInterval(CTimerId);
  }
  function CCTimerDecline() {
    if(freeTimerTime[0] === 0){
        stopCCTimer();
        //document.getElementById("mic-3").play();
        //setTimeout("document.getElementById(\"mic-3\").pause();",5000);
        startCTimer();
        return;
    }
    freeTimerTime[0]--;
  }
  function startCCTimer() {
    if(freeTimerTime[0] === 0) {
        return ;
    }
    timerStatus[1]=1;
    CCTimerId = setInterval(CCTimerDecline,1000);
  }
  function stopCCTimer() {
    timerStatus[1]=0;
    clearInterval(CCTimerId);
  }
  function isInArray(arr,value){

    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
  }
  export function getPosi(posi) {
    posi = parseInt(posi)
    let posiName = ""
    if(posi > 4) { posi = posi - 4; posiName="反方" } else { posiName="正方" }
    switch (posi) {
      case 1:
        return(posiName + "一辩")
      case 2:
        return(posiName + "二辩")
      case 3:
        return(posiName + "三辩")
      case 4:
        return(posiName + "四辩")
      default: break;
    } 
  }
  function posiToName(posi) {
    posi = parseInt(posi)
    if(posi > 4) { posi = posi - 4 }
    switch (posi) {
      case 1:
        return("一")
      case 2:
        return("二")
      case 3:
        return("三")
      case 4:
        return("四")
      default: break;
    } 
  }
  export function getStatusInfo(id) {
    rules = appGlobal.rules
    let debateSess = [], speakerPosi = [], statusInfo=[]
    let [type,session,time,,speakerPermission]=rules[id];
    if(isInArray(speakerPermission,appGlobal.debatorPosi)) 
    { statusInfo[0] = "允许发言"; } else { statusInfo[0] = "禁止发言";}
    debateSess = session.split("|")
    speakerPosi = speakerPermission.split("|")
    if(type === "1") { statusInfo[1] = "双计时器" } else { statusInfo[1] = "单计时器" }
    statusInfo[2] = parseInt(time);
    if(debateSess[0] === "C"){
      titleArr[0] = "正" 
      titleArr[5] = "反" 
    } else {
      titleArr[0] = "反" 
      titleArr[5] = "正" 
    }
    titleArr[2] = " · "
    statusInfo[4] = getPosi(appGlobal.debatorPosi)
    switch (debateSess[1]) {
      case "LOR":
        titleArr[1] = "方" + posiToName(speakerPosi[0]) + "辩"
        titleArr[3] = "立论"
        statusInfo[3] = titleArr[0] + titleArr[1] + titleArr[2] + titleArr[3]
        break;
      case "RLOR":
        titleArr[1] = posiToName(speakerPosi[0])
        titleArr[3] = "驳立论"
        titleArr[4] = " · "
        titleArr[6] = posiToName(speakerPosi[1])
        statusInfo[3] = titleArr[0] + titleArr[1] + titleArr[2] + titleArr[3] + titleArr[4] + titleArr[5] + titleArr[6]
        break;
      case "AQ":
        titleArr[1] = posiToName(speakerPosi[0])
        titleArr[3] = "质询"
        titleArr[4] = " · "
        titleArr[6] = "方"
        statusInfo[3] = titleArr[0] + titleArr[1] + titleArr[2] + titleArr[3] + titleArr[4] + titleArr[5] + titleArr[6]
        break;
      case "CONC":
        titleArr[1] = "方" + posiToName(speakerPosi[0]) + "辩"
        titleArr[3] = "质辩小结";
        statusInfo[3] = titleArr[0] + titleArr[1] + titleArr[2] + titleArr[3]
        break;
      case "FREE":
        statusInfo[3] = "自由辩论";
        break;
      case "FCONC":
        titleArr[1] = "方" + posiToName(speakerPosi[0]) + "辩"
        titleArr[3] = "总结陈词";
        statusInfo[3] = titleArr[0] + titleArr[1] + titleArr[2] + titleArr[3]
        break;
      case "STA":
        statusInfo[3] = "准备阶段"
        break;
      case "FIN":
        statusInfo[3] = "赛事结束"
        break;
      default: break;
    } 
      return(statusInfo)
  }

  export function changeStatusTo(id) {
    if(id>lastId){ 
      recordingPoint[id] = appGlobal.recordingTime
      appGlobal.recordingPoint = recordingPoint
      console.log(recordingPoint)
    }
    lastId = id;
    statusNowLocal = id;
    appGlobal.statusNow = id;
    if(id !== lastRecId) {
      let currentStatusInfo = []
      currentStatusInfo = getStatusInfo(id)
      if(appGlobal.catagory === 1) {
        let roundInfo = '[ { "currentRound":'+ appGlobal.statusNow +', "bigTime":'+ bigTimerTime +', "freeTimeLeft":'+ freeTimerTime[0] +', "freeTimeRight":'+ freeTimerTime[1] +',"currentStatus":'+ timerIsRunning +'  } ]'
        yesapi.table.updateViaID("game", {current_round:roundInfo}, appGlobal.gid);
      }
      if(currentStatusInfo[0] === "允许发言") {
        appGlobal.ifMute = false
        $("#speakPermission").html("允许发言");
        $("#speakPermission").attr("class","badge-glow badge-lg badge badge-success");
      } else {
        appGlobal.ifMute = true
        $("#speakPermission").html("禁止发言");
        $("#speakPermission").attr("class","badge-glow badge-lg badge badge-danger");
      }
      if(currentStatusInfo[1] === "双计时器") {
          freeTimerTime[0]=currentStatusInfo[2];
          freeTimerTime[1]=currentStatusInfo[2];
      }
        bigTimerTime=parseInt(currentStatusInfo[2]);
      if (statusNowLocal!== rules.length-2) {
      changeStatusHTML(currentStatusInfo[3],getStatusInfo(id+1)[3]);
      } else {
      changeStatusHTML(currentStatusInfo[3],"已经最后环节");
      }
      if(appGlobal.catagory === 1) {

        let roundInfo = '[ { "currentRound":'+ appGlobal.statusNow +', "bigTime":'+ bigTimerTime +', "freeTimeLeft":'+ freeTimerTime[0] +', "freeTimeRight":'+ freeTimerTime[1] +',"currentStatus":'+ timerIsRunning +'  } ]'
        yesapi.table.updateViaID("game", {current_round:roundInfo}, appGlobal.gid);
      }
      }
      lastId = id;
    }

  export function timerStatusChangeTo(statusCode){
    if (statusCode === 0) {
      if (timerIsRunning === 1){
        timerStatusChange()
      }
    } else if (statusCode === 1) {
      if (timerIsRunning === 0){
        timerStatusChange()
      }
    }
  }

  export function timerStatusChange(){
    if(rules[statusNowLocal][0]==="1") {
  
    if(timerStatus[1]===-1) {
      timerIsRunning = 1
      if(nowTeam === 1) startCTimer();
      else startCCTimer();
      }
      else if(timerStatus[1]===0) {
        timerIsRunning = 1
        stopCTimer();
        startCCTimer();
      }
      else if(timerStatus[1]===1) {
        timerIsRunning = 1
        stopCCTimer();
        startCTimer();
      }
    }
      else {
      if(timerStatus[0]===0) {
        timerIsRunning = 1
        startBigTimer();
      }
      else {
        timerIsRunning = 0
        stopBigTimer();
    }

  }}
  
  export function timerClear(){
    stopBigTimer();
    stopCTimer();
    stopCCTimer();
    timerStatus = [0,0];

  }
  export function nextStage() {
    if (statusNowLocal!== rules.length-2) {
      changeStatusTo(statusNowLocal+1);

    }
    //updateTitle();
  }
  export function lastStage() {
    if (statusNowLocal!== 0) {
      changeStatusTo(statusNowLocal-1);
    }
    //updateTitle();
  }
  
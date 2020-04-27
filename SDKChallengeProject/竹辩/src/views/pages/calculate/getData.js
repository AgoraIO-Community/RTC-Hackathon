import appGlobal from "./config.js";
import yesapi from "../../../webServices/yes3";
let alreadySet = false

function gradeCalculator(grade) {
    let result = [],tempResult=[],totalGrade = 0
    for(let i = 0; i < grade.length; i++){
      tempResult = grade[i]
      let successTimes = tempResult.detect_success_times
      let totalTimes = tempResult.detect_total_times
      result[i+1] = parseInt((successTimes/totalTimes)*100)
      totalGrade = totalGrade + result[i+1]
    }
    result[0] = (totalGrade / (grade.length)).toFixed(2)
    return result
  }

function gradeAvg(participation,fluency,stage_performance,self_confidence) {
    let result = [],totalGrade = 0
    for(let i = 0; i < participation.length-1; i++){
      result[i+1] = parseInt((((participation[i+1]*1)+(fluency[i+1]*1)+(stage_performance[i+1]*1)+(self_confidence[i+1]*1))/4))
      totalGrade = totalGrade + result[i+1]
    }
    result[0] = (totalGrade / (participation.length-1)).toFixed(2)
    return result
  }

  export default async function getData() {
    let gid = localStorage.getItem("gid")
    let uuid = localStorage.getItem("uuid");
    if (gid === undefined) gid = 1;
    
    alreadySet = true
    let ret2 = await yesapi.table.read(
        "game",
        [
          ["id", "=", gid],
        ],
        "and",
      );
    let { team_info, winner } = ret2.data.data;
    let ret1 = await yesapi.table.read(
      "ai_grade",
      [
        ["gid", "=", gid],
        ["userid", "=", uuid],
      ],
      "and",
    );
    let { participation, fluency, stage_performance, self_confidence, emotions } = ret1.data.data;
    
    participation = JSON.parse(participation);
    fluency = JSON.parse(fluency)
    stage_performance = JSON.parse(stage_performance)
    self_confidence = JSON.parse(self_confidence)
    emotions = JSON.parse(emotions)
    appGlobal.participation = gradeCalculator(participation)
    appGlobal.fluency = gradeCalculator(fluency)
    appGlobal.stage_performance = gradeCalculator(stage_performance)
    appGlobal.self_confidence = gradeCalculator(self_confidence)
    appGlobal.AIGrade = gradeAvg(appGlobal.participation,appGlobal.fluency,appGlobal.stage_performance,appGlobal.self_confidence)
    
    let ret3 = await yesapi.table.read(
      "grade",
      [
        ["comp_id", "=", gid],
      ],
      "and",
    );

    let { grade } = ret3.data.data;
    let gradeMood = grade.split("|")
    appGlobal.RefAvg = JSON.parse(gradeMood[0])
    appGlobal.RefGrade = JSON.parse(gradeMood[1])

    winner = JSON.parse(winner)
    team_info = JSON.parse(team_info)
    appGlobal.posi = winner[0]
    appGlobal.neg = winner[1]
    appGlobal.team_info = team_info

  }
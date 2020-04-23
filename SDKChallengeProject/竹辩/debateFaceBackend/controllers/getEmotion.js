var request = require('request');
const { InvalidQueryError } = require('../lib/error')
const getEmotion = {}

getEmotion.getEmotion = async (ctx, next) => {
    const {img} = ctx.request.body;
    if (!img) {
        throw new InvalidQueryError()
    }
    //console.log(img)
    let result = img.split(",")
    let base64 = result[1]
    console.log(base64)

    function httprequest(){
        var subscription_key = ''
        var face_api_url = 'https://eastasia.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false&returnFaceAttributes=age%2Cgender%2CheadPose%2Csmile%2CfacialHair%2Cglasses%2Cemotion%2Chair%2Cmakeup%2Cocclusion%2Caccessories%2Cblur%2Cexposure%2Cnoise'
        var image_url = img
    
        return new Promise((resolve, reject)=>{
            var option ={
            url: face_api_url,
            method: "POST",
            json: true,
            headers: {
                "Ocp-Apim-Subscription-Key": subscription_key,
            },
            body: { "url": image_url }
            }
            request(option, function(error, response, body) {
                console.log(error,response,body);
            if (!error && response.statusCode == 200) {
                resolve(body)
            }
            });
        });
    };

    function putb64(){
        var pic = result[1];
        var url = "http://upload.qiniup.com/putb64/20264"; //非华东空间需要根据注意事项 1 修改上传域名
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange=function(){
          if (xhr.readyState==4){
            document.getElementById("myDiv").innerHTML=xhr.responseText;
          }
        }
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/octet-stream");
        xhr.setRequestHeader("Authorization", "UpToken 填写你从服务端获取的上传token");
        xhr.send(pic);
      }

    
    await httprequest().then(async function(req){
        ctx.code = 200;
        ctx.msg = 'Successful';
        ctx.result = req;
        return next()
    })
}

module.exports = getEmotion

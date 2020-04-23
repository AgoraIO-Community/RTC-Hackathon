const pca = require('../lib/pca');
const {computeTrainResult} = require('./pcaProcess');
const facialService = require('../services').facialFeatures;
const faceIdentification = {};

faceIdentification.faceIdentification = async (ctx, next) => {
    const { faceData } = ctx.request.body;
    if (!ctx.trainedResult) {
        ctx.trainedResult = await computeTrainResult();
    }

    const detectResult = pca.pcaExec(faceData, ctx.trainedResult);
    const responseData = await facialService.findOne({UUID: detectResult}); //完成面部识别

    ctx.code = 200;
    ctx.msg = 'Successful';
    ctx.result = {
        UUID: responseData.UUID,
    };
    return next();
}

module.exports = faceIdentification;

const facialService = require('../services').facialFeatures;
const {computeTrainResult} = require('./pcaProcess');

const addFacialFeatures = {};

addFacialFeatures.addFacialFeatures = async (ctx, next) => {

    const {UUID, faceData} = ctx.request.body
    const stu = await facialService.findOne({UUID});
    if (stu) {
        const newStu = {
            faceData: [...stu.faceData, ...faceData]
        };
        await facialService.update({UUID}, newStu);
    } else {
        await facialService.add({UUID});
    }
    ctx.code = 200;
    ctx.msg = 'Successful';
    ctx.result = '';
    
    computeTrainResult().then(trainedResult => {
        ctx.trainedResult = trainedResult;
    });

    return next();
};

module.exports = addFacialFeatures;

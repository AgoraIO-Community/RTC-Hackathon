const facialService = require('../services').facialFeatures;
const pca = require('../lib/pca');


const computeTrainResult = async () => {
    const docs = await facialService.Structure.find();
    console.log(docs)
    const sourceFace = [],
            UUIDMap = new Map();
    let count = 0;
    
    docs.forEach(doc => {
        doc.faceData.forEach(face => {
            const flatFace = [];
            face.forEach(row => flatFace.push(...row));
            sourceFace.push(flatFace);
            UUIDMap.set(count, doc.UUID);
            count++;
        });
    });
    return pca.pcaTrain(sourceFace, UUIDMap); //训练结束
};

module.exports = {
    computeTrainResult
};

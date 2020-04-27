/**
 * PCA算法的实现 参考Github项目
 */

const {
    Matrix,
    EigenvalueDecomposition
} = require('ml-matrix');

/**
 * 
 * @param {number[][]} data 训练集 
 * @param {number} targetDimension 目标维度
 */
const pcaTrain = (data = [[]], UUIDMap, targetDimension = 72) => {
    const faceMatrix = new Matrix(data).transpose();
    const rows = faceMatrix.rows,
        columns = faceMatrix.columns;

    // 求均值向量和均值向量矩阵
    const avgMatrix = new Matrix(rows, columns);
    const avgVector = [];
    for (let i = 0; i < rows; i++) {
        const rowValue = faceMatrix.getRow(i);
        const rowSum = rowValue.reduce((preSum, value) => preSum + value, 0);
        const rowAvg = rowSum / columns;
        avgVector.push(rowAvg);
        const avgRow = rowValue.map(value => value - rowAvg);
        avgMatrix.setRow(i, avgRow);
    }
    //console.log('均值向量');

    // 求协方差矩阵
    const covMatrix = faceMatrix.mmul(faceMatrix.transpose()).mul(1/columns);
    //console.log('协方差矩阵');

    // 求特征向量
    const e = new EigenvalueDecomposition(covMatrix);
    const vectors = e.eigenvectorMatrix;
    //console.log('特征向量');

    // 对训练数据降维处理
    const vectorsArray = [];
    for (let k = 0; k < targetDimension; k++) {
        vectorsArray.push(vectors.getRow(k));
    }
    const vectorMatrix = new Matrix(vectorsArray); // 特征向量矩阵
    const trainedMatrix = vectorMatrix.mmul(avgMatrix); // 降维后的数据矩阵

    const trainedData = [];
    for (let l = 0; l < trainedMatrix.columns; l++) {
        trainedData.push(trainedMatrix.getColumn(l));
    }
    // trainedMatrix.getColumnVector()
    return {
        trainedData,
        trainedMatrix,
        avgVector,
        vectorMatrix,
        UUIDMap
    };
};

/**
 * 
 * @param {number[][]} targetData 
 */
const pcaExec = (targetData = [], trainResult) => {
    const { trainedData, trainedMatrix, avgVector, vectorMatrix, UUIDMap } = trainResult;
    const targetVector = [];

    targetData.forEach(item => targetVector.push(...item));

    const targetAvgVector = targetVector.map((item, index) => item - avgVector[index]);
    const targetMatrix = new Matrix([targetAvgVector]).transpose();

    const reducedMatrix = vectorMatrix.mmul(targetMatrix);

    const subedVector = [];
    console.log(trainedMatrix.columns, 'trainedMatrix columns');
    console.log(trainedMatrix.rows, 'trainedMatrix rows');
    for (let i = 0; i < trainedMatrix.columns; i++) {
        const columnMatrix = trainedMatrix.getColumnVector(i).sub(reducedMatrix.getColumnVector(0));
        subedVector.push(columnMatrix.getColumn(0));
    }

    const modeHeight = subedVector.map(vector => getModeHeight(vector));
    console.log(modeHeight, 'modHeight');
    console.log(UUIDMap, 'UUIDMap');

    return UUIDMap.get(findMinIndex(modeHeight));
};

const findMinIndex = (source = []) => {
    const minValue = Math.min(...source);
    return source.findIndex(value => value === minValue);
}

const getModeHeight = (vector = []) => {
    const squarSum = vector.reduce((sum, value) => sum + value * value, 0);
    return Math.sqrt(squarSum);
}


module.exports = {
    pcaTrain,
    pcaExec
};

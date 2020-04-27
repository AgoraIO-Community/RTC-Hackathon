import { initCanvas } from './getImageData';


/**
 * 灰度加权平均系数
 */
const R = 30, G = 59, B = 11;

/** 
 * 图像灰度化
 */
export function grayPocess(imageData) {
    const data = imageData.data;
    const grayMatrix = [];
    const alpaMatrix = [];
    for (let i = 0; i < imageData.height; i++) {
        const line = [];
        const alpaLine = [];
        for (let j = 0; j < imageData.width; j++) {
            const beginIndex = (i * imageData.width + j) * 4 - 1;
            const grayValue = (data[beginIndex + 1] * R + data[beginIndex + 2] * G + data[beginIndex + 3] * B) / (R + G + B);
            line.push(grayValue);
            alpaLine.push(data[beginIndex + 4]);
        }
        grayMatrix.push(line);
        alpaMatrix.push(alpaLine);
    }
    return {
        grayMatrix,
        alpaMatrix
    };
}

/**
 * 
 * @param {number[][]} grayMatrix RGBA中RGB灰度化的值
 * @param {number[][]} alpaMatrix RGBA中的A值
 */
export function getGrayScaleImage(grayMatrix, alpaMatrix = []) {
    const ctx = initCanvas().getContext('2d');
    const width = (grayMatrix[0] || []).length,
        height = grayMatrix.length;
    const grayImageData = ctx.createImageData(width, height);
    for (let i = 0; i < height; i++) {
        for (let j = 0; j < width; j++) {
            const beginIndex = (i * width + j) * 4;
            grayImageData.data[beginIndex] = grayMatrix[i][j];
            grayImageData.data[beginIndex + 1] = grayMatrix[i][j];
            grayImageData.data[beginIndex + 2] = grayMatrix[i][j];
            grayImageData.data[beginIndex + 3] = (alpaMatrix[i] && alpaMatrix[i][j]) || 255;
        }
    }
    return grayImageData;
}








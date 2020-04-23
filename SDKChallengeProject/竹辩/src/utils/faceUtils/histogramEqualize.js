const L = 255; // RGB灰度级数

/**
 * 直方图均衡化
 * @param {number[][]} grayMatix 
 */
export default function histogramEqualize(grayMatix = []) {
    const appearCountMap = {};
    grayMatix.forEach(line => {
        line.forEach(value => appearCountMap[value] = appearCountMap[value] ? appearCountMap[value] + 1 : 1);
    });

    const keys = Object.keys(appearCountMap).map(key => Number(key)).sort((a, b) => a - b);
    const cdfMap = keys.reduce((cdfMapPrev, key, index) => {
        if (index === 0) {
            cdfMapPrev[key] = appearCountMap[key];
            return cdfMapPrev;
        }

        const prevKey = keys[index - 1];
        cdfMapPrev[key] = cdfMapPrev[prevKey] + appearCountMap[key];
        return cdfMapPrev;
    }, {});

    const cdfValue = Object.values(cdfMap);
    const cdfMin = Math.min(...cdfValue);
    const width = grayMatix[0].length,
        height = grayMatix.length;

    const h = grayRGB => Math.round((cdfMap[grayRGB] - cdfMin) / (width * height - cdfMin) * (L - 1));

    return grayMatix.map(line => line.map(value => h(value)));
}

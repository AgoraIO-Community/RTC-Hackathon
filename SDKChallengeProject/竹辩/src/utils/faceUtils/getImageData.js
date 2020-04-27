/**
 * 创建canvas上下文
 */
let canvas = null;
export const initCanvas = () => {
    if (canvas) {
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        return canvas;
    }
    canvas = document.createElement('canvas');
    canvas.width = document.body.clientWidth;
    canvas.height = document.body.clientHeight;
    return canvas;
};

/**
 * 获取图像像素信息
 * 通过URL.createObjectUrl构造imageUrl
 * @param {string | Image} imageSource 
 */
export default function getImageData(imageSource) {
    const ctx = initCanvas().getContext('2d');

    return new Promise((resolve, reject) => {
        if (imageSource instanceof Image) {
            ctx.drawImage(imageSource, 0, 0);
            resolve(ctx.getImageData(0, 0, imageSource.width, imageSource.height));
        } else {
            const image = new Image();

            image.onload = () => {
                ctx.drawImage(image, 0, 0);
                resolve(ctx.getImageData(0, 0, image.width, image.height));
            };

            image.onerror = () => {
                console.log(`加载图片出错: ${imageSource}`);
                reject();
            };

            image.src = imageSource;
        }
    });
}

export const clipImage = (img, faceDetectionBox) => {
    const { x, y, width, height } = faceDetectionBox;
    const ctx = initCanvas().getContext('2d');
    ctx.drawImage(img, x, y, width, height, 0, 0, width, height);
    return ctx.getImageData(0, 0, width, height);
};

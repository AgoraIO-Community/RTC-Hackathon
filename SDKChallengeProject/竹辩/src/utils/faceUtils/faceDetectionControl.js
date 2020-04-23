import * as faceapi from 'face-api.js';

export default faceapi;

export const SSD_MOBILENETV1 = 'ssd_mobilenetv1';
export const TINY_FACE_DETECTOR = 'tiny_face_detector';

let currentModel;

export const getCurrentDetector = () => currentModel === TINY_FACE_DETECTOR ? faceapi.nets.tinyFaceDetector : faceapi.nets.ssdMobilenetv1;

export const getDetectorOptions = () => currentModel === TINY_FACE_DETECTOR ? new faceapi.TinyFaceDetectorOptions() : new faceapi.SsdMobilenetv1Options();

export const loadModel = (modelType = TINY_FACE_DETECTOR) => {
    if (currentModel === modelType && isModelLoaded()) {
        return Promise.resolve();
    }
    currentModel = modelType;
    return getCurrentDetector().loadFromUri('/models');
};

export const isModelLoaded = () => !!getCurrentDetector().params;

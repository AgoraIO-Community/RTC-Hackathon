import React, {useEffect} from 'react';
import "../../assets/scss/pages/compShow.scss"
let mediaStreamTrack
export const stopStream = () => {
    mediaStreamTrack.getTracks()[0].stop();
};
const initStream = () => {
    const videoEl = document.querySelector('#web-camera-video');
    navigator.mediaDevices.getUserMedia({ video: true, audio: false}).then(userStream => {
        videoEl.srcObject = userStream;
        mediaStreamTrack = userStream
    }).catch(err => {
        console.error(`getUserMedia: fail! error:${err}`);
    });
};

const initWidth = props => {
    const { width = 1150 } = props;
    let streaming = false;
    const videoEl = document.querySelector('#web-camera-video');
    videoEl.addEventListener('canplay', function(){
        if (!streaming) {
            const height = (videoEl.videoHeight / (videoEl.videoWidth/width));
            videoEl.setAttribute('width', width);
            videoEl.setAttribute('height', height);
            streaming = true;
        }
    }, false);
};

const WebCamera = (props, ref) => {
    useEffect(() => {
        // didMounted时调用
        initStream(props);
        document.querySelector('#web-camera-video').play();

    }, []);

    useEffect(() => {
        initWidth(props);
    });

    const { onLoadedMetaData } = props;
    return (
        <div className='webCamera'>
            <video id="web-camera-video" ref={ref} onLoadedMetadata={onLoadedMetaData}>摄像头未开启或无权限。</video>
        </div>
    );
};

export default React.forwardRef(WebCamera);


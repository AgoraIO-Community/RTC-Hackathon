const gridWidth = 1200;
const gridHeight = 900;

function VideoGrid($elem){
    var self = this;
    self.$elem = $elem;
    self.videoContainers = [];
    self.index = 0;

    self.$elem.css({
        width: `${gridWidth}px`,
        height: `${gridHeight}px`
    });

    self.addVideoContainer = (videoContainer) =>{
        if (self.videoContainers.length >= 16){
            alert(`Video count exceeds 16`)
            return;
        }
        let putSuccess = self.putVideoContainerInitPosition(videoContainer);
        while (!putSuccess){
            for (var i in self.videoContainers){
                if(self.videoContainers[i].widthGrid > 1){
                    if (self.videoContainers.length == 1){
                        self.videoContainers[i].widthGrid -= 2;
                    }else{
                        self.videoContainers[i].widthGrid -= 1;
                    }
                    self.videoContainers[i].calculateOccupies();
                    putSuccess = self.putVideoContainerInitPosition(videoContainer);
                    break;
                }
            }
        }

        window.videoContainer = videoContainer;
        self.$elem.append(videoContainer.$container);
        self.videoContainers.push(videoContainer);
        self.animatePositions();

        const handleContainerAnimation = (evt)=>{
            console.log("handleContainerAnimation", evt);
            window.evt = evt;
            const videoContainer = self.videoContainers.find((container)=>{
                return container.index == $(evt.target).attr("index");
            });
            console.log("handleContainerAnimation videoContainer", videoContainer);
            videoContainer.xGrid = parseInt(videoContainer.$container.css('left')) * 4 / gridWidth;
            videoContainer.yGrid = parseInt(videoContainer.$container.css('top')) * 4 / gridHeight;
            videoContainer.widthGrid = parseInt(videoContainer.$container.css('width')) * 4 / gridWidth;
            videoContainer.calculateOccupies();
        };
        videoContainer.$container.draggable({
            grid: [gridWidth / 4, gridHeight / 4],
            stop: handleContainerAnimation,
            containment: 'parent',
        });

        videoContainer.$container.resizable({
            grid: [gridWidth / 4, gridHeight / 4],
            aspectRatio: gridWidth / gridHeight,
            stop: handleContainerAnimation,
            containment: 'parent',
        });
    };

    self.putVideoContainerInitPosition = (videoContainer) =>{
        let positionFound = false;
        let widthGrid = null;
        let xGrid = null;
        let yGrid = null;
        let occupies = [];
        for (widthGrid = 4; widthGrid > 0; widthGrid--){
            for (yGrid = 0; yGrid + widthGrid <= 4; yGrid++){
                for (xGrid = 0; xGrid + widthGrid <= 4; xGrid++){
                    videoContainer.widthGrid = widthGrid;
                    videoContainer.xGrid = xGrid;
                    videoContainer.yGrid = yGrid;
                    videoContainer.calculateOccupies();
                    positionFound = true;
                    for (var containerId in self.videoContainers){
                        const existingContainer = self.videoContainers[containerId];
                        console.log("existingContainer.occupies", existingContainer.occupies, "videoContainer.occupies", videoContainer.occupies);
                        if (_.intersection(existingContainer.occupies, videoContainer.occupies).length){
                            positionFound = false;
                            break;
                        }
                    }
                    if (positionFound){
                        console.log("positionFound", xGrid, yGrid, widthGrid);
                        return true;
                    }
                }
            }
        }
    }

    self.animatePositions = function(){
        for (var i in self.videoContainers){
            const videoContainer = self.videoContainers[i];
            videoContainer.$container.css({
                'z-index': i
            });
            const aniConfig = {
                width: (videoContainer.widthGrid / 4 * 100) + '%',
                height: (videoContainer.widthGrid / 4 * 100) + '%',
                left: (videoContainer.xGrid / 4 * 100) + '%',
                top: (videoContainer.yGrid / 4 * 100) + '%',
            };
            console.log("aniConfig", aniConfig)
            videoContainer.$container.animate(aniConfig, 200);
            videoContainer.$container.css({
                'z-index': i
            });
        }
    }
}

function VideoContainer(mediaObject, videoGrid){
    var self = this;
    self.videoTrack = mediaObject.getVideoTracks()[0];
    self.audioTrack = mediaObject.getAudioTracks()[0];
    mediaObject.removeTrack(self.audioTrack);
    self.mediaObject = mediaObject;
    self.index = "" + videoGrid.index++;
    self.$video = $(`<video index="${self.index}" autoplay muted style="width: 100%;"></video>`);
    self.$video[0].srcObject = self.mediaObject;
    self.$container = $(`<div class="video-container" index="${self.index}" style="position: absolute; left: 0;"></div>`);
    self.$container.css({
        position: 'absolute',
    });
    self.$container.append(self.$video);
    console.log("self.$container", self.$container)

    self.videoTrack.onended = function(){
        self.destroy();
    }

    self.audioStream = new MediaStream();
    self.audioStream.addTrack(self.audioTrack);
    self.audioSource = audioContext.createMediaStreamSource(self.audioStream);
    self.gainNode = audioContext.createGain();
    self.audioSource.connect(self.gainNode);
    self.gainNode.connect(audioContext.destination);

    self.widthGrid = 0;
    self.xGrid = 0;
    self.yGrid = 0;
    self.calculateOccupies = ()=>{
        if (self.widthGrid  == 1){
            self.gainNode.gain.value = 0.1;
        }else{
            self.gainNode.gain.value = self.widthGrid / 4;
        }

        self.occupies = [];
        for (var i = self.xGrid; i < self.xGrid + self.widthGrid; i++){
            for (var j = self.yGrid; j < self.yGrid + self.widthGrid; j++){
                self.occupies.push(i * 100 + j);
            }
        }
        console.log("x", self.xGrid, "y", self.yGrid, "size", self.widthGrid, "occupies", self.occupies)
    }
    self.destroy = function(){
        console.log("Destroying container", self);
        if (self.audioTrack){
            self.audioTrack.stop();
        }
        if (self.gainNode){
            self.gainNode.disconnect();
        }
        if (self.$container){
            self.$container.remove();
        }
    };
};

let videoGrid = null;

async function addOneVideoContainer(){
    const mediaObject = await navigator.mediaDevices.getUserMedia({
        audio: true,
        video: {aspectRatio: gridWidth / gridHeight}
    });
    console.log("mediaObject", mediaObject);
    const videoContainer = new VideoContainer(mediaObject, videoGrid);
    videoGrid.addVideoContainer(videoContainer);
}

async function main(){

    videoGrid = new VideoGrid($("#videoGrid"));
    await addOneVideoContainer();

    const client = AgoraRTC.createClient({mode: 'live'});
    client.init("0c0b4b61adf94de1befd7cdd78a50444", function(){
        client.join("0c0b4b61adf94de1befd7cdd78a50444", cname, null, function(uid){
            const localStream = AgoraRTC.createStream({streamID: uid, audio: true, video: true, screen: false});
            localStream.setVideoProfile('480p_1');
            localStream.init(function(){
                console.log("localStream init success", localStream);
                client.publish(localStream);
            });
        });
    });
    client.on('stream-added', function (evt) {
        var stream = evt.stream;
        console.log("Subscribe ", stream);
        client.subscribe(stream, function (err) {
            console.log("Subscribe stream failed", err);
        });
    });

    client.on('stream-subscribed', function (evt) {
        var stream = evt.stream;
        console.log("Subscribe remote stream successfully: " + stream.getId());
        const videoContainer = new VideoContainer(stream.stream, videoGrid);
        videoContainer.uid = stream.getId();
        videoGrid.addVideoContainer(videoContainer);
    });

    client.on('peer-leave', function (evt) {
        console.log('peer-leave', evt);
        for (var i in videoGrid.videoContainers){
            const container = videoGrid.videoContainers[i];
            if (container.uid === evt.uid){
                console.log("Found uid in videoContainers", evt.uid, container);
                container.destroy();
                videoGrid.videoContainers.splice(i, 1);
                break;
            }
        }

    });

}


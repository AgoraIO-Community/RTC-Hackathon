package mobi.accessible.show.model;

import java.util.List;

public class ResourceResultBean {

    /**
     * resourceId : JyvK8nXHuV1BE64GDkAaBGEscvtHW7v8BrQoRPCHxmeVxwY22-x-kv4GdPcjZeMzoCBUCOr9q-k6wBWMC7SaAkZ_4nO3JLqYwM1bL1n6wKnnD9EC9waxJboci9KUz2WZ4YJrmcJmA7xWkzs_L3AnNwdtcI1kr_u1cWFmi9BWAWAlNd7S7gfoGuH0tGi6CNaOomvr7-ILjPXdCYwgty1hwT6tbAuaW1eqR0kOYTO0Z1SobpBxu1czSFh1GbzGvTZG
     * sid : 38f8e3cfdc474cd56fc1ceba380d7e1a
     * serverResponse : {"fileListMode":"json","fileList":[{"filename":"xxx.m3u8","trackType":"audio_and_video","uid":"123","mixedAllUser":"true","isPlayable":"true","sliceStartTime":"1562724971626"},{"filename":"xxx.m3u8","trackType":"audio_and_video","uid":"456","mixedAllUser":"true","isPlayable":"true","sliceStartTime":"1562724971626"}],"uploadingStatus":"uploaded"}
     */

    private String resourceId;
    private String sid;
    private ServerResponseBean serverResponse;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public ServerResponseBean getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponseBean serverResponse) {
        this.serverResponse = serverResponse;
    }

    public static class ServerResponseBean {
        /**
         * fileListMode : json
         * fileList : [{"filename":"xxx.m3u8","trackType":"audio_and_video","uid":"123","mixedAllUser":"true","isPlayable":"true","sliceStartTime":"1562724971626"},{"filename":"xxx.m3u8","trackType":"audio_and_video","uid":"456","mixedAllUser":"true","isPlayable":"true","sliceStartTime":"1562724971626"}]
         * uploadingStatus : uploaded
         */

        private String fileListMode;
        private String uploadingStatus;
        private List<FileListBean> fileList;

        public String getFileListMode() {
            return fileListMode;
        }

        public void setFileListMode(String fileListMode) {
            this.fileListMode = fileListMode;
        }

        public String getUploadingStatus() {
            return uploadingStatus;
        }

        public void setUploadingStatus(String uploadingStatus) {
            this.uploadingStatus = uploadingStatus;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }

        public static class FileListBean {
            /**
             * filename : xxx.m3u8
             * trackType : audio_and_video
             * uid : 123
             * mixedAllUser : true
             * isPlayable : true
             * sliceStartTime : 1562724971626
             */

            private String filename;
            private String trackType;
            private String uid;
            private String mixedAllUser;
            private String isPlayable;
            private String sliceStartTime;

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getTrackType() {
                return trackType;
            }

            public void setTrackType(String trackType) {
                this.trackType = trackType;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getMixedAllUser() {
                return mixedAllUser;
            }

            public void setMixedAllUser(String mixedAllUser) {
                this.mixedAllUser = mixedAllUser;
            }

            public String getIsPlayable() {
                return isPlayable;
            }

            public void setIsPlayable(String isPlayable) {
                this.isPlayable = isPlayable;
            }

            public String getSliceStartTime() {
                return sliceStartTime;
            }

            public void setSliceStartTime(String sliceStartTime) {
                this.sliceStartTime = sliceStartTime;
            }
        }
    }
}

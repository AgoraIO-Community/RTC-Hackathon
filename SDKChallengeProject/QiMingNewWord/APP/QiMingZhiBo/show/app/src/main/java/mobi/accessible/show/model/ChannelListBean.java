package mobi.accessible.show.model;

import java.util.List;

public class ChannelListBean {

    /**
     * success : true
     * data : {"channels":[{"channel_name":"lkj144","user_count":3}],"total_size":3}
     */

    private boolean success;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * channels : [{"channel_name":"lkj144","user_count":3}]
         * total_size : 3
         */

        private int total_size;
        private List<ChannelsBean> channels;

        public int getTotal_size() {
            return total_size;
        }

        public void setTotal_size(int total_size) {
            this.total_size = total_size;
        }

        public List<ChannelsBean> getChannels() {
            return channels;
        }

        public void setChannels(List<ChannelsBean> channels) {
            this.channels = channels;
        }

        public static class ChannelsBean {
            /**
             * channel_name : lkj144
             * user_count : 3
             */

            private String channel_name;
            private int user_count;

            public String getChannel_name() {
                return channel_name;
            }

            public void setChannel_name(String channel_name) {
                this.channel_name = channel_name;
            }

            public int getUser_count() {
                return user_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }
        }
    }
}

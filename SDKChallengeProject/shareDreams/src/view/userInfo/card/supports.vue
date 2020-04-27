<template>
    <div class="all-follows">
        <div class="intro">
            <p>我的支持者</p>
        </div>
        <div v-if="!loading">
            <div class="content" v-if="backupList.length!==0">
                <div class="all-follows" v-for="(item,index) in backupList" :key="index">
                    <div class="per-follow">
                        <img :src="item.supportUserDetail.userAvatar"  class="avatar"/>
                        <div class="detail">
                            <div class="per-name"><p>{{item.supportUserDetail.userName}}</p></div>
                            <div class="per-intro"><p>{{item.supportUserDetail.userIntro}}</p></div>
                        </div>
                        <div class="btn-groups">
                            <div class="chat" @click="chat(item)"><p>私信</p></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="none" v-else>
                <p>您暂时没有支持者</p>
                <div class="go" @click="navigateTo()">发布视频</div>
            </div>
        </div>
        <div class="content-grey" v-else>
            <div class="all-follows">
                <div class="per-follow">
                    <div class="avatar"/>
                    <div class="detail">
                        <div class="per-name"></div>
                        <div class="per-intro"></div>
                    </div>
                </div>
                <div class="per-follow">
                    <div class="avatar"/>
                    <div class="detail">
                        <div class="per-name"></div>
                        <div class="per-intro"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import API from '../../../utils/API'
export default {
  data () {
    return {
      backupList: [],
      loginUser: this.$store.state.loginUser,
      loading: true
    }
  },
  methods: {
    navigateTo () {
      this.$router.push('/user/record')
    },
    chat (item) {
      this.$router.push({name: 'message', params: {chatUser: item.supportUserDetail}})
    }
  },
  created () {
    API.users.getBackupUserList({supportUserId: this.loginUser._id}, (responseData) => { // 获取用户的支持者列表
      if (responseData.code === 200) {
        console.log(responseData)
        this.backupList = responseData.data
        this.loading = false
      }
    }, (err) => {
      console.log(err)
      this.$toast('获取列表失败')
    })
  }
}
</script>
<style lang="less" scoped>
.all-follows{
    padding-bottom: 20px;
    .intro{
        height: 50px;
        border-bottom:1px solid grey;
        display: flex;
        flex-direction: row;
        align-items: center;
        font-size: 13px;
        color: #0B8DD9;
        p{
            border-left: 2px solid #0B8DD9;
            padding-left: 10px;
            margin-left:20px;
        }
    }
    .content{
        min-height: 250px;
        flex:1;
        display: flex;
        flex-direction: column;
        .all-follows{
            .per-follow{
                margin: 0 30px;
                border-bottom: 1px solid grey;
                height: 120px;
                flex:1;
                display: flex;
                flex-direction: row;
                align-items: center;
                .avatar{
                    width: 60px;
                    height: 60px;
                    border: 3px solid grey;
                    border-radius: 50%;
                    z-index: 99;
                }
                .detail{
                    width: 500px;
                    display: flex;
                    flex-direction: column;
                    .per-name{
                        margin-left: 30px;
                        font-size: 15px;
                        font-weight: bold;
                        p{
                            width: 300px;
                            padding-left: -20px;
                        }
                    }
                    .per-intro{
                        margin-top: -25px;
                        font-size: 12px;
                        margin-left: 30px;
                        p{
                            width: 300px;
                            padding-left: -20px;
                            text-overflow: ellipsis;
                            overflow:hidden;
                            white-space:nowrap;
                        }
                    }
                }
                .btn-groups{
                    margin-left: 30px;
                    height: 40px;
                    display: flex;
                    flex-direction: row;
                    justify-content: center;
                    color: white;
                    font-size: 13px;
                    .chat{
                        width: 50px;
                        height: 30px;
                        background-color: #0B8DD9;
                        border-radius: 5px;
                        flex-direction: row;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        cursor: pointer;
                    }
                    .support{
                        margin-left: 30px;
                        padding: 0 10px;
                        height: 30px;
                        background-color: grey;
                        border-radius: 5px;
                        flex-direction: row;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        cursor: pointer;
                    }
                }
            }
            .per-video:hover{
                background-color: grey;
            }
        }
        .page{
            width: 100%;
            display: flex;
            justify-content: center;
        }
    }
    .content-grey{
        min-height: 250px;
        flex:1;
        display: flex;
        flex-direction: column;
        .all-follows{
            .per-follow{
                margin: 0 30px;
                border-bottom: 1px solid grey;
                height: 120px;
                flex:1;
                display: flex;
                flex-direction: row;
                align-items: center;
                .avatar{
                    width: 60px;
                    height: 60px;
                    border: 3px solid grey;
                    border-radius: 50%;
                    z-index: 99;
                    background: grey;
                }
                .detail{
                    width: 500px;
                    display: flex;
                    flex-direction: column;
                    .per-name{
                        margin-left: 30px;
                        width: 150px;
                        height: 15px;
                        font-size: 15px;
                        font-weight: bold;
                        background: grey;
                    }
                    .per-intro{
                        width: 250px;
                        margin-top: 5px;
                        font-size: 12px;
                        height: 12px;
                        margin-left: 30px;
                        background: grey;
                    }
                }
            }
        }
    }
    .none{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        min-height: 250px;
        p{
            color: rgb(66, 66, 66);
        }
        .go{
            margin-left: 10px;
            padding: 0 10px;
            height: 30px;
            background-color: #0B8DD9;
            border-radius: 5px;
            flex-direction: row;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            font-size: 12px;
            cursor: pointer;
        }
    }
}
</style>

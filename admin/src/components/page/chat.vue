<script>
import { actions } from '../../store';

import Card from './chat/card';
import List from './chat/list';
import TextSend from './chat/textSend';
import Message from './chat/message';

export default {
    components: { Card, List, TextSend, Message },
    vuex: {
        actions: actions
    },data () {
        return {
           count: 1
        };
    },
    created () {
        this.getDate();
        this.initData();
         setInterval(this.timer, 5000);
      },methods:{
        getDate(){
            this.$axios.post('/api/getChatData', {
                    lawOrder: this.refuseform
                }).then((res) => {
                    console.log(res.data)
                  this.$store.state.sessions = res.data;
                })
        },timer () {
            this.getDate()
        }
    }
}
</script>


<template>
<div id="app">
    <div class="sidebar">
        <card></card>
        <list></list>
    </div>
    <div class="main">
        <message></message>
        <TextSend></TextSend>
    </div>
</div>
</template>

<style lang="less" scoped>
#app {
    margin: 20px auto;
    width: 800px;
    height: 600px;

    overflow: hidden;
    border-radius: 3px;

    .sidebar, .main {
        height: 100%;
    }
    .sidebar {
        float: left;
        width: 200px;
        color: #f4f4f4;
        background-color: #2e3238;
    }
    .main {
        position: relative;
        overflow: hidden;
        background-color: #eee;
    }
    .text {
        position: absolute;
        width: 100%;
        bottom: 0;
        left: 0;
    }
    .message {
        height: ~'calc(100% - 160px)';
    }
}
</style>

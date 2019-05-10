<script>
import { actions } from '../../../store';

export default {
    vuex: {
        actions: actions,
        getters: {
            // 当前会话index
            currentId: ({ currentSessionId }) => currentSessionId
        }
    },
    
    data () {
        return {
            content: '',
            count: 0
        };
    },
    mounted: function () {
           
    },created(){
        
    },
    methods: {
        onKeyup (e) {
            if (e.ctrlKey && e.keyCode === 13 && this.content.length) {
            
                this.$axios.post('/api/sendMessage', {
                    toUser: this.currentId,
                    content:this.content
                }).then((res) => {
                    console.log(res.data)
                  this.sendMessage(this.content);
                    this.content = '';
                })
               
            }
        },sendText (content) {
           console.log(content)
        }
    }
};
</script>

<template>
<div class="text">
    <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" @keyup="onKeyup">kelaodejunmo</textarea>
</div>
</template>

<style lang="less" scoped>
.text {
    height: 160px;
    border-top: solid 1px #ddd;

    textarea {
        padding: 10px;
        height: 100%;
        width: 100%;
        border: none;
        outline: none;
        font-family: "Micrsofot Yahei";
        resize: none;
    }
}
</style>
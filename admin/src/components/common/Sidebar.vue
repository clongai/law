<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
                 text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-if="func === '1'">
                <template v-for="orderHandle in orderHandles">
                    <template v-if="orderHandle.subs">
                        <el-submenu :index="orderHandle.index" :key="orderHandle.index">
                            <template slot="title">
                                <i :class="orderHandle.icon"></i><span slot="title">{{ orderHandle.title }}</span>
                            </template>
                            <el-menu-item v-for="(subItem,i) in orderHandle.subs" :key="i" :index="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </el-submenu>
                    </template>
                    <template v-else>
                        <el-menu-item :index="orderHandle.index" :key="orderHandle.index">
                            <i :class="orderHandle.icon"></i><span slot="title">{{ orderHandle.title }}</span>
                        </el-menu-item>
                    </template>
                </template>
                <template v-if="orginazation === '2'">
                <template v-for="orderHandle in chatHandles">
                    <template v-if="orderHandle.subs">
                        <el-submenu :index="orderHandle.index" :key="orderHandle.index">
                            <template slot="title">
                                <i :class="orderHandle.icon"></i><span slot="title">{{ orderHandle.title }}</span>
                            </template>
                            <el-menu-item v-for="(subItem,i) in orderHandle.subs" :key="i" :index="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </el-submenu>
                    </template>
                    <template v-else>
                        <el-menu-item :index="orderHandle.index" :key="orderHandle.index">
                            <i :class="orderHandle.icon"></i><span slot="title">{{ orderHandle.title }}</span>
                        </el-menu-item>
                    </template>
                </template>
            </template>
            </template>
            
            <template v-else>
                <template v-for="bossView in bossViews">
                    <template v-if="bossView.subs">
                        <el-submenu :index="bossView.index" :key="bossView.index">
                            <template slot="title">
                                <i :class="bossView.icon"></i><span slot="title">{{ bossView.title }}</span>
                            </template>
                            <el-menu-item v-for="(subItem,i) in bossView.subs" :key="i" :index="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </el-submenu>
                    </template>
                    <template v-else>
                        <el-menu-item :index="bossView.index" :key="bossView.index">
                            <i :class="bossView.icon"></i><span slot="title">{{ bossView.title }}</span>
                        </el-menu-item>
                    </template>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '../common/bus';
    export default {
        data() {
            return {
                collapse: false,
                func: '',
                orginazation:'',
                orderHandles: [
                    {
                        icon: 'el-icon-setting',
                        index: 'order',
                        title: '报单处理'
                    }
                    
                ],
                 chatHandles: [
                   
                    {
                        icon: 'el-icon-setting',
                        index: 'chat',
                        title: '沟通与反馈'
                    }
                ],
                bossViews: [
                    {
                        icon: 'el-icon-menu',
                        index: 'bossView',
                        title: '老板视图'
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        },
        created(){
            this.func = localStorage.getItem('ms_userFunction');
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })
            this.orginazation = localStorage.getItem('ms_userOrginazation')
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 250px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>

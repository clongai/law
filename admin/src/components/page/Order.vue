<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-select v-model="status" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" >
                <el-table-column prop="orderId" label="订单编号" width="100">
                </el-table-column>
                <el-table-column prop="doneDate" label="提单时间" :show-overflow-tooltip="true" width="100">
                </el-table-column>
                <el-table-column prop="startTime" label="开始时间" width="150">
                </el-table-column>
                <el-table-column prop="startTime" label="结束时间" width="150">
                </el-table-column>
                <el-table-column prop="statusVal" label="订单状态" width="150">
                </el-table-column>
                <el-table-column prop="place" label="地点" :show-overflow-tooltip="true" width="150">
                </el-table-column>
                <el-table-column prop="happen" label="案件详情" :show-overflow-tooltip="true" width="200">
                </el-table-column>
                <el-table-column prop="possessions" label="涉及财物" :show-overflow-tooltip="true" width="200">
                </el-table-column>
                <el-table-column prop="appeal" label="诉求" :show-overflow-tooltip="true" width="200">
                </el-table-column>
                <el-table-column label="操作" fixed="right" width="150">
                    <template slot-scope="scope">
                        <!--待审核-->
                        <el-button v-if="((scope.row.status == '0' || scope.row.status == '16' || scope.row.status == '12' || scope.row.status == '13' ) && orginazation == '1')
                        || ((scope.row.status == '2' || scope.row.status == '15'  ) && orginazation == '2')
                        || (scope.row.status == '7' && orginazation == '3') " size="small" type="text" style="color:red;" @click="handleOrder(scope.row)">处理</el-button>
                        <!--其他状态只能查看-->
                        <el-button v-else size="small" type="text" @click="handleOrder(scope.row)">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page="page"
                    :page-size="pageSize"
                    layout="total, prev, pager, next"
                    :total="total"
                >
                </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                status: '0',
                orginazation: 1,
                options: [],
                url: './static/vuetable.json',
                tableData: [],
                page: 1,
                pageSize: 10,
                total: 100,
                serviceLevel:'',
            }
        },
        created() {
            this.orginazation = localStorage.getItem('ms_userOrginazation');
            this.serviceLevel = localStorage.getItem('ms_serviceLevel');
            debugger;
            this.getOption()
            //this.getCount()
            this.getData()
        },
        methods: {
            getCount(){
                this.$axios.post('/api/countLawOrderByStatus', {
                    status: this.status,
                    serviceLevel :this.serviceLevel,
                    orginazation: this.orginazation
                }).then((res) => {
                    this.total = res.data
                })
            },
            getData() {
                this.$axios.post('/api/queryLawOrderByStatus', {
                    status: this.status,
                    serviceLevel :this.serviceLevel,
                    orginazation: this.orginazation,
                    page: this.page - 1,
                    pageSize: this.pageSize
                }).then((res) => {
                    this.tableData = res.data.content;
                    this.total = res.data.totalElements;
                })
            },
            search() {
                this.page = 1
                //this.getCount()
                this.getData()
            },
            handleCurrentChange(val) {
                this.page = val
                //this.getCount()
                this.getData()
            },
            handleOrder(row){
                this.$router.push('/handle/OrderDetail?orderId='+row.orderId);
            },
            getOption() {
               
                switch(this.orginazation) {
                    case '1':
                        this.status = '0,1,2,6,3,11,12,13,14,15,16,17,18,5,4,7';
                        this.options = [{
                            value: '0,16,12',
                            label: '待审核'
                        }, {
                            value: '1',
                            label: '接受待付款'
                        }, {
                            value: '2',
                            label: '接受已付款'
                        },{
                            value: '3',
                            label: '拒绝'
                        },{
                            value: '0,1,2,6,3,11,12,13,14,15,16,17,18,5,4,7',
                            label: '全部'
                        }];
                        break;
                    case '2':
                        this.status = '2,15';
                        this.options = [{
                            value: '2,15',
                            label: '待审核'
                        }, {
                            value: '4',
                            label: '接受待付款'
                        }, {
                            value: '5',
                            label: '接受已付款'
                        },{
                            value: '6',
                            label: '拒绝'
                        },{
                            value: '0,1,2,6,3,11,12,13,14,15,16,17,18,5,4,7',
                            label: '全部'
                        }];
                        break;
                    case '3':
                        this.status = '7';
                        this.options = [{
                            value: '7',
                            label: '待审核'
                        }, {
                            value: '8',
                            label: '接受待付款'
                        }, {
                            value: '9',
                            label: '接受已付款'
                        },{
                            value: '10',
                            label: '拒绝'
                        }
                        ];
                        break;
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
</style>

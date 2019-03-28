<template>
    <div class="boss-view">
        <div class="statistics-title">推广统计中心</div>
        <div class="boss-view-content">
            <div class="boss-view-top clearfix">
                <div class="view-card">
                    <div class="view-card-item">
                        <div class="card-top">总人数</div>
                        <div class="card-bottom">{{totalPeople}}</div>
                        <img class="card-icon-img" src="../../../static/img/people.jpg"/>
                        <!--<div class="card-icon-img"></div>-->
                    </div>
                    <div class="view-card-item">
                        <div class="card-top">涉案金额（元）</div>
                        <div class="card-bottom">{{involvedNum}}</div>
                        <img class="card-icon-img" src="../../../static/img/income.jpg"/>
                        <!--<div class="card-icon-img"></div>-->
                    </div>
                </div>
                <div class="echarts-item">
                    <div> 
                        <lineChart
                            :color="incomeColor"
                            :id="incomeId"
                            :title="incomeTitle"
                            :y-unit="incomeYUnit"
                            :x-unit="incomeXUnit"
                            :realData="incomeYData"
                            :url="incomeUrl"></lineChart>
                    </div>
                </div>
                <div class="echarts-item">
                    <lineChart
                        :color="orderColor"
                        :id="orderId"
                        :title="orderTitle"
                        :y-unit="orderYUnit"
                        :x-unit="orderXUnit"
                        :realData="orderYData"
                        :url="orderUrl"></lineChart>
                </div>
            </div>
            <div class="boss-view-bottom">
                <div class="statistics-title">普法宣传员</div>
                <div class="view-bottom-content">
                    <div class="boss-view-search">
                        <div class="search-date-filter clearfix">
                            <div class="filter-item-date">
                                <el-date-picker
                                    v-model="date"
                                    type="daterange"
                                    size="small"
                                    range-separator="—"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期">
                                </el-date-picker>
                            </div>
                            <div class="filter-item-input">
                                <el-input v-model="filterName" size="small" placeholder="姓名"></el-input>
                            </div>
                            <div class="filter-item-input">
                                <el-input v-model="superiorName" size="small" placeholder="上级姓名"></el-input>
                            </div>
                            <el-button type="warning" size="small" @click="searchBtnClick">查询</el-button>
                        </div>
                    </div>
                    <div class="boss-view-result">
                        <el-table
                            class="align-center"
                            ref="multipleTable"
                            :data="searchResultData"
                            tooltip-effect="dark"
                            style="width: 100%"
                            @row-click="handleSelectionChange">
                            <el-table-column
                                label="选择"
                                width="80">
                                <template slot-scope="scope">
                                    <el-radio class="radio" v-model="tableRadio" :label="scope.row.id"
                                              @change.native="getCurrentRow(scope.row.id)">&nbsp;
                                    </el-radio>
                                </template>
                            </el-table-column>
                            <el-table-column
                                prop="rank"
                                label="排序"
                                width="80">
                            </el-table-column>
                            <el-table-column
                                prop="name"
                                label="姓名"
                                width="120">
                            </el-table-column>
                            <el-table-column
                                prop="employeeId"
                                label="工号"
                                width="120">
                            </el-table-column>
                            <el-table-column
                                prop="phone"
                                label="手机号"
                                width="140">
                            </el-table-column>
                            <el-table-column
                                prop="degree"
                                label="级别">
                            </el-table-column>
                            <el-table-column
                                prop="superiorName"
                                width="120"
                                label="上级姓名">
                            </el-table-column>
                            <el-table-column
                                prop="subordinateNum"
                                width="120"
                                label="下级人数">
                                <template slot-scope="scope">
                                    <span class="subordinate-num">{{scope.row.subordinateNum}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                prop="orderNum"
                                label="成单数">
                            </el-table-column>
                            <el-table-column
                                prop="income"
                                label="收入">
                            </el-table-column>
                            <el-table-column
                                prop="complianceRate"
                                label="达标率">
                            </el-table-column>
                            <el-table-column
                                label="状态"
                                width="120">
                                <template slot-scope="scope">
                                    <span :class="[scope.row.status != 0 ? 'text-stoped' : '']">{{ statusMap[scope.row.status] }}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-pagination
                            layout="prev, pager, next"
                            @current-change="paginationClick"
                            :pageSize="pageOpt.pageSize"
                            :current-page="pageOpt.nowPage"
                            :total="pageOpt.totalPage">
                        </el-pagination>
                    </div>
                    <div class="footer-button">
                        <el-button type="warning" size="small" @click="stopBusiClick">暂停业务</el-button>
                    </div>
                    <!--暂停业务提示框-->
                    <el-dialog
                        title=""
                        :visible.sync="stopDialogVisible"
                        width="30%"
                        center>
                        <slot name="title">
                            <div class="dialog-title-slot confirm-title">确定停止{{currentRow !== null ? currentRow.name :
                                ''}}的业务吗？
                            </div>
                        </slot>
                        <span slot="footer" class="dialog-footer">
              <el-button type="warning" size="small" @click="stopDialogVisible = false">确 定</el-button>
              <el-button type="waiting" size="small" @click="stopDialogVisible = false">取 消</el-button>
            </span>
                    </el-dialog>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import lineChart from './lineChart.vue'

    export default {
        name: "boss-view",
        mounted() {
            this.getBaseInfo();
        },
        components: {
            lineChart
        },
        data() {
            return {
                stopDialogVisible: false,
                totalPeople: '26,238',
                involvedNum: '2,226,238',
                incomeColor: ['#b2d8ed', '#0778ca'],
                incomeId: "income",
                incomeTitle: "收入",
                incomeUrl: "",
                incomeYUnit: "元",
                incomeXUnit: "月",
                incomeYData:[],
                orderColor: ['#f7e2bc', 'orange'],
                orderId: "order",
                orderTitle: "成单",
                orderUrl: "",
                orderYUnit: "元",
                orderXUnit: "月",
                orderYData:[],
                date: '',
                filterName: '',
                superiorName: '',
                statusMap: {
                    0: '正常',
                    1: '暂停'
                },
                multipleSelection: [],
                pageOpt: {
                    pageSize: 5,
                    nowPage: 1,
                    totalPage: 5
                },
                totalData: [],
                searchResultData: [],
                tableRadio: '',
                currentRow: null
            }
        },
        methods: {
            getBaseInfo(val) {
                this.$axios.post('/api/getStatisticsData',{
                    type: 0
                }).then((res) =>{ 
                    //debugger;
                    this.totalPeople=res.data.totalPeople;
                    this.involvedNum = res.data.involvedNum;
                })
            },
            handleSelectionChange(val) {
                this.currentRow = val;
                this.tableRadio = val.id;
            },
            getCurrentRow(val) {

            },
            paginationClick(page) {
                this.pageOpt.nowPage = page;
                this.searchResultData = this.totalData.slice((this.pageOpt.nowPage - 1) * this.pageOpt.pageSize, this.pageOpt.nowPage * this.pageOpt.pageSize)
            },
            stopBusiClick() {
                this.stopDialogVisible = true;
            },
            searchBtnClick() {
                this.totalData = [
                    {
                        id: 1,
                        rank: 1,
                        name: '王小虎',
                        employeeId: "C202",
                        phone: 13811111111,
                        degree: 'C',
                        superiorName: '刘全',
                        subordinateNum: '3',
                        orderNum: '201',
                        income: '2000',
                        complianceRate: '95%',
                        status: 0
                    }, {
                        id: 2,
                        rank: 1,
                        name: '王小虎',
                        employeeId: "C202",
                        phone: 13811111111,
                        degree: 'C',
                        superiorName: '刘全',
                        subordinateNum: '3',
                        orderNum: '201',
                        income: '2000',
                        complianceRate: '95%',
                        status: 0
                    }, {
                        id: 3,
                        rank: 1,
                        name: '王小虎',
                        employeeId: "C202",
                        phone: 13811111111,
                        degree: 'C',
                        superiorName: '刘全',
                        subordinateNum: '3',
                        orderNum: '201',
                        income: '2000',
                        complianceRate: '95%',
                        status: 0
                    }, {
                        id: 4,
                        rank: 1,
                        name: '王小虎',
                        employeeId: "C202",
                        phone: 13811111111,
                        degree: 'C',
                        superiorName: '刘全',
                        subordinateNum: '3',
                        orderNum: '201',
                        income: '2000',
                        complianceRate: '95%',
                        status: 0
                    }, {
                        id: 5,
                        rank: 1,
                        name: '王小虎',
                        employeeId: "C202",
                        phone: 13811111111,
                        degree: 'C',
                        superiorName: '刘全',
                        subordinateNum: '3',
                        orderNum: '201',
                        income: '2000',
                        complianceRate: '95%',
                        status: 1
                    }, {
                        id: 6,
                        rank: 1,
                        name: '王小虎',
                        employeeId: "C202",
                        phone: 13811111111,
                        degree: 'C',
                        superiorName: '刘全',
                        subordinateNum: '3',
                        orderNum: '201',
                        income: '2000',
                        complianceRate: '95%',
                        status: 1
                    }
                ];
                this.pageOpt.totalPage = this.totalData.length
                this.searchResultData = this.totalData.slice((this.pageOpt.nowPage - 1) * this.pageOpt.pageSize, this.pageOpt.nowPage * this.pageOpt.pageSize)
            }
        }
    }
</script>
<style>
    .boss-view-top {
        width: 100%;
        height: 200px;
    }

    .statistics-title {
        margin-bottom: 15px;
        font-size: 16px;
        line-height: 40px;
        border-bottom: 1px solid #ccc;
        color: #000;
    }

    .view-card {
        float: left;
        width: 20%;
        height: 100%;
    }

    .echarts-item {
        float: left;
        width: 40%;
        height: 100%;
        padding-left: 15px;
    }

    .echarts-item > div {
        width: 100%;
        height: 100%;
        padding: 20px;
        background: #fff;
        -webkit-border-radius: 4px;
        -moz-border-radius: 4px;
        border-radius: 4px;
    }

    .view-card-item {
        position: relative;
        width: 100%;
        height: calc((100% - 15px) / 2);
        padding: 16px 20px 0px;
        background: #fff;
        -webkit-border-radius: 4px;
        -moz-border-radius: 4px;
        border-radius: 4px;
    }

    .view-card-item:first-child {
        margin-bottom: 15px;
    }

    .card-top {
        width: 100%;
        padding: 2px 0 8px;
        color: #ababab;
        line-height: 20px;
    }

    .card-bottom {
        padding: 8px 0 0;
        font-size: 22px;
        line-height: 26px;
    }

    .card-icon-img {
        position: absolute;
        bottom: 20px;
        right: 20px;
        width: 50px;
        height: 50px;
        background: #99a9bf;
    }

    .boss-view-bottom {
        padding-top: 15px;
        background: #fff;
        border-radius: 4px;
        margin-top: 15px;
    }

    .boss-view-bottom .statistics-title {
        padding-left: 15px;
        padding-bottom: 5px;
    }

    .view-bottom-content {
        padding: 0 15px 15px;
    }

    .filter-item-date {
        float: left;
        width: 230px;
        margin-right: 15px;
    }

    .filter-item-date .el-date-editor {
        width: 100%;
    }

    .filter-item-input {
        float: left;
        width: 150px;
        margin-right: 15px;
    }

    .boss-view-result {
        padding: 15px 0 0px;
    }

    .el-table thead th {
        background: #eef4f5;
    }

    .el-table th, .el-table td {
        padding: 8px 12px;
        font-size: 12px;
    }

    .text-stoped {
        color: #D0021B;
    }

    .boss-view-bottom .el-pagination {
        padding: 8px;
    }

    .footer-button {
        text-align: center;
    }

    .confirm-title {
        position: static;
        width: 100%;
    }

    .subordinate-num {
        color: #F5A623;
        border-bottom: 2px solid #F5A623;
        padding: 0 5px 2px;
    }
</style>

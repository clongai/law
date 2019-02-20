<template>
  <div class="promoter-content">
    <div class="statistics-title">普法宣传员</div>
    <div class="promoter-search">
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
    <div class="promoter-result">
      <el-table
              class="align-center"
              ref="promoterTable"
              :data="searchResultData"
              tooltip-effect="dark"
              style="width: 100%"
              @row-click="handleSelectionChange">
        <el-table-column
                type="selection"
                label="选择"
                width="80">
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
      <el-button type="warning" size="small" @click="stopBusiClick">暂停</el-button>
      <el-button type="waiting" size="small" @click="delBusiClick">删除</el-button>
    </div>
    <!--暂停提示框-->
    <el-dialog
            title=""
            :visible.sync="stopDialogVisible"
            width="30%"
            center>
      <slot name="title">
        <div class="dialog-title-slot confirm-title">确定停止成单人的业务吗？</div>
      </slot>
      <span slot="footer" class="dialog-footer">
              <el-button type="warning" size="small" @click="stopDialogVisible = false">确 定</el-button>
              <el-button type="waiting" size="small" @click="stopDialogVisible = false">取 消</el-button>
            </span>
    </el-dialog>
    <!--删除提示框-->
    <el-dialog
            title=""
            :visible.sync="delDialogVisible"
            width="30%"
            center>
      <slot name="title">
        <div class="dialog-title-slot confirm-title">确定删除成单人的业务吗？</div>
      </slot>
      <span slot="footer" class="dialog-footer">
              <el-button type="warning" size="small" @click="delDialogVisible = false">确 定</el-button>
              <el-button type="waiting" size="small" @click="delDialogVisible = false">取 消</el-button>
            </span>
    </el-dialog>
    <!--选择成单人提示框-->
    <el-dialog
            title=""
            :visible.sync="operateDialogVisible"
            width="30%"
            center>
      <slot name="title">
        <div class="dialog-title-slot confirm-title">请先选择成单人！</div>
      </slot>
      <span slot="footer" class="dialog-footer">
              <el-button type="warning" size="small" @click="operateDialogVisible = false">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    name: "promoterOperate",
    mounted () {

    },
    data () {
      return {
        stopDialogVisible: false,
        delDialogVisible: false,
        operateDialogVisible: false,
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
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            if (this.multipleSelection.indexOf(row.id) === -1) {
              this.multipleSelection.push(row.id)
            } else {
              let ind = this.multipleSelection.indexOf(row.id);
              this.multipleSelection.splice(ind, ind+1)
            }
            this.$refs.promoterTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.promoterTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.toggleSelection([val]);
        // this.multipleSelection = val.id;
      },
      paginationClick (page) {
        this.pageOpt.nowPage = page;
        this.searchResultData = this.totalData.slice((this.pageOpt.nowPage - 1) * this.pageOpt.pageSize, this.pageOpt.nowPage * this.pageOpt.pageSize)
      },
      stopBusiClick () {
        this.multipleSelection.length !== 0 ? (this.stopDialogVisible = true) : (this.operateDialogVisible = true);
      },
      delBusiClick () {
        this.multipleSelection.length !== 0 ? (this.delDialogVisible = true) : (this.operateDialogVisible = true);
      },
      searchBtnClick () {
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
        this.pageOpt.totalPage = this.totalData.length;
        this.searchResultData = this.totalData.slice((this.pageOpt.nowPage - 1) * this.pageOpt.pageSize, this.pageOpt.nowPage * this.pageOpt.pageSize)
      }
    }
  }
</script>
<style>
  .promoter-content {
    padding: 15px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    background: #fff;
  }
  .statistics-title {
    margin-bottom: 15px;
    padding-left: 15px;
    padding-bottom: 5px;
    font-size: 16px;
    line-height: 40px;
    border-bottom: 1px solid #ccc;
    color: #000;
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
  .promoter-result {
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
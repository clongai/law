<template>
    <div>
        <div class="container">
            <el-card class="box-card" shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>基础信息</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content">开始时间</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content">结束时间</div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content">地点</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.startTime}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.endTime}}</div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content bg-purple">&nbsp;{{order.place}}</div>
                    </el-col>
                </el-row>
            </el-card>

            <el-card class="box-card" shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>人物信息</span>
                </div>
                <el-row :gutter="34">
                    <el-col :span="3">
                        <div class="grid-content" align="center">描述</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content" align="center">姓名</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content" align="center">年龄</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content" align="center">手机</div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content" align="center">身份证</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content" align="center">座机号</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content" align="center">邮箱</div>
                    </el-col>
                </el-row>
                <el-row :gutter="34" v-for="(item,index) in person1">
                    <el-col :span="3">
                        <div class="grid-content ">当事人{{index+1}}</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple">{{item.personName}}</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple">{{item.personAge}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.personPhone}}</div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">&nbsp;{{item.personCardNo}}</div>
                    </el-col>
                    <!-- <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.landline}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.personEmail}}</div>
                    </el-col> -->
                </el-row>
                <el-row :gutter="34" v-for="(item,index) in person2">
                    <el-col :span="3">
                        <div class="grid-content ">对方当事人{{index+1}}</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple">{{item.personName}}</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple">&nbsp;{{item.personAge}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.personPhone}}</div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">&nbsp;{{item.personCardNo}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">&nbsp;{{item.landline}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">&nbsp;{{item.personEmail}}</div>
                    </el-col>
                    
                </el-row>
                <el-row :gutter="34" v-for="(item,index) in person3">
                    <el-col :span="3">
                        <div class="grid-content ">关系人{{index+1}}</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple">{{item.personName}}</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple">&nbsp;{{item.personAge}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.personPhone}}</div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple">&nbsp;{{item.personCardNo}}</div>
                    </el-col>
                    <!-- <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.landline}}</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple">{{item.personEmail}}</div>
                    </el-col> -->
                </el-row>
            </el-card>

            <el-card class="box-card" shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>描述信息</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content">案件详情</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">&nbsp;{{order.happen}}</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content">诉求</div>
                    </el-col>
                </el-row>
                <el-row :gutter="24">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">&nbsp;{{order.appeal}}</div>
                    </el-col>
                </el-row>
            </el-card>

            <el-card class="box-card" shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>证据</span>
                </div>
                <div v-for="item of photos" class="card_img">
                    <img v-if="item != ''" v-bind:src="item" class="card_image"/>
                </div>
                <div v-for="item of videos" class="card_img">
                    <video v-if="item != ''" v-bind:src="item" controls="controls"/>
                </div>
            </el-card>
            <!--success-->
            <el-card
                v-if="order.status == '1'||order.status == '2'||order.status == '4'||order.status == '5'||order.status == '8'||order.status == '9' ||order.status == '13'"
                class="box-card" shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>处理信息</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content">面谈日期</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content">联系人</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content">联系电话</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content">地址</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.acceptStartTime1}}&nbsp;--&nbsp;{{order.acceptEndTime1}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.acceptContracts}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.acceptPhone}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.acceptAddress}}</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content">补充信息</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">&nbsp;{{order.acceptRemark}}</div>
                    </el-col>
                </el-row>
                <el-row v-if="order.feedbackOpinion != '' && order.feedbackOpinion != null" :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content">专家反馈信息</div>
                    </el-col>
                </el-row>
                <el-row v-if="order.feedbackOpinion != '' && order.feedbackOpinion != null" :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">&nbsp;{{order.feedbackOpinion}}</div>
                    </el-col>
                </el-row>
                 <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content">法务处理意见</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">&nbsp;{{order.analysisResult}}</div>
                    </el-col>
                </el-row>
                 <el-row :gutter="20">
                    <el-col :span="6">
                        <div v-if="order.termsType == 'A'" class="grid-content" style="color:blue;" >提供法律文书，当事人自行处理报价</div>
                        <div v-else class="grid-content">提供法律文书，当事人自行处理报价</div>
                    </el-col>
                    <el-col :span="6">
                        <div v-if="order.termsType == 'B'" class="grid-content" style="color:blue;">提供法律文书，全程跟踪指导报价</div>
                        <div v-else class="grid-content">提供法律文书，全程跟踪指导报价</div>
                    </el-col>
                    <el-col :span="6">
                        <div v-if="order.termsType == 'C'" class="grid-content" style="color:blue;" >与山东聚青签署委托代理协议报价</div>
                        <div v-else class="grid-content">与山东聚青签署委托代理协议报价</div>
                    </el-col>
                    
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div v-if="order.termsType == 'A'" style="color:blue;" class="grid-content bg-purple">&nbsp;{{order.quoteA}}</div>
                        <div v-else class="grid-content bg-purple">&nbsp;{{order.quoteA}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div v-if="order.termsType == 'B'" style="color:blue;" class="grid-content bg-purple">&nbsp;{{order.quoteB}}</div>
                        <div v-else class="grid-content bg-purple">&nbsp;{{order.quoteB}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div v-if="order.termsType == 'C'" style="color:blue;" class="grid-content bg-purple">&nbsp;{{order.quoteC}}</div>
                        <div v-else class="grid-content bg-purple">&nbsp;{{order.quoteC}}</div>
                    </el-col>
                    
                </el-row>
                
            </el-card>

            
            <!--fail-->
            <el-card v-if="order.status == '3'||order.status == '6'||order.status == '10'||order.status == '14'||order.status == '17'||order.status == '18'" class="box-card"
                     shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>处理信息</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content">原因</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="grid-content bg-purple">&nbsp;{{order.refuseReason}}</div>
                    </el-col>
                </el-row>
            </el-card>
            <el-card v-if="order.status == '12'" class="box-card"
                     shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>电话预约时间点</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content">开始时间</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content">结束时间</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.customStartTime}}</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">&nbsp;{{order.customEndTime}}</div>
                    </el-col>
                </el-row>
            </el-card>
            <el-card v-if="order.status == '5' && orginazation == '2' " class="box-card" shadow="never">
                <div slot="header" class="clearfix card_header">
                    <span>上传法律文书(支持pdf、docx)</span>
                </div>
                <el-card v-for="(item,index) in fileByOrderId" >
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div class="grid-content bg-purple">{{item.fileName}}</div>
                    </el-col>
                    <el-col :span="6">
                        <el-button v-bind:attr="item.fileId"  @click="downloadFile">下载</el-button>
                    </el-col>
                </el-row>
                </el-card>
                <el-row :gutter="20">
                    <div class="">选择文件
                                <input class="grid-content bg-purple" id="file" type="file" name="file" accept="" @change="uploadFile"/>
                    </div> 
                </el-row>
            </el-card>


            <!--法务部人员操作按钮-->
            <el-row v-if="(order.status == '2' ||order.status == '15' )&& orginazation == '2'" type="flex" justify="space-around">
                <el-button type="warning" @click="lawWorkAccept">接受</el-button>
                <el-button type="danger" @click="refuse">拒绝</el-button>
                <!-- <el-button type="danger" @click="pass2expert">专转家团队</el-button> -->
            </el-row>
            
            <!--市场部人员进行电话预约操作按钮-->
            <el-row v-else-if="(order.status == '0' || order.status == '16' ) && orginazation == '1'" type="flex" justify="space-around">
                <el-button type="warning" @click="phoneAppointmentAccept">接受电话预约</el-button>
                <el-button type="danger" @click="refuse">拒绝</el-button>
            </el-row>
            <!--市场部人员进行电话沟通之后提交操作按钮-->
            <el-row v-else-if="order.status == '12'  && orginazation == '1'" type="flex" justify="space-around">
                <el-button type="warning" @click="accept">接受</el-button>
                <el-button type="danger" @click="refuse">拒绝</el-button>
            </el-row>
            <!--市场部人员输入代理金额-->
            <el-row v-else-if="order.status == '13'  && orginazation == '1'" type="flex" justify="space-around">
                <el-button type="warning" @click="inputAgentMoneyAccept">接受</el-button>
                <el-button type="danger" @click="refuse">拒绝</el-button>
            </el-row>
            <!--市场部人员&专家团队操作按钮-->
             <el-row v-else-if="order.status == '7' && orginazation == '3'" type="flex" justify="space-around">
                <!-- <el-button type="warning" @click="accept">接受</el-button>
                <el-button type="danger" @click="refuse">拒绝</el-button> -->
                <el-button type="warning" @click="feedbackOpinionVisible = true">反馈市场部</el-button>
            </el-row>

            <!-- <el-row v-else-if="order.status == '0' || order.status == '7'" type="flex" justify="space-around">
                <el-button type="warning" @click="accept">接受</el-button>
                <el-button type="danger" @click="refuse">拒绝</el-button>
            </el-row> -->
        </div>

        <el-dialog title="确定接受此订单" :visible.sync="phoneAppointmentVisible" width="50%">
            <el-form ref="phoneAppointmentForm" :model="phoneAppointmentForm" :rules="rules" label-width="100px">
                <!-- <el-form-item label="编号">
                    <el-input v-model="phoneAppointmentForm.orderId" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="预约时间一">
                    <el-date-picker type="datetime" placeholder="开始日期" v-model="phoneAppointmentForm.phoneStartTime1"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                    <el-date-picker type="datetime" placeholder="结束日期" v-model="phoneAppointmentForm.phoneEndTime1"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="预约时间二">
                    <el-date-picker type="datetime" placeholder="开始日期" v-model="phoneAppointmentForm.phoneStartTime2"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                    <el-date-picker type="datetime" placeholder="结束日期" v-model="phoneAppointmentForm.phoneEndTime2"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="预约时间三">
                    <el-date-picker type="datetime" placeholder="开始日期" v-model="phoneAppointmentForm.phoneStartTime3"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                    <el-date-picker type="datetime" placeholder="结束日期" v-model="phoneAppointmentForm.phoneEndTime3"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="联系人">
                    <el-input v-model="phoneAppointmentForm.acceptContracts"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="phoneAppointmentForm.acceptPhone"></el-input>
                </el-form-item> -->
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="phoneAppointmentVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAccept('phoneAppointmentForm')">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="输入代理金额" :visible.sync="inputAgentMoneyVisible" width="50%">
            <el-form ref="inputAgentMoneyForm" :model="inputAgentMoneyForm" :rules="rules" label-width="200px">
                <el-form-item label="编号">
                    <el-input v-model="inputAgentMoneyForm.orderId" :disabled="true"></el-input>
                </el-form-item>
                <!-- <el-form-item  prop="agentMoney" label="代理金额">
                    <el-input-number v-model="inputAgentMoneyForm.agentMoney" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item> -->
                <el-form-item  prop="quoteA" label="A:提供法律文书，当事人自行处理代理金额">
                    <el-input-number v-model="inputAgentMoneyForm.quoteA" :precision="2" :step="0.01"  :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                <el-form-item  prop="quoteB" label="B:提供法律文书，全程跟踪指导代理金额">
                    <el-input-number v-model="inputAgentMoneyForm.quoteB" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                <el-form-item  prop="quoteC" label="C:与山东聚青签署委托代理协议代理金额">
                    <el-input-number v-model="inputAgentMoneyForm.quoteC" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="inputAgentMoneyVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAccept('inputAgentMoneyForm')">确 定</el-button>
            </span>
        </el-dialog>


        <el-dialog title="接受报单需补充下列面谈信息" :visible.sync="acceptVisible" width="50%">
            <el-form ref="acceptform" :model="acceptform" :rules="rules" label-width="90px">
                <el-form-item label="编号">
                    <el-input v-model="acceptform.orderId" :disabled="true"></el-input>
                </el-form-item>
                <!-- <el-form-item v-if="orginazation == '1'" prop="involvingMoney" label="涉案金额">
                    <el-input-number v-model="acceptform.involvingMoney" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                <el-form-item v-if="orginazation == '1'" prop="caseBaseLevel" label="案由大类">
                   
                    <el-autocomplete v-model="acceptform.caseBaseLevel" :fetch-suggestions="querySearchBaseCase" :debounce=0  @select="baseCaseSelected" style="width: 100%;"></el-autocomplete>
                </el-form-item>
                <el-form-item v-if="orginazation == '1'" prop="caseSubLevel" label="案由小类">
                   
                    <el-autocomplete v-model="acceptform.caseSubLevel" :fetch-suggestions="querySearchSubCaseAsync" :debounce=0  @select="subCaseSelected" style="width: 100%;"></el-autocomplete>
                </el-form-item> -->
                <!-- <el-form-item label="日期">
                    <el-date-picker type="datetime" placeholder="选择日期" v-model="acceptform.acceptDate"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%;"></el-date-picker>
                </el-form-item> -->
                <el-form-item  label="面谈时间">
                    <el-date-picker prop="acceptDate" type="datetime" placeholder="开始日期" v-model="acceptform.acceptStartTime1"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                    <el-date-picker type="datetime" placeholder="结束日期" v-model="acceptform.acceptEndTime1"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                </el-form-item>
                <!-- <el-form-item label="面谈时间二">
                    <el-date-picker type="datetime" placeholder="开始日期" v-model="acceptform.acceptStartTime2"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                    <el-date-picker type="datetime" placeholder="结束日期" v-model="acceptform.acceptEndTime2"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="面谈时间三">
                    <el-date-picker type="datetime" placeholder="开始日期" v-model="acceptform.acceptStartTime3"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker>
                    <el-date-picker type="datetime" placeholder="结束日期" v-model="acceptform.acceptEndTime3"
                                    value-format="yyyy-MM-dd HH:mm:ss" style="width: 45%;"></el-date-picker> 
                </el-form-item>-->
                <el-form-item prop="acceptContracts" label="联系人">
                    <el-input  v-model="acceptform.acceptContracts"></el-input>
                </el-form-item>
                <el-form-item prop="acceptPhone" label="联系电话">
                    <el-input  v-model="acceptform.acceptPhone"></el-input>
                </el-form-item>
                <el-form-item prop="acceptAddress" label="地址">
                    <el-input  v-model="acceptform.acceptAddress"></el-input>
                </el-form-item>
                <el-form-item label="补充信息">
                    <el-input v-model="acceptform.acceptRemark"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="acceptVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAccept('acceptform')">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="请选择不接受理由" :visible.sync="refuseVisible" width="50%">
            <el-form ref="refuseform" :model="refuseform" label-width="50px">
                <el-form-item label="编号">
                    <el-input v-model="acceptform.orderId" :disabled="true"></el-input>
                </el-form-item>
                <div class="box-card">
                    <el-radio v-if="order.status!=12"  v-model="refuseform.refuseReasonLabel"  label="0" border>完善资料</el-radio>
                    <!-- <el-radio v-model="refuseform.refuseReason" label="重复录入" border>重复录入</el-radio> -->
                    <el-radio v-model="refuseform.refuseReasonLabel" label="1" border>拒单原因</el-radio>
                </div>
                <el-input type="textarea" v-model="refuseform.refuseReason"></el-input>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="refuseVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRefuse">确 定</el-button>
            </span>
        </el-dialog>
        <!-- <el-dialog title="确认转交给专家团队" :visible.sync="pass2expertVisible" width="50%">
            <span slot="footer" class="dialog-footer">
                <el-button @click="pass2expertVisible = false">取 消</el-button>
                <el-button type="primary" @click="savePass">确 定</el-button>
            </span>
        </el-dialog> -->


        <el-dialog title="请补充面谈信息" :visible.sync="lawWorkAcceptVisible"  width="50%">
            <el-form ref="lawWorkAcceptForm" :model="lawWorkAcceptForm" :rules="rules" label-width="240px">
                <el-form-item label="编号">
                    <el-input v-model="lawWorkAcceptForm.orderId" :disabled="true"></el-input>
                </el-form-item>
                <!-- <div class="box-card">
                    <el-form-item label="预约面谈时间一">
                    <el-radio  v-model="lawWorkAcceptForm.faceSelected"  label="0" border>
                        {{order.acceptStartTime1}}----{{order.acceptEndTime1}}
                    </el-radio>
                     </el-form-item>
                     <el-form-item label="预约面谈时间二">
                    <el-radio v-model="lawWorkAcceptForm.faceSelected" label="1" border>
                         {{order.acceptStartTime2}}----{{order.acceptEndTime2}}
                    </el-radio>
                     <br>
                    </el-form-item>
                      <el-form-item label="预约面谈时间三">
                     <el-radio v-model="lawWorkAcceptForm.faceSelected" label="2" border>
                         {{order.acceptStartTime3}}----{{order.acceptEndTime3}}
                    </el-radio>
                    </el-form-item>
                </div> -->
                <el-form-item v-if="orginazation == '2'" prop="involvingMoney" label="涉案金额">
                    <el-input-number v-model="lawWorkAcceptForm.involvingMoney" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                <el-form-item v-if="orginazation == '2'" prop="caseBaseLevel" label="案由大类">

                    <el-autocomplete v-model="lawWorkAcceptForm.caseBaseLevel" :fetch-suggestions="querySearchBaseCase" :debounce=0  @select="baseCaseSelected" style="width: 100%;"></el-autocomplete>
                </el-form-item>
                <el-form-item v-if="orginazation == '2'" prop="caseSubLevel" label="案由小类">  
                    <el-autocomplete v-model="lawWorkAcceptForm.caseSubLevel" :fetch-suggestions="querySearchSubCaseAsync" :debounce=0  @select="subCaseSelected" style="width: 100%;"></el-autocomplete>
                </el-form-item> 
              <el-form-item label="分析结果">
                     <el-input type="textarea" v-model="lawWorkAcceptForm.analysisResult"></el-input>
                </el-form-item>
                <el-form-item v-if="orginazation == '2'" prop="quoteA" label="A:提供法律文书，当事人自行处理报价">
                    <el-input-number v-model="lawWorkAcceptForm.quoteA" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                <el-form-item v-if="orginazation == '2'" prop="quoteB" label="B:提供法律文书，全程跟踪指导报价">
                    <el-input-number v-model="lawWorkAcceptForm.quoteB" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
                <el-form-item v-if="orginazation == '2'" prop="quoteC" label="C:与山东聚青签署委托代理协议报价">
                    <el-input-number v-model="lawWorkAcceptForm.quoteC" :precision="2" :step="0.01" :max="99999999999999" style="width: 100%"></el-input-number>
                </el-form-item>
               <el-form-item label="材料清单">
                     <el-input type="textarea" v-model="lawWorkAcceptForm.fileList"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="lawWorkAcceptVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAccept('lawWorkAcceptForm')">确 定</el-button>
            </span>
        </el-dialog>


        <el-dialog title="请补充反馈信息" :visible.sync="feedbackOpinionVisible"  width="50%">
            <el-form ref="feedbackOpinionForm" :model="feedbackOpinionForm" :rules="rules" label-width="120px">              
               <el-form-item label="反馈信息">
                     <el-input type="textarea" v-model="feedbackOpinionForm.feedbackOpinion"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="feedbackOpinionVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAccept('feedbackOpinionForm')">确 定</el-button>
            </span>
        </el-dialog>
      

    </div>



</template>

<script>
    export default {
        data: function () {
            return {
                baseCaseList: [],
                subCaseList: [],
                state4: '',
                timeout:  null,
                orginazation: '',
                orderId: '',
                order: {},
                person1: [],
                person2: [],
                person3: [],
                photos: [],
                videos: [],
                files: [],
                acceptVisible: false,
                phoneAppointmentVisible: false,
                inputAgentMoneyVisible: false,
                refuseVisible: false,
                lawWorkAcceptVisible: false,
                feedbackOpinionVisible:false,
                pass2expertVisible: false,
                acceptform: {
                    orderId: '',
                    acceptDate: '',
                    acceptStartTime1: '',
                    acceptEndTime1: '',
                    acceptStartTime2: '',
                    acceptEndTime2: '',
                    acceptStartTime3: '',
                    acceptEndTime3: '',
                    acceptContracts: '',
                    acceptPhone: '',
                    acceptAddress: '',
                    acceptRemark: '',
                    involvingMoney: '',
                    caseBaseLevel: '',
                    caseSubLevel: ''
                },
                phoneAppointmentForm:{
                    orderId: '',
                    phoneEndTime1: '',
                    phoneStartTime1: '',
                    phoneEndTime2: '',
                    phoneStartTime2: '',
                    phoneEndTime3: '',
                    phoneStartTime3: '',
                    acceptPhone: '',
                    acceptContracts: ''
                },
                inputAgentMoneyForm:{
                     orderId: '',
                     agentMoney:'',
                     quoteA:'',
                     quoteB:'',
                     quoteC:''
                },
                refuseform: {
                    orderId: '',
                    refuseReasonLabel:'',
                    refuseReason: ''
                },
                lawWorkAcceptForm :{
                    orderId: '',
                    fileList: '',
                    faceSelected: '',
                    involvingMoney: '',
                    caseBaseLevel: '',
                    caseSubLevel: '',
                    analysisResult:'',
                    quoteA:'',
                    quoteB:'',
                    quoteC:''
                },
                feedbackOpinionForm:{
                    orderId: '',
                    feedbackOpinion:''
                },
                rules: {
                    involvingMoney :[{required: true, message: '请输入涉案金额', trigger: 'change'}],
                    caseBaseLevel :[{required: true, message: '请选择案由大类', trigger: 'change'}],
                    caseSubLevel :[{required: true, message: '请选择案由小类', trigger: 'change'}],
                    acceptContracts :[{required: true, message: '联系人'}],
                    acceptPhone :[{required: true, message: '联系电话'}],
                    acceptAddress :[{required: true, message: '联系地址'}],
                    acceptDate :[{required: true, message: '面谈时间'}],
                    quoteA :[{required: true, message: '请输入A报价金额', trigger: 'change'}],
                    quoteB :[{required: true, message: '请输入B报价金额', trigger: 'change'}],
                    quoteC :[{required: true, message: '请输入C报价金额', trigger: 'change'}],
                    analysisResult :[{required: true, message: '请输入分析结果', trigger: 'change'}]
                },
                fileByOrderId:''
            }
        },
        created() {
            
            this.orderId = this.$route.query.orderId
            this.acceptform.orderId = this.orderId
            this.refuseform.orderId = this.orderId
            this.phoneAppointmentForm.orderId = this.orderId
            this.inputAgentMoneyForm.orderId = this.orderId
            this.lawWorkAcceptForm.orderId = this.orderId;
            this.feedbackOpinionForm.orderId = this.orderId;
            
            this.orginazation = localStorage.getItem('ms_userOrginazation')
            this.getData();
            this.getFileByOrderId();
        },
        methods: {
            getData() {
                this.$axios.post('/api/getLawOrder', {
                    orderId: this.orderId,
                    orginazation: this.orginazation
                }).then((res) => {
                    
                    this.order = res.data.orderPO
                    this.feedbackOpinionForm.feedbackOpinion = this.order.feedbackOpinion;
                    this.person1 = res.data.lawPerson1
                    this.person2 = res.data.lawPerson2
                    this.person3 = res.data.lawPerson3
                    this.photos = this.order.photos.split(",")
                    this.videos = this.order.videos.split(",")
                })
            },
            accept() {
                this.acceptVisible = true
            },
            phoneAppointmentAccept(){
                //电话预约弹窗显示
                this.phoneAppointmentVisible = true
            },
            phoneAppointmentRefuse(){
                //电话预约弹窗关闭
                this.phoneAppointmentVisible = false
            },
            lawWorkAccept(){
                this.lawWorkAcceptVisible = true;
            },
            lawWorkRefuse(){
                this.lawWorkAcceptVisible = false;
            },
             inputAgentMoneyAccept(){
                //电话预约弹窗显示
                this. inputAgentMoneyVisible = true
            },
             inputAgentMoneyRefuse(){
                //电话预约弹窗关闭
                this. inputAgentMoneyVisible = false
            },
            refuse() {
                this.refuseVisible = true
            },  
            pass2expert() {
                this.pass2expertVisible = true
            },
            saveAccept(acceptform) {
                var lawOrder = this.acceptform;
                if(acceptform=="acceptform"){
                    //数据校验
                    if(this.isBlank(lawOrder.acceptStartTime1)||this.isBlank(lawOrder.acceptEndTime1)){
                        alert("面谈时间不能为空");
                        return;
                    }
                    if(new Date(lawOrder.acceptStartTime1) >= new Date(lawOrder.acceptEndTime1)){
                        alert("起始时间不能大于结束时间");
                        return;
                    }
                    if(!this.isBlank(lawOrder.acceptPhone)){
                        if (!/(^1[3|5|8][0-9]{9}$)/.test(lawOrder.acceptPhone)) {
                            alert("请输入正确的手机号");
                            return;
                        }
                    }
                    
                }

                if(acceptform=="phoneAppointmentForm"){
                    lawOrder = this.phoneAppointmentForm;
                    //电话预约时间设置校验
                    
                    /*if(this.isBlank(lawOrder.phoneEndTime1)||this.isBlank(lawOrder.phoneEndTime2)||this.isBlank(lawOrder.phoneEndTime3)
                    ||this.isBlank(lawOrder.phoneStartTime1)||this.isBlank(lawOrder.phoneStartTime2)||this.isBlank(lawOrder.phoneStartTime3)){
                        alert("预约时间不能为空");
                        return;
                    }
                    if(new Date(lawOrder.phoneStartTime1) >= new Date(lawOrder.phoneEndTime1)
                    ||new Date(lawOrder.phoneStartTime2) >= new Date(lawOrder.phoneEndTime2)
                    ||new Date(lawOrder.phoneStartTime3) >= new Date(lawOrder.phoneEndTime3)){
                        alert("起始时间不能大于结束时间");
                        return;
                    }*/
                   
                }
                
                if(acceptform=="inputAgentMoneyForm"){
                    lawOrder = this.inputAgentMoneyForm;
                      
                      if(lawOrder.quoteA<Math.floor(((this.order.quoteA/0.2)*0.03))){
                            alert("A条款报价不能低于"+Math.floor(((this.order.quoteA/0.2)*0.03)));
                            return;
                      }
                      if(lawOrder.quoteB<Math.floor(((this.order.quoteB/0.2)*0.03))){
                            alert("B条款报价不能低于"+Math.floor(((this.order.quoteB/0.2)*0.03)));
                            return;
                      }
                      if(lawOrder.quoteC<Math.floor(((this.order.quoteC/0.2)*0.03))){
                            alert("C条款报价不能低于"+Math.floor(((this.order.quoteC/0.2)*0.03)));
                            return;
                      }
                  
                }
                
                if(acceptform=="lawWorkAcceptForm"){
                    lawOrder = this.lawWorkAcceptForm;
                }
                if(acceptform=="feedbackOpinionForm"){
                    lawOrder = this.feedbackOpinionForm;
                }
                
                this.$refs[acceptform].validate((valid) => {
                   
                    if (valid) {
                        
                        this.$axios.post('/api/accpetLawOrder', {
                            lawOrder: lawOrder,
                            orginazation: this.orginazation
                        }).then((res) => {
                            this.$alert('保存成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    this.acceptVisible = false
                                    this.$router.push('/handle/order');
                                }
                            });
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            saveRefuse() {
                debugger;
                this.$axios.post('/api/refuseLawOrder', {
                    lawOrder: this.refuseform,
                    orginazation: this.orginazation
                }).then((res) => {
                    this.$alert('保存成功', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.refuseVisible = false
                            this.$router.push('/handle/order');
                        }
                    });
                })
            },
            uploadFile(e){
                
                //这里是只上传文件的步骤
                // let formData = new FormData();
                // formData.append("upfile",this.fileObj);
                // 这里上传文件并且需要传更多参数的时候
                let url = "/api/uploadFileOrderId";// 上传服务器接
                let formData = new FormData();
                formData.append("file",e.target.files[0]);// 这就是给formData添加新的表单数据的形式
                formData.append("orderId",this.orderId);
                this.$axios({
                url:url,
                method:"post",
                data:formData,
                headers:{"Content-Type":"multipart/form-data"},
                }).then(res => {
                    debugger;
                if(res.status=="200"){
                    this.$alert('保存成功', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.refuseVisible = false
                            this.$router.push('/handle/order');
                        }
                    });
               
                }else{
                }
                }).catch(err =>{
                console.log(err)
                });
            },
             download (data) {
                if (!data) {
                    return
                }
                // let url = window.URL.createObjectURL(new Blob([data]))
                // let link = document.createElement('a')
                // link.style.display = 'none'
                // link.href = url
                // link.setAttribute('download', 'test.pdf')

                // document.body.appendChild(link)
                // link.click()

                const blob = new Blob([data])
                if (window.navigator.msSaveOrOpenBlob) {
                    // 兼容IE10
                    navigator.msSaveBlob(blob, "test.pdf")
                } else {
                    //  chrome/firefox
                    let aTag = document.createElement('a')
                    aTag.download = "test.pdf"
                    aTag.href = URL.createObjectURL(blob)
                    aTag.click()
                    URL.revokeObjectURL(aTag.href)
                }

            },
            savePass() {
               
                this.$axios.post('/api/passLawOrder', {
                    orderId: this.orderId,
                }).then((res) => {
                    this.$alert('保存成功', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.pass2expertVisible = false
                            this.$router.push('/handle/order');
                        }
                    });
                })
            },
            loadBaseCase() {
                this.$axios.post('/api/findAllBaseCase',{
                }).then((res) =>{
                    this.baseCaseList = res.data;
                })
            },
            getFileByOrderId(){
                 this.$axios.post('/api/getFileByOrderId',{
                    orderId: this.orderId
                }).then((res) =>{
                
                    this.fileByOrderId = res.data;
                })
            },
            downloadFile(e){
               
                //  this.$axios.post('/api/downloadFile',{
                //     fileId: 1
                // }).then((res) =>{
                //     this.download(res)
                // })
                //window.open('api/downloadFile?fileId='+e.currentTarget.getAttribute('attr'), '_blank');
                window.location.href ='/downloadFile?fileId='+e.currentTarget.getAttribute('attr');
                // this.$axios({
                //     method: 'post',
                //     url: 'api/downloadFile',
                //     data: {
                //         fileId: 1
                //     },
                //     responseType: 'arraybuffer'
                // }).then(response => {
                //     this.download(response)
                // }).catch((error) => {

                // })
            },
            querySearchBaseCase(queryString, cb) {
                var baseCases = this.baseCaseList;
                var results = queryString ? baseCases.filter(this.createStateFilter(queryString)) : baseCases;

                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(results);
                }, 3000 * Math.random());
            },
            querySearchSubCaseAsync(queryString, cb) {
                var results;
                this.$axios.post('/api/findCaseByName',{
                    caseName: queryString,
                    caseType: 2,
                    parentCase: this.acceptform.caseBaseLevel
                }).then((res) =>{
                    this.subCaseList = res.data;
                    results = res.data;
                })
                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(results);
                }, 3000 * Math.random());

            },
            createStateFilter(queryString) {
                return (state) => {
                    return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            baseCaseSelected(item) {
                this.acceptform.caseSubLevel = "";
                this.subCaseList = [];
            },
            subCaseSelected(item) {
               
                this.acceptform.caseBaseLevel = item.parent;
            },
            isBlank(val){
                if(val==""||val==null||val=="undefined" || typeof val == "undefined" || val=="null" ||val=="NULL"){
                    return true;
                }
                return false;
            }
        },
        mounted() {
            this.loadBaseCase()
        }
    }
</script>

<style scoped>
    .box-card {
        margin-bottom: 20px;
    }

    .card_img {
        width: 20%;
        float: left;
    }

    .card_image {
        width: 90%
    }

    .el-row {
        margin-bottom: 5px;
    }

    :last-child {
        margin-bottom: 0;
    }
    .el-col {
        padding-left: 10px;
    }

    /*.bg-purple {*/
    /*background: #eee;*/
    /*}*/
    .bg-purple {
        background: #f1f5f7;
        color: #a5afb9;
        font-weight: normal;
    }

    .grid-content {
        border-radius: 2px;
        min-height: 18px;
        padding: 5px;
        font-size: 12px;
    }

    .redio-div {
        border-radius: 2px;
        min-height: 18px;
        padding: 5px;
        font-size: 12px;
        float: left;
    }

    .card_header {
        font-weight: bold;
    }
</style>

<template>
    <div class="login-wrap">
        <div class="ms-title">后台管理系统</div>
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" width="40%" label-width="80px" class="demo-ruleForm">
                <el-form-item  label="用户名" prop="username">
                    <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                 <el-form-item  label="部门" prop="department">
                <el-select v-model="status"  label="部门" placeholder="部门" @change="selectDept">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
                </el-form-item>
                 <el-form-item v-show="isLevelShow" label="级别" prop="level">
                <el-select v-model="level"  label="级别" placeholder="级别">
                    <el-option
                        v-for="item in levelOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" placeholder="密码" v-model="ruleForm.password"
                             ></el-input>
                </el-form-item>
                <el-form-item label="密码确认" prop="twopassword">
                    <el-input type="password" placeholder="密码确认" v-model="ruleForm.twopassword"
                    @blur="passwordSure('ruleForm')"
                              @keyup.enter.native="passwordSure('ruleForm')"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
                </div>
                <!--<p style="font-size:12px;line-height:30px;color:#999;">Tips : 用户名和密码随便填。</p>-->
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                status: '1',
                level: '2',
                isLevelShow: false,
                levelOptions:[{
                            value: '2',
                            label: '法务'
                        }, {
                            value: '4',
                            label: '专家'
                        }, {
                            value: '5',
                            label: '顶级专家'
                        }],
                options: [{
                            value: '1',
                            label: '市场部'
                        }, {
                            value: '2',
                            label: '法务部'
                        }],
                ruleForm: {
                    username: 'admin',
                    password: '',
                    twopassword: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            submitForm(formName) {
                if(this.ruleForm.password!=this.ruleForm.twopassword){
                    alert("两次密码输入不一致，请重新输入！");
                    return;
                }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$axios.post('/api/register', {
                            userName: this.ruleForm.username,
                            userPwd: this.ruleForm.password,
                            orginazation: this.status,
                            serviceLevel: this.level
                        }).then((res) => {
                            console.log(res)
                            if (res.data.status == '1') {
                                 localStorage.setItem('ms_username', this.ruleForm.username);
                                localStorage.setItem('ms_userFunction', res.data.data.function);
                                localStorage.setItem('ms_userOrginazation', res.data.data.orginazation);
                                localStorage.setItem('ms_serviceLevel', res.data.data.serviceLevel);
                                if (res.data.function == '0') {
                                    this.$router.push('/view/');
                                } else {
                                    this.$router.push('/handle/');
                                }
                                
                            } else {
                               this.$alert(res.data.message, '提示', {
                                    confirmButtonText: '确定',
                                });
                            }
                        })
                    }
                });
            },
            selectDept(){
                
                if(this.status==2){
                    this.isLevelShow = true;
                }else{
                    this.isLevelShow = false;
                }
            },
            passwordSure(){
                if(this.ruleForm.password!=this.ruleForm.twopassword){
                    alert("两次密码输入不一致，请重新输入！");
                    return;
                }
            }
        }
    }
</script>

<style scoped>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
    }

    .ms-title {
        position: absolute;
        top: 50%;
        width: 100%;
        margin-top: -230px;
        text-align: center;
        font-size: 30px;
        color: #fff;

    }

    .ms-login {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 400px;
        height: 400px;
        margin: -150px 0 0 -190px;
        padding: 40px;
        border-radius: 5px;
        background: #fff;
    }

    .login-btn {
        text-align: center;
    }

    .login-btn button {
        width: 100%;
        height: 36px;
    }
</style>

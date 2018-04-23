<template>
    <div class="login-wrap">
        <div class="ms-title">Login</div>
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="请输入密码" v-model="ruleForm.password" @keyup.enter.native="submitForm1('ruleForm')"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm1('ruleForm')">登录</el-button>
                    <br/></br>
                    <el-button type="danger" @click="submitForm2('ruleForm')">注册</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function(){
            return {
                ruleForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
       methods: {
            submitForm1(formName) {
                const self = this;
                let params = {
                    "username" :this.ruleForm.username,
                    "password" :this.ruleForm.password,
                    "success":""
                };
                console.log(params);

                self.$refs[formName].validate((valid) => {
                    if (valid) {
                            this.$http.get("/ptEditor/user/login",{params : {
                    "username" :this.ruleForm.username,
                    "password" :this.ruleForm.password,
                    "success":""
                },}).then(
                            success =>{
                                console.log(success.data);
                                if(success.data.success==1){
                                    this.$message({
                                        type: "info",
                                        message: "登陆成功"
                                    });
                                    console.log(success.data.token);
                                    localStorage.setItem('token',success.data.token);
                                    localStorage.setItem('ms_username',this.ruleForm.username);
                                    self.$router.push('/readme');
                                } else {
                                    this.$message({
                                        type: "error",
                                        message: "登陆失败"
                                    });
                                }
                            },
                            error => {
                                console.log(error);
                                this.$message({
                                    type: "error",
                                    message: "登陆出错"
                                });
                            }
                        );
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            submitForm2(formName) {
                const self = this;

                let params = {
                    "username" :this.ruleForm.username,
                    "password" :this.ruleForm.password
                };
                //console.log(params);

                self.$refs[formName].validate((valid) => {
                    if (valid) {
                            this.$http.get("/ptEditor/user/insert1",{params : {
                                    "username" :this.ruleForm.username,
                                    "password" :this.ruleForm.password,
                                    "success":""
                                },} ).then(
                            success =>{
                                console.log(success.data);
                                if(success.data.success==1){
                                     this.$message({
                                                type: "info",
                                                message: "注册成功"
                                            });

                                }
                                 else {
                                            this.$message({
                                                type: "error",
                                                message: "注册失败"
                                            });
                                }
                            },
                            error => {
                                            console.log(error);
                                            this.$message({
                                                type: "error",
                                                message: "注册出错"
                                            });
                                        });


                    } else {
                        console.log('error submit!!');
                        return false;
                    }
              });
          },
    }
  }
</script>

<style scoped>
    .login-wrap{
        position: relative;
        width:100%;
        height:100%;
    }
    .ms-title{
        position: absolute;
        top:50%;
        width:100%;
        margin-top: -230px;
        text-align: center;
        font-size:30px;
        color: #fff;

    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:300px;
        height:160px;
        margin:-150px 0 0 -190px;
        padding:40px;
        border-radius: 5px;
        background: #fff;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:50%;
        height:30px;
    }
</style>

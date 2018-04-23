<template>
    <div>
        <el-dialog title="添加图片" :visible.sync="dialogFormVisible " @close="resetForm2" :close-on-click-modal=false>
              <el-form ref="form2" :model="form2" label-width="80px" :rules="rules">
                    <el-form-item label="图片名称" prop="ddname">
                        <el-input v-model="form2.pt_name" ></el-input>
                    </el-form-item>
                    <el-form-item label="上传者" prop="uintname">
                        <el-input v-model="form2.username"></el-input>
                    </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addItem(1,'form2')">确 定</el-button>
                <el-button @click="addItem(2,'form2')">下一步</el-button>
                <el-button @click="dialogFormVisible = false">取 消</el-button>
              </div>
        </el-dialog>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-date"></i> 添加</el-breadcrumb-item>
                <el-breadcrumb-item>添加图片</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="form-box">
            <el-form ref="form" :model="form" label-width="80px" :rules="rules">
                <el-form-item>
                    <el-table
                        :data="form.items"
                        border
                        style="width: 100%">
                        <el-table-column
                          label="已添加图片">
                          <el-table-column
                            label="图片名称"
                            prop="pt_name">
                          </el-table-column>
                          <el-table-column
                            label="上传者"
                            prop="username">
                          </el-table-column>
                          <el-table-column
                            label="操作">
                            <template scope="scope">
                                <el-button @click="deleteItem(scope.$index)">删除</el-button>
                            </template>
                          </el-table-column>
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item>
                    <el-button @click="dialogFormVisible = true">添加图片</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">提交</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    export default {
        data: function(){
            var validator1=(rule, value, callback) => {
                //console.log(22);
                //console.log(this.form);
                this.$http.get("/ptEditor/picture/" ,{
                    params:{
                        pt_name:value,
                        dtid:-1
                        },
                    }).then(
                            success =>{

                                if(success.data==true)
                                {
                                    return callback(new Error("图片名称已存在！"));
                                } else {
                                     callback();
                                }
                            },
                            error => {
                                console.log(error);
                                callback();
                            }
                );
            }
            var validator2=(rule, value, callback) => {
                //console.log(22);
                //console.log(this.form);
                if(!value.match("^[0-9]+$")){
                    return callback(new Error("编号必须为数字"));
                }
                console.log(111);
                this.$http.get("/iot/devicetype/findbydtcode" ,{
                    params:{
                        dtcode:value,
                        dtid:-1
                        },
                    }).then(
                            success =>{

                                if(success.data==true)
                                {
                                    return callback(new Error("编号已存在！"));
                                }else{
                                     callback();
                                }
                            },
                            error => {
                                console.log(error);
                                callback();
                            }
                );
            }
            return {
                form: {
                    username: '',
                    dtcode:'',
                    mvtype:'0',
                    state:'0',
                    notes:'',
                    items: []
                },
                dialogFormVisible: false,
                form2: {
                    ddname: '',
                    uintname: ''
                },
                rules:{
                    ddname: [
                        {required: true, message: '请输入图片名称', trigger: 'blur'},
                    ],
                    username:[
                        {required: true, message: '请输入上传者名称', trigger: 'blur'},
                    ],
                    dtname:[
                        {required: true, message: '请输入图片名称', trigger: 'blur'},
                        {validator: validator1, trigger: 'blur'}
                    ],
                    dtcode:[
                        {required: true, message: '请输入图片编号', trigger: 'blur'},
                        {validator: validator2, trigger: 'blur'},
                    ]

                },
                formLabelWidth: '120px'
            }
        },
        methods: {
            //增加图片
            onSubmit() {
                this.$http.get("/ptEditor/user/user",{params:{
                    username:localStorage.getItem('ms_username')
                }}).then(
                    success=>{
                        this.$message({
                            type: "info",
                            message: "正在上传！"
                        });
                    },
                    error=>{
                        this.$message({
                            type: "error",
                            message: "error"
                        });
                    }
                );
                this.$router.push({path: '/addpt'});
                
                 this.form.username="";
                 this.form.pt_name="";
                 this.form.state='0';
                 this.form.mvtype='0';
                 this.form.notes="";
                 this.form.items=[];


            },
            addItem(type,formName) {
                const self = this;
                self.$refs[formName].validate((valid) => {
                    if (valid) {
                            this.form.items.push({pt_name: this.form2.pt_name, username: this.form2.username});
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
                if(type == 1){
                    this.dialogFormVisible = false;
                } else {
                    this.resetForm2();
                }
            },
            resetForm(formName){
                this.$refs[formName].resetFields();
            },
            deleteItem(index){
                this.form.items.splice(index, 1);
            },
            resetForm2(){
                this.form2 = {
                    pt_name: '',
                    username: ''
                };
            },
            cancel(){
                this.form.username="";
                this.form.pt_name="";
                this.form.mvtype=0;
                this.form.state=0;
                this.form.notes="";
                this.form.items=[];
            }
        }
    }
</script>

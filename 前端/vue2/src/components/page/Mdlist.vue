<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-menu"></i> 列表</el-breadcrumb-item>
                <el-breadcrumb-item>我的图片列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">

            <el-input v-model="select_word" placeholder="输入图片名称" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="search" @click="getData()">搜索</el-button>
        </div>

        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" >
            <el-table-column prop="ptid" label="id" width="80">
            </el-table-column>
            <el-table-column prop="pt_name" label="图片名称" width="150">
            </el-table-column>
            <el-table-column prop="notes" label="备注" >
            </el-table-column>
            <el-table-column label="操作" width="80">
                <template scope="scope">
                    <el-button size="small" type="danger"
                            @click="handleEdit(scope)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                    @current-change ="handleCurrentChange"
                    layout="prev, pager, next"
                    :total="total"
                    :page-size="5"
                    >
            </el-pagination>
        </div>
    </div>
</template>

<script>
import util from '../common/js/util'
    export default {
        data() {
            var validator1=(rule, value, callback) => {
                console.log(123);
                console.log(value);
                this.$http.get("/ptEditor/picture/picture" ,{
                    params:{
                        username:localStorage.getItem("ms_username"),
                        },
                    }).then(
                            success =>{

                                if(success.data==true)
                                {
                                    return callback(new Error("名称已存在！"));
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
            return {
                tableData: [],
                username:'',
                select_word:"",
                 page:1,
                 total:0,
                 dialogFormVisible: false,
                 form2:{
                    region1:'',
                 },
                 form: {
                 form2:{
                    region1:'',
                 },
                    dtname: [{
                      dtid:'',
                      dtname:''
                    }],
                    state:0,
                    notes:''
                },
                rules:{
                    region1:[
                        {required: true, message: '请选择名称', trigger: 'blur'},
                        {validator: validator1, trigger: 'blur'}
                    ],
                },
                updatemydtid:'',
                formLabelWidth: '120px'
            }
        },
        created(){
            this.getUserID();
             this.getData();
        },
        methods: {
        getUserID(){
          this.$http.get("/ptEditor/user/user",{params:{
                    name:localStorage.getItem('ms_username')
                }}).then(
                    success=>{
                        this.id = success.data.id;
                        console.log("userid "+this.id)
                        this.$message({
                            type: "info",
                            message: "获取id成功"
                        });
                    },
                    error=>{
                        this.$message({
                            type: "error",
                            message: "error"
                        });
                    }
                );
        },
            getData(){

                this.$http.get("/ptEditor/picture/picture" ,{
                    params:{
                        pt_name:this.select_word,
                        username:localStorage.getItem("ms_username")
                        },
                    }).then(
                            success =>{
                                this.tableData=success.data;
                                console.log(success.data)
                                for(let i=0;i<success.data.length;i++){
                                    
                                    if(success.data[i].state==1)
                                    {
                                        this.tableData[i].state="禁用";
                                    }
                                    else
                                    {
                                        this.tableData[i].state="可用";
                                    }
                                }
                                this.count(this.select_word);

                            },
                            error => {
                                console.log(error);
                            }
                        );

            },
             handleCurrentChange(val){
               this.page = val;
               this.getData();
            },
            resetForm(formName){
                this.$refs[formName].resetFields();
            },
            count(name){
                this.$http.get("/ptEditor/findPtNumByUName" ,{
                    params:{
                        username:localStorage.getItem("ms_username")
                    }}).then(
                            success =>{
                            //console.log(111);
                            //console.log(success.data);
                            this.total=success.data;

                            },
                            error => {
                                console.log(error);
                            }
                        );
            },
            handleEdit(val){
                this.dialogFormVisible=true;
                this.$router.push({path: '/editmd', query: {mydtid: val.row}});
            },
        }
    }
</script>

<style scoped>
.handle-box{
    margin-bottom: 20px;
}
.handle-select{
    width: 120px;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
</style>

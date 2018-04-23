<template>
    <div class="table">
        <el-dialog
          title="提示"
          :visible.sync="visible3"
          size="tiny"
          :close-on-click-modal=false>
          <span>你确定要修改这条记录吗？修改后不允许撤销。</span>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleClose2">确 定</el-button>
            <el-button @click="visible3 = false">取 消</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="提示"
          :visible.sync="visible2"
          size="tiny"
          :close-on-click-modal=false>
          <span>你确定要删除这条记录吗？删除后不允许撤销。</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="visible2 = false">取 消</el-button>
            <el-button type="primary" @click="handleClose">确 定</el-button>
          </span>
        </el-dialog>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-menu"></i> 列表</el-breadcrumb-item>
                <el-breadcrumb-item>图片列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">
            <el-input v-model="select_word" placeholder="请输入用户名" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="search" @click="getData()">搜索</el-button>
        </div>
        <el-table :data="tableData" border style="width: 100%" ref="multipleTable" >
            <el-table-column prop="ptid" label="ID" width="80">
            </el-table-column>
            <el-table-column prop="pt_name" label="图片名称" >
            </el-table-column>
            <el-table-column prop="username" label="上传用户" >
            </el-table-column>
            <el-table-column prop="notes" label="备注">
            </el-table-column>
            <el-table-column prop="pt_createTime" label="创建时间" width="180">
            </el-table-column>
            <el-table-column label="操作" width="80">
                <template scope="scope">
                    <el-button size="small" type="danger"
                            @click="handleEdit(scope)">编辑</el-button>
                </template>
                
            </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="page"
          :page-size="5"
          layout="prev, pager, next"
          :total="0">
        </el-pagination>
    </div>
</template>

<script>
import util from '../common/js/util'
    export default {
        data() {
            var validator = (rule, value, callback) => {

                if(!value.match("^[A-Z]+$")){
                    return callback(new Error("设备类别编号必须为大写字母"));
                }
            }
            var validator1=(rule, value, callback) => {
                //console.log(22);
                //console.log(this.form);
                this.$http.get("/ptEditor/user/findPtByUName" ,{
                    params:{
                        uname:this.form.uname
                        },
                    }).then(
                            success =>{
                                console.log("dtlist "+success.data)
                                if(success.data==true)
                                {
                                    return callback(new Error("设备类别名称已存在！"));
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
                if(!value.match("^[A-Z]+$")){
                    return callback(new Error("设备类别编号必须为大写字母"));
                }
                console.log(111);
                this.$http.get("/iot/devicetype/findbydtcode" ,{
                    params:{
                        dtcode:value,
                        dtid:this.form.dtid
                        },
                    }).then(
                            success =>{

                                if(success.data==true)
                                {
                                    return callback(new Error("设备类别编号已存在！"));
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
            var validator3=(rule, value, callback) => {
                this.$http.get("/iot/devdatetype/findbydtidddname" ,{
                    params:{
                        ddname:this.form2.ddname,
                        dtid:this.form2.dtid
                        },
                    }).then(
                            success =>{

                                if(success.data==true)
                                {
                                    return callback(new Error("设备数据类型名称已存在！"));
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
                tableData: [],
                username: '',
                select_word: '',
                page:1,
                total:0,
                dialogFormVisible: false,
                dialogFormVisible1:false,
                dialogFormVisible3:false,
                visible2: false,
                visible3: false,
                deleteddtid:'',
                deleteindex:'',
                updateindex:'',
                scope:{},
                form: {
                    dtid:'',
                    dtname: '',
                    dtcode:'',
                    mvtype:'',
                    state:'',
                    notes:'',
                },
                form2: {
                    ddname: '',
                    uintname: '',
                    dtid:'',
                },
                form3: {
                    ddname: '',
                    uintname: '',
                    ddtid:'',
                    dtid:'',
                },
                rules:{
                    dtcode: [
                        {required: true, message: '请输入设备类别编号', trigger: 'blur'},
                        {validator: validator2, trigger: 'blur'},
                    ],
                    dtname:[
                        {required: true, message: '请输入设备类别名称', trigger: 'blur'},
                        {validator: validator1, trigger: 'blur'}
                    ],
                    ddname:[
                        {required: true, message: '请输入设备数据类型', trigger: 'blur'},
                        {validator: validator3, trigger: 'blur'},
                    ]
                },
                formLabelWidth: '120px',
                items: [{
                    ddname: '',
                    uintname: '',
                    dtid:'',
                }
                ]
            }
        },
        created(){
            this.getData();
        },
        methods: {
            getUser(){
                this.$http.get("/ptEditor/user/id",
                    {params:{userid:this.tableData[i].userid}}).then(
                        success =>{
                            console.log("getUser "+ success.data.username)
                        },
                        error =>{
                            this.$message({
                                type: "error",
                                message : "查询用户名失败"
                            });
                        }
                    );
            },
            getData(){
                this.$http.get("/ptEditor/user/findPtByUName" ,{
                    params:{
                        username:this.select_word,
                        currentPage:this.page,
                        onePage:5
                        },
                    }).then(
                            success =>{
                            console.log(success);
                            //console.log(success.data[0].createtime);
                                this.tableData=success.data;
                                for(let i=0;i<success.data.length;i++){
                                    this.tableData[i].pt_createTime=util.formatDate.format(new Date(success.data[i].pt_createTime), 'yyyy-MM-dd hh:mm:ss');
                                    this.tableData[i].pt_name=success.data[i].pt_name;
                                        
                                    if(success.data[i].state==1)
                                    {
                                        this.tableData[i].state="禁用";
                                    }
                                    else
                                    {
                                        this.tableData[i].state="可用";
                                    }
                                    if(success.data[i].mvtype==1)
                                    {
                                        this.tableData[i].mvtype="低速移动";
                                    }
                                    else
                                    {
                                        this.tableData[i].mvtype="静止";
                                    }
                                }
                                this.count();

                            },
                            error => {
                                console.log(error);
                            }
                        );
            },
            handleCurrentChange(val){
                //console.log(val);
                this.page = val;
                this.getData();
            },
            count(){
                this.$http.get("/ptEditor/findPtNumByUName" ,{
                    params:{
                        username:this.select_word
                        },
                    }).then(
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
                this.dialogFormVisible = true;
                this.form2.ptid=val.row.ptid;
                //console.log(222);
                //console.log(val.row.mfid);
                //console.log(333);
                //console.log(val);
                this.scope=val;
                console.log(this.scope);

                this.$http.get("/ptEditor/picture/picture" ,{
                    params:{
                        pt_name:val.row.pt_name,
                        username:val.row.username
                        },
                    }).then(
                            success =>{
                                //console.log(111);
                                //console.log(success.data);
                                this.form=success.data;
                                var imgUrl = success.data.pt_src;
                                this.$router.push({path: '/edit', params: { imgUrl:imgUrl }});

                            },
                            error => {
                                console.log(error);
                            }
                        );
               

            },

            resetForm(formName){
                this.$refs[formName].resetFields();
            },
            update(formName){
                //console.log(333);
                //console.log(this.form.mfid);
                const self = this;
                let params = {
                    "dtid":this.form.dtid,
                    "dtname" : this.form.dtname,
                    "dtcode" : this.form.dtcode,
                    "mvtype":this.form.mvtype,
                    "state":this.form.state,
                    "notes":this.form.notes

                };


                console.log(333);
                self.$refs[formName].validate((valid) => {
                console.log(valid);
                    if (valid) {
                            self.$http.post("/iot/devicetype/update",params).then(
                            success =>{
                                    this.$message({
                                        type: "info",
                                        message: "修改成功！"
                                    });
                                    this.dialogFormVisible = false;
                                    this.getData();


                            },
                            error => {
                                console.log(error);
                                this.$message({
                                    type: "error",
                                    message: "设备类别名称已存在！"
                                });
                                this.dialogFormVisible = false;
                            }
                        );
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            handleClose() {
                this.visible2=false;
                this.$http.get("/iot/devdatetype/delete" ,{
                    params:{
                        ddtid:this.deleteddtid,
                        },
                    }).then(
                            success =>{
                                this.items.splice(this.deleteindex, 1);
                            },
                            error => {
                                console.log(error);
                            }
                        );

            },
            handleClose2() {
                this.visible3=false;
                this.$http.get("/iot/devdatetype/update" ,{
                    params:{
                        ddtid:this.form3.ddtid,
                        ddname:this.form3.ddname,
                        uintname:this.form3.uintname,
                        },
                    }).then(
                            success =>{
                                this.items[this.updateindex].ddname=this.form3.ddname;
                                this.items[this.updateindex].uintname=this.form3.uintname;
                                this.dialogFormVisible3=false;

                            },
                            error => {
                                console.log(error);
                            }
                        );
            },
            deleteItem(val){
                this.deleteddtid=val.row.ddtid;
                this.deleteindex=val.$index;
                this.visible2 = true;
            },
            editItem(val){
                console.log(val);
                this.dialogFormVisible3=true;
                this.form3.ddname=val.row.ddname;
                console.log(this.form3.ddname);
                this.form3.uintname=val.row.uintname;
                console.log(this.form3.uintname);
                if(val.row.ddtid!=null){
                    this.form3.ddtid=val.row.ddtid;
                }
                console.log(this.form3.ddtid);
                this.updateindex=val.$index;
                console.log(this.updateindex);
                this.form3.dtid=val.row.dtid;

            },
            addItem(){
                this.visible3 = true;
            },
            getDdtid(){
                this.$http.get("/iot/devdatetype/getddtid" ,{
                    params:{
                        dtid:this.form2.dtid,
                        ddname:this.form2.ddname,
                        uintname:this.form2.uintname,
                        },
                    }).then(
                            success =>{
                                //console.log(123);
                                //console.log(success.data);
                                this.form3.ddtid=success.data.ddtid;
                                console.log(234);
                                console.log(this.form3.ddtid);
                            },
                            error => {
                                console.log(error);
                            }
                        );
            },
            addItem2(){
                this.$http.get("/iot/devdatetype/addone" ,{
                    params:{
                        dtid:this.form2.dtid,
                        ddname:this.form2.ddname,
                        uintname:this.form2.uintname,
                        },
                    }).then(
                            success =>{
                                this.items.push({ddname: this.form2.ddname,
                                uintname: this.form2.uintname,dtid:this.form2.dtid});
                                this.getDdtid();
                                this.dialogFormVisible1=false;
                                this.form2.ddname='';
                                this.form2.uintname='';
                                this.form2.dtid='';

                            },
                            error => {
                                this.form2.ddname='';
                                this.form2.uintname='';
                                this.form2.dtid='';
                                console.log(error);
                            }
                        );
            }
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

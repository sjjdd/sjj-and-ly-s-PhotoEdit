<template>
  <div id="sta">

    <el-upload
      class="upload-demo"
      drag
      action="/ptEditor/picture/uploadImage"
      list-type="picture-card"
      :data="mydata"
      :headers="headers"
      :before-upload="beforeAvatarUpload"
      :on-preview="showPicture"
      :on-remove="handleRemove"
      multiple
      :limit="5"
      :on-exceed="handleExceed"
      :file-list="fileList">
      
      <i class="el-icon-plus"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img v-image-preview width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    </div>
</template>

<script>
   
    var token =  localStorage.getItem('token')
    export default {

       data() {
        
         return {
           dialogImageUrl: '',
           dialogVisible: false,
           headers: {token: token},
           fileList: [],
           imgsrc:'../../../static/img/rect.png',
           mydata:{
            id:0
           }
         };
       },
       created(){
        this.getUserID();
       },
       methods: {
        showPicture(file){
          this.dialogImageUrl = file.url;
          this.dialogVisible = true;
          console.log("file.url "+file.url)
        },
 
        getUserID(){
          this.$http.get("/ptEditor/user/user",{params:{
                    name:localStorage.getItem('ms_username')
                }}).then(
                    success=>{
                        this.mydata.id = success.data.id;
                        console.log("userid "+this.mydata.id)
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
        },
        beforeAvatarUpload(file) {
        const isImg = (file.type === 'image/jpeg')||(file.type === 'image/png')||(file.type === 'image/jpg');
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isImg) {
          this.$message.error('上传头像图片只能是 JPG/JPEG/PNG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        console.log("file "+file)
        localStorage.setItem("file ",file);
        return isImg && isLt2M;
      },

       handleRemove(file, fileList) {
        console.log(file, fileList);
      },

      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      }
    },
    computed:{
      headers(){
         Authorizations:localStorage.getItem('token')
      }
    }
  }
</script>

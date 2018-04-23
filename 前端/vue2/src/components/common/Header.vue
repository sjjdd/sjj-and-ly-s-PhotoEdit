<template>
    <div class="header">
        <div class="logo">HOME</div>
        <div class="user-info">
           <el-dropdown trigger="hover">
                    <span class="el-dropdown-link userinfo-inner">
                    <img class="user-logo" src="../../../static/img/img.jpg"> <span>{{name}}</span></span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item  @click.native="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                name: localStorage.getItem('realname')
            }
        },
        methods:{
            logout() {
                this.$confirm('确认退出吗?', '提示', {
                }).then(() => {
                    console.log("权限已清除");
                    localStorage.clear();
                    this.$router.push('/login');
                }).catch(() => {

                });

            },
        }
    }
</script>
<style scoped>
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        line-height: 70px;
        color: #fff;
    }
    .header .logo{
        float: left;
        width:250px;
        text-align: center;
    }
    .user-info {
        position:relative;
        float: right;
        padding-right: 50px;
        font-size: 16px;
        color: #fff;

    }
    .user-info .el-dropdown-link{
        position: relative;
        display: inline-block;
        padding-left: 50px;
        color: #fff;
        cursor: pointer;
        vertical-align: middle;
    }
    .user-info .user-logo{
        position: relative;
        left:0;
        top:15px;
        width:40px;
        height:40px;
        border-radius: 50%;
    }
    .el-dropdown-menu__item{
        text-align: center;
    }
</style>

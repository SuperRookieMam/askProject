<template>
  <div id="login" class="loginDiv">
    <div class="inner">
      <el-card class="box-card">
        <div class="inner-div">
          <el-form label-width="80px"
                   ref="loginForm"
                   :model="user">
            <el-form-item label="账号"
                          :rules="loginRule"
                          prop="username">
              <el-input v-model="user.username" placeholder="请输入账号"/>
            </el-form-item>
            <el-form-item label="密码"
                          :rules="loginRule"
                          prop="password">
              <el-input v-model="user.password" placeholder="请输入账号"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('loginForm')">登陆</el-button>
              <el-button @click="resetForm('loginForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import { namespace } from 'vuex-class'

  const loginModel = namespace('Login')
  @Component({
    inject: ['setParames']
  })
  export default class Login extends Vue {
    loginRule = [{required: true, message: '请输入'}]

    user = {
      username: 'jueshihaonanren',
      password: '123456'
    }

    @loginModel.Action('login')
    login

    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.login(this.user).then(ele => {
            this.setParames('/', {type: 'other'})
            this.$router.push({path: '/'})
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }

    resetForm (formName) {
      this.$refs[formName].resetFields()
    }

    created () {

    }
  }
</script>
<style>
  .loginDiv {
    width: 100%;
    height: 100%;
    text-align: center;
    vertical-align: middle;
  }
  .inner {
    position: absolute;
    width: 36%;
    height:  30%;
    top:32%;
    left:30%;
    margin-left:-50px;
    margin-top:-25px;
    text-align: center;
    vertical-align: middle;
  }
  .inner-div-label{
    width: 30%;
    height: 25px;
    color: lavender;
  }

  .inner-div{
    line-height:15px;
    width: 90%;
    color: lavender;
    margin-top: 20px;
  }
  .box-card {
    width: 100%;
    background-color: #f0f7ff;
    text-align: center;
    vertical-align: middle;
  }
  .item {
    padding: 18px 0;
    width: 100%;

  }
  .text {
    font-size: 18px;
  }
</style>

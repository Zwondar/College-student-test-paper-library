<template>
    <div id="login-page" :style="{ 'background-image': `url(${backgroundImage})` }">
      <div class="login-container">
        <h1>Sign</h1>
        <br>
        <input type="text" class="Set-Con" placeholder="Username" v-model="loginData.username" @blur="validateInput('loginData.username')" />
        <br>
        <input type="password" class="Set-Con" placeholder="Password" v-model="loginData.password" @blur="validateInput('loginData.password')" />
        <br>
        <br>
        <button @click="handleLogin" id="Set-Button">Sign</button>
      </div>
    </div>
  </template>
  
  <script>
import axios from 'axios';

  export default {
    data() {
      return {
        backgroundImage: require('@/assets/R-C.gif'), 
        loginData: { // 将要发送的数据封装到loginData对象中
        username: '',
        password: '',
      },
      usernameError: false,
      passwordError: false,
        canSubmit: true
      };
    },
    methods: {
      validateInput(fieldName) {
      if (fieldName === 'loginData.username') {
        this.usernameError = this.loginData.username.trim() === '';
        this.canSubmit = !this.usernameError && !this.passwordError;
      } else if (fieldName === 'loginData.password') {
        this.passwordError = this.loginData.password.trim() === '';
        this.canSubmit = !this.usernameError && !this.passwordError;
      }
    },
      handleLogin() {
        // 在此处添加登录逻辑
        if(!this.canSubmit) {
          alert('账号密码不能为空')
          return;
        }
        axios.post('http://localhost:8080/Sign',this.loginData).
        then(response=>{
           alert(response.data.msg)
           if(response.data.msg=="创建账号成功") this.$router.push('/')
          }).catch(err=>{
          console.log(err);
          })
      }
    }
  };
  </script>
  
  <style scoped>
  #login-page {
    height: 100vh;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .Set-Con {
   width: 200px;
   height: 50px;
  }

  #Set-Button {
  width: 100px;
  height: 50px;
  }
  
  .login-container {
    padding: 2rem;
    background-color: rgba(255, 255, 255, 0.8); /* 添加半透明白色背景以确保文本可读 */
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 300px;
    height: 300px;
    display: flex;
  flex-direction: column;
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 增加这一行使内部元素垂直居中 */
  }
  </style>
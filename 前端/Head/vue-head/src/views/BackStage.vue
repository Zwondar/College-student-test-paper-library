<template>
    <div id="login-page" :style="{ 'background-image': `url(${backgroundImage})` }">
      <form class="login-container" @submit.prevent="submitForm">
        <h1>BackStage</h1>
        <br>
        <input type="text" class="Set-Con" placeholder="学校,学院,学科,年级,文件名,时间---名称,作者" v-model="textData"/>
        <br>
        <input type="file" id="UseFile" placeholder="文件上传" @change="onFileChange"/>
        <br>
        <br>
        <input id="Set-Button" type="submit" value="GO"> 
      </form>
    </div>
  </template>
  
  <script>
import axios from 'axios';
  export default {
    data() {
      return {
        backgroundImage: require('@/assets/R-C.gif'), 
        textData : "",
        selectedFile : null,
        token: sessionStorage.getItem('token') 
      };
    },
    methods: { 
    onFileChange(e) {  
      this.selectedFile = e.target.files[0];  
    }, 
    submitForm() {
      const formData = new FormData();  
      formData.append('Data', this.textData);  
      formData.append('File', this.selectedFile);  
      axios.post('http://localhost:8080/Log/upload',formData , {
        headers: {  
          'Content-Type': 'multipart/form-data',  
          'Token': this.token // 设置Token请求头  
        }  
      }).then(response=>{
        alert(response.data.data)
      }).catch(err=>{
        console.log(err)
      })  
    }
    },
    mounted() {
      var jwt=sessionStorage.getItem("token");
        let headers = {
    Token: jwt 
  };
        axios.post('http://localhost:8080/Log/verify',{},{headers})//向该url发送请求
      .then(response=>{
        if(response.data.data=="1") this.$router.push('/Log/On');
      })
      .catch(err => {
        console.log(err)
        this.$router.push('/');
      })
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
   width: 500px;
   height: 70px;
  }

  #Set-Button {
  width: 100px;
  height: 50px;
  }

  #UseFile {
    width: 500px;
  height: 50px;
  }
  
  .login-container {
    padding: 2rem;
    background-color: rgba(255, 255, 255, 0.8); /* 添加半透明白色背景以确保文本可读 */
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 700px;
    height: 400px;
    display: flex;
  flex-direction: column;
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 增加这一行使内部元素垂直居中 */
  }
  </style>
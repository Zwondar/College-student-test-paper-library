<template>
  <div>
    <img :src='image' id="ima">
    <pre id="textFileContent"></pre>
    <el-button type="primary" round @click="back">回退</el-button>
  </div>
   
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
        url :this.$route.query.url,
        image :''
      }
    },
    methods : {
        fetchTextFileContent() { 

      axios.get(this.url ,{ responseType: 'arraybuffer' })  
        .then(response => {  
          // ------------------- 
          const con=this.url.slice(-4);
          if(con==".txt") document.getElementById('textFileContent').textContent = response.data;
         
          else this.image=this.url;  
        })  
        .catch(error => {  
          console.error('Error fetching the text file:', error);  
        });  
    }  ,
       back(){
      this.$router.push('/Log/On')
    }
    
    },
    mounted() {
       this.fetchTextFileContent();
       var jwt=sessionStorage.getItem("token");
        let headers = {
    Token: jwt 
  };
        axios.post('http://localhost:8080/Log/verify',{},{headers})//向该url发送请求
      .then()
      .catch(err => {
        console.log(err);
        this.$router.push('/');
      })
    }
}
</script>

<style scoped>  
/* 这里可以添加一些样式来美化pre元素，比如设置字体、背景色等 */  
pre {  
  font-family: monospace; /* 通常.txt文件使用等宽字体 */  
  white-space: pre; /* 保留空格和换行符 */  
  background-color: #f4f4f4; /* 背景色示例 */  
  padding: 10px; /* 内边距示例 */  
  border: 1px solid #ddd; /* 边框示例 */  
  font-size: 20px; 
}  

#ima{
  width: 100%;
}
</style>
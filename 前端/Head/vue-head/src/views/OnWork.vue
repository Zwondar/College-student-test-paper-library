<template>
    <div>
        <el-container>
            <el-header>
                <div class="flex-container">
                    
                    <span 
                    v-for="(item, index) in grade"  
                    :key="index"  
                    class="con-select"  
                    @click="SelectData(index)"  
                    :style="{ color: activeIndex === index ? activeColor : defaultColor }"  
                    >  
                    {{ item }}  
                </span>
                    <el-input v-model="sel.input" placeholder="请输入查询内容" class="inline-input"></el-input>
                    <el-button type="success" round @click="getData">GO</el-button>
                </div>
            </el-header> 
            <!-- HEADER-->
            <el-main>

                <el-table :data="tableApp" style="width: 100%" v-if="control1">

<el-table-column label="名称" width="380">
    <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
            <p>版本: {{ scope.row.edition }}</p>
            <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
        </el-popover>
    </template>
</el-table-column>

<el-table-column label="作者" width="280">
    <template slot-scope="scope">
        
        <span style="margin-left: 10px">{{ scope.row.author }}</span>
    </template>
</el-table-column>

<el-table-column label="操作">
    <template slot-scope="scope">

    
        <!-- scope.$index -->
        <el-button size="mini" type="success"
            @click="handleDownload( scope.row)">下载</el-button>
    </template>
</el-table-column>
</el-table>

                <!--分割-->>

                <el-table :data="tableData" style="width: 100%" v-if="control2">

                    <el-table-column label="学校" width="180">
                        <template slot-scope="scope">
                            
                            <span style="margin-left: 10px">{{ scope.row.school }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="学院" width="180">
                        <template slot-scope="scope">
                            
                            <span style="margin-left: 10px">{{ scope.row.college }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="学科" width="180">
                        <template slot-scope="scope">
                            
                            <span style="margin-left: 10px">{{ scope.row.discipline }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="年级" width="180">
                        <template slot-scope="scope">
                            
                            <span style="margin-left: 10px">{{ scope.row.grade }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="名称" width="380">
                        <template slot-scope="scope">
                            <el-popover trigger="hover" placement="top">
                                <p>更新时间: {{ scope.row.upDataTime }}</p>
                                <p>内容: {{ scope.row.name }}</p>
                                <div slot="reference" class="name-wrapper">
                                    <el-tag size="medium">{{ scope.row.name }}</el-tag>
                                </div>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column label="时间" width="280">
                        <template slot-scope="scope">
                            
                            <span style="margin-left: 10px">{{ scope.row.time }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作">
                        <template slot-scope="scope">

                        
                            <!-- scope.$index -->
                            <el-button size="mini"  type="primary"  
                            @click="handleEdit( scope.row)">查看</el-button>
                            <el-button size="mini" type="success"
                                @click="handleDownload( scope.row)">下载</el-button>
                        </template>
                    </el-table-column>
                </el-table>

            </el-main>
            <!-- Main -->
        </el-container>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            tableData: [],
            token: sessionStorage.getItem('token'),
            grade :['大一','大二','大三','大四','软件'],
            sel :{
                input: '',//输入框操作
                select:''
            },
            activeIndex: null,  
      activeColor: 'white',  
      defaultColor: 'black',  
      url :'',
      control1 : false,
    control2 :true,
      tableApp : []
      }
    },
    methods: {

    //  查看
      handleEdit(row) {
      const formData = new FormData();  
      formData.append('name', row.name);
       axios.post('http://localhost:8080/Log/lookData',formData,
       {
        headers: {    
          'Token': this.token // 设置Token请求头  
        }  
      }).then(response=>{
          this.url=response.data.data;
          this.$router.push({ name: 'view', query: { url: this.url } }); 
       }).catch(err=>{
        console.log(err);
       })
      },

      //下载
      handleDownload(row) {
        const formData = new FormData();  
       formData.append('name', row.name);
       axios.post('http://localhost:8080/Log/lookData',formData,
       {
        headers: {    
          'Token': this.token // 设置Token请求头  
        }  
      }).then(response=>{
          this.url=response.data.data;
          window.location.href = this.url;
          
       }).catch(err=>{
        console.log(err);
       })
    
      },
    //查询条件
    SelectData(g){
      this.activeIndex = g; 
      this.sel.select=this.grade[g];
      if(this.sel.select=='软件') {
        this.control1=true;
        this.control2=false;
     }else {
        this.control1=false;
        this.control2=true;
     }
      this.getData();
    },

      //获取数据
     getData (){
        if(this.control2) {
        axios.post('http://localhost:8080/Log/getData',this.sel,{
        headers: {    
          'Token': this.token // 设置Token请求头  
        }  
      }).then(response=>{
        this.tableData=response.data.data
      }).catch(err=>{
        console.log(err)
      })  
      }
      else {
        axios.post('http://localhost:8080/Log/getApp',this.sel,{
        headers: {    
          'Token': this.token // 设置Token请求头  
        }  
      }).then(response=>{
        this.tableApp=response.data.data
      }).catch(err=>{
        console.log(err)
      })  
      }
    }
    },
    mounted() {
        var jwt=sessionStorage.getItem("token");
        let headers = {
    Token: jwt // 将 'Authorization' 改为 'Token'
  };
        axios.post('http://localhost:8080/Log/verify',{},{headers})//向该url发送请求
      .then()
      .catch(err => {
        console.log(err);
        this.$router.push('/');
      }),
      axios.post('http://localhost:8080/Log/getData',this.sel,{headers}).then(
        response=>{
            this.tableData=response.data.data;
        }
      ).catch(err=>{
        console.log(err)
      })
      
  }
    
    }

</script>

<style>
.con-select {
    background-color: rgb(255, 157, 0); 
    border-radius: 2500px;
    display: inline-block;
    width: 500px;
    height: 70px;
    /* 根据需要调整宽度 */
    align-items: center;
    /* 添加此行以实现垂直居中 */
    justify-content: center;
    /* 如果需要同时实现水平居中（已通过 text-align 实现） */
    /* 如果需要文字居中显示 */
    font-size: 30px;
    cursor: pointer;  
    transition: color 0.5s; 
}

.flex-container {
    display: flex;
    align-items: center;
    /* 使所有子元素垂直居中对齐 */
    gap: 8px;
    /* 添加间隔以确保元素之间有一定的间距 */
}

.con-select,
.inline-input {
    /* 如果需要限制宽度，请设置合适的最大宽度 */
    max-width: 500px;
    display: inline-flex;
    /* 使得 span 和 el-input 都能灵活调整宽度并保持在一行 */
}
</style>
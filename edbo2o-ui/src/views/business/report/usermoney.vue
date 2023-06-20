<template>
  <el-main>
    <el-row>
      <el-form ref="queryForm" :model="queryParams" :inline="true" >
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-row>
      <el-col :span="8">
        <el-table v-loading="loading" :data="dataList" >
          <el-table-column label="客户名称" align="center" prop="name" />
          <el-table-column label="手机号" align="center" prop="phone" />
          <el-table-column label="消费金额" align="center" prop="money" />
        </el-table>
      </el-col>
      <el-col :span="16">
        <div ref="chart" style="width:100%;height:700px"></div>
      </el-col>
      <el-col :span="24">
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
  </el-main>

</template>
<script>
  import { userMoney} from "@/api/business/report/report";
  import * as echarts from 'echarts';
  export default {
    name: "xxx",
    data() {
      return {
        // 遮罩层
        loading: true,
        dataList:[],
        // 总条数
        total: 0,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          status: null,
          timeDim:null,
          groupDim:null
        },
        // 表单参数
        form: {},
        chartData:[]
      };
    },
    created() {
      this.getList();
    },
    mounted(){
      //this.initChart()
    },
    methods: {
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.queryParams= {
          pageNum: 1,
            pageSize: 10,
            status: null,
            timeDim:null,
            groupDim:null
        }
      },
      getList(){
        userMoney(this.queryParams).then(resp=>{
          this.dataList = resp.rows;
          this.total = resp.total;
          this.loading = false;

          //处理图形
          this.chartData = [];
          this.dataList.forEach(item=>{
            let data = {
              amount:item.money,
              product:item.name,
            }
            this.chartData.push(data)
            //初始化barChart
            this.initChart();
          })
        })
      },
      initChart(){
        const chart = this.$refs.chart
        let option = {
          dataset: {
            // source: [
            //   ['score', 'amount', 'product'],
            //   [89.3, 58212, 'Matcha Latte'],
            //   [57.1, 78254, 'Milk Tea'],
            //   [74.4, 41032, 'Cheese Cocoa'],
            //   [50.1, 12755, 'Cheese Brownie'],
            //   [89.7, 20145, 'Matcha Cocoa'],
            //   [68.1, 79146, 'Tea'],
            //   [19.6, 91852, 'Orange Juice'],
            //   [10.6, 101852, 'Lemon Juice'],
            //   [32.7, 20112, 'Walnut Brownie']
            // ]
            source: this.chartData
          },
          grid: { containLabel: true },
          xAxis: { name: 'amount' },
          yAxis: { type: 'category' },
          visualMap: {
            orient: 'horizontal',
            left: 'center',
            min: 1000,
            max: 10000,
            text: ['High Score', 'Low Score'],
            // Map the score column to color
            dimension: 0,
            inRange: {
              color: ['#65B581', '#FFCE34', '#FD665F']
            }
          },
          series: [
            {
              type: 'bar',
              encode: {
                // Map the "amount" column to X axis.
                x: 'amount',
                // Map the "product" column to Y axis
                y: 'product'
              }
            }
          ]
        };
        echarts.init(chart).setOption(option);
      }
    }
  };
</script>

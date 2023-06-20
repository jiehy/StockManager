<template>
  <div>
    <el-row>
      <el-col :span="80" :push="6">
        <el-form :model="form" ref="form" size="max" v-show="showSearch" label-width="90px">
            <el-form-item label="仓库" prop="warehouse">
              <el-select v-model="form.warehouse" placeholder="请选择仓库">
                <el-option
                  v-for="item in warehouseList"
                  :key="item.id"
                  :label="item.entrepotName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="出入库时间" prop="entryExitTime">
              <el-date-picker clearable
                              v-model="form.entryExitTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="年/月/日">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="备注" prop="info">
              <el-input type="textarea" :style="{width: '220px'}" v-model="form.info" placeholder="请输入备注" />
            </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="50" :push="6">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="handleItem"
        >添加明细</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="80" :push="6">
        <el-table :data="itemList" style="width: 800px">
          <el-table-column label="序号" type="index"/>
          <el-table-column label="名称" prop="name"/>
          <el-table-column label="品牌" prop="brand"/>
          <el-table-column label="分类" prop="category"/>
          <el-table-column label="数量" prop="amount"/>
          <el-table-column
            label="操作"
            align="center"
            v-if="">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleDel(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button @click="cancel">关闭</el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import { getStatement } from "@/api/business/statement/statement";
// import { addStatementItem,listStatementItem,payStatement,getQRcode} from "@/api/business/statementItem/statementItem";
// import { listService } from "@/api/business/service/service";
import {listManagement} from "@/api/entrepot/management";

export default {
  name: "inbound_outbound",
  dicts: [],
  data() {
    return {
      itemList: this.$route.query.items,
      warehouseList: [],
      form: {
        type: this.$route.query.id,
          items: this.itemList,
        },
        showSearch: true,
      };
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        listManagement().then(req => {
          this.warehouseList = req.rows;
        });
      },
      cancel() {
        this.$router.push({
          path: "/business/warehouse"
        });
      },
      handleSubmit() {

      },
      handleDel(row) {

      },
      handleItem() {
        this.$router.push({
          path:"/business/warehouse/item"
        });
      },
    },
    computed: {
    },
  };
</script>

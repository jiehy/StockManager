<template>
  <div>
    <el-row>
      <el-col :span="80" :push="7">
        <el-form :model="form" ref="form" size="small" :inline="true" v-show="showSearch" label-width="90px">
          <el-form-item label="关键字" prop="keyword">
            <el-input v-model="form.keyword" placeholder="请输入关键字" />
          </el-form-item>
            <el-form-item label="分类" prop="classify">
              <treeselect v-model="form.superiorClassify" :options="superiorClassifyList" :normalizer="normalizer" placeholder="请选择上级分类" style="width: 230px"/>
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
          @click="handleAddItem"
        >添加物品</el-button>
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
                @click="handleChoose(scope.row)"
              >选择此物品</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" @click="handleSubmit(items)">提交</el-button>
        <el-button @click="cancel">关闭</el-button>
      </el-col>
    </el-row>

    <!--添加物品弹窗-->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="itemForm" label-width="80px">
        <el-form-item label="物品名称" prop="name">
          <el-input v-model="itemForm.name" placeholder="请输入物品名称"/>
        </el-form-item>
        <el-form-item label="上级分类" prop="superiorClassify">
          <treeselect v-model="form.superiorClassify" :options="superiorClassifyList" :normalizer="normalizer" placeholder="请选择上级分类" :disabled="flag"/>
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="itemForm.brand" placeholder="请输入品牌"/>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            ref="upfile"
            action="upload"
            style="display: inline"
            :auto-upload="false"
            :on-change="handleChange"
            :file-list="fileList"
          >
            <el-button type="success">选择文件</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述" prop="info">
          <el-input type="textarea" v-model="itemForm.info" placeholder="请输入描述"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelItem">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
// import { getStatement } from "@/api/business/statement/statement";
// import { addStatementItem,listStatementItem,payStatement,getQRcode} from "@/api/business/statementItem/statementItem";
// import { listService } from "@/api/business/service/service";
import {AllListGoodsManagement} from "@/api/goods/management";
import {superiorList} from "@/api/business/classify";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
    name: "item",
    components: { Treeselect },
    dicts: [],
    data() {
      return {
        fileList:[],
        itemForm: {},
        open: false,
        items: [],
        superiorClassifyList: [],
        itemList: [],
        warehouseList: [],
        form: {
          type: this.id,
        },
        showSearch: true,
      };
    },
    created() {
      this.getList();
    },
    methods: {
      handleChange(file, fileList){
        console.log(file);
        console.log(fileList);
        this.fileList=fileList;
      },
      cancel() {
        this.$router.push({
          path:"/business/warehouse"
        });
      },
      cancelItem() {
        this.reset();
        this.open = false;
      },
      handleSubmit(items) {
        this.$router.push({
          path:"/business/warehouse/inbound_or_outbound",query:{items}
        });
      },
      handleChoose(row) {
        this.items.push(row);
      },
      getList() {
        superiorList().then(response => {
          this.superiorClassifyList = response.data;
          this.loading = false;
        });
        AllListGoodsManagement().then(response => {
          this.itemList = response.data;
        });
      },
      /** 转换上级分类数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.id,
          label: node.classify,
          children: node.children
        };
      },
      // 表单重置
      reset() {
        this.form = {
          keyword: null,
          superiorClassify: null,
        };
        this.itemForm = {
          name: null,
          info: null,
          brand: null,
          superiorClassify: null,
          fileList: [],
        }
      },
      handleAddItem() {
        this.reset();
        this.open = true;
      },
    },
    computed: {
    },
  };
</script>

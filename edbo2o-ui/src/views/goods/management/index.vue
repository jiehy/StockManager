<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="请输入分类名称或描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="entrepot">
        <el-select
          v-model="entrepotParams.entrepot"
          placeholder="仓库"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in this.entrepotList"
            :key="dict.entrepotName"
            :label="dict.entrepotName"
            :value="dict.entrepotName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入所属分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['goods:management:add']"
        >添加物品</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['goods:management:edit']"
        >仓库管理</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['goods:management:edit']"
        >分类管理</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="物品" align="center" prop="coverImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverImage" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="分类" align="center" prop="category" />
      <el-table-column label="数量" align="center" prop="specification" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:management:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:management:remove']"
          >删除
          </el-button>
          <el-button
            v-hasPermi="['goods:management:remove']"
            size="mini"
            type="text"
            @click="handleDetail(scope.row)"
          >出入库明细
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {addManagement, delManagement, listGoodsManagement, updateManagement} from "@/api/goods/management";
import {listManagement} from "@/api/entrepot/management";

export default {
    name: "Management",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 物品基本信息表格数据
        managementList: [],
        entrepotList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          category: null,
          coverImage: null,
          name: null,
          brand: null,
          specification: null,
          depict: null,
          keyword: null
        },
        //查询仓库参数
        entrepotParams: {
          entrepot: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [
            {required: true, message: "仓库名称不能为空", trigger: "blur"},
            {max: 30, message: '仓库名称长度不能超过30个字', trigger: 'blur'}
          ],
          depict: [
            {required: true, message: "仓库地址不能为空", trigger: "blur"},
            {max: 200, message: '仓库地址长度不能超过200个字', trigger: 'blur'}
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getEntrepotList();
    },
    methods: {
      /** 查询物品基本信息列表 */
      getList() {
        this.loading = true;
        listGoodsManagement(this.queryParams).then(response => {
          this.managementList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getEntrepotList() {
        listManagement(this.entrepotParams).then(response => {
          this.entrepotList = response.rows;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          category: null,
          coverImage: null,
          name: null,
          brand: null,
          specification: null,
          depict: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.$router.push({
          path: '/goods/management/add'
        });
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        const id = row.id;
        this.$router.push({
          path: '/goods/management/edit',
          query: {id}
        });
      },
      /** 明细按钮操作 */
      handleDetail(row) {
        const id = row.id;
        this.$router.push({
          path: '/goods/management/detail',
          query: {id}
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateManagement(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addManagement(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除物品基本信息编号为"' + ids + '"的数据项？').then(function() {
          return delManagement(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('goods/management/export', {
          ...this.queryParams
        }, `management_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>

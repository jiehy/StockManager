<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column align="center" label="序号" prop="id"/>
      <el-table-column align="center" label="物品" prop="coverImage" width="100">
        <template slot-scope="scope">
          <image-preview :height="50" :src="scope.row.coverImage" :width="50"/>
        </template>
      </el-table-column>

      <el-table-column align="center" label="名称" prop="name"/>
      <el-table-column align="center" label="品牌" prop="brand"/>
      <el-table-column align="center" label="分类" prop="category"/>
      <el-table-column align="center" label="数量" prop="specification"/>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['goods:management:edit']"
            icon="el-icon-edit"
            size="small"
            type="primary"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['goods:management:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
          <el-button
            v-hasPermi="['goods:management:remove']"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >出入库明细
          </el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import {
  addManagement,
  delManagement,
  getManagement,
  listGoodsManagement,
  updateManagement
} from "@/api/goods/management";
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物品基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getManagement(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物品基本信息";
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
      this.$modal.confirm('是否确认删除物品基本信息编号为"' + ids + '"的数据项？').then(function () {
        return delManagement(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
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

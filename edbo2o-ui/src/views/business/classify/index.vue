<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="请输入关键字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上级分类" prop="superiorClassify">
        <treeselect v-model="queryParams.superiorClassify" :options="superiorClassifyList" :normalizer="normalizer" placeholder="请选择上级分类" style="width: 230px"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="handleAdd"
          v-hasPermi="['classify:management:add']"
        >添加分类</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          size="mini"
          @click="handleMigration"
          v-hasPermi="['classify:management:migration']"
        >物品迁移</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100" />
      <el-table-column label="上级分类" align="center" prop="superiorClassify">
        <template slot-scope="scope">
          <template v-for="item in all">
            <span v-if="item.id == scope.row.superiorClassify">{{ item.classify }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="分类" align="center" prop="classify" />
      <el-table-column label="描述" align="center" prop="classifyDescribe" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['classify:management:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['classify:management:remove']"
          >删除</el-button>
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

    <!-- 添加或修改分类管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级分类" prop="superiorClassify">
          <treeselect v-model="form.superiorClassify" :options="superiorClassifyList" :normalizer="normalizer" placeholder="请选择上级分类" :disabled="flag"/>
        </el-form-item>
        <el-form-item label="分类名称" prop="classify">
          <el-input v-model="form.classify" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="描述" prop="classifyDescribe">
          <el-input v-model="form.classifyDescribe" placeholder="请输入描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物品迁移分类管理对话框 -->
    <el-dialog :title="title" :visible.sync="migrationOpen" width="500px" append-to-body>
      <el-form ref="form" :model="move" :rules="rules" label-width="90px">
        <el-form-item label="被迁移分类" prop="classifySource">
          <treeselect v-model="move.source" :options="superiorClassifyList" :normalizer="normalizer" placeholder="请选择源分类"/>
        </el-form-item>
        <el-form-item label="迁移到" prop="classifyTarget">
          <treeselect v-model="move.target" :options="superiorClassifyList" :normalizer="normalizer" placeholder="请选择目标分类"/>
        </el-form-item>
        <el-form-item prop="classifyInclude">
          <el-checkbox v-model="move.include">包括子分类</el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitMove">确 定</el-button>
        <el-button @click="cancelMove">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addManagement,
  allList,
  delManagement,
  getManagement,
  listManagement,
  moveManagement,
  superiorList,
  updateManagement
} from "@/api/business/classify";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Management",
  components: { Treeselect },
  data() {
    return {
      all: [],
      flag: false,
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
      // 分类管理表格数据
      managementList: [],
      // 上级分类表格数据
      superiorClassifyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示迁移弹出层
      migrationOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        superiorClassify: null,
        classify: null,
        classifyDescribe: null,
        classifyHierarchy: null,
        keyword: null
      },
      // 表单参数
      form: {},
      // 数据迁移参数
      move: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
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
    /** 查询分类管理列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows;
        this.total = response.total;
      });
      superiorList().then(response => {
        this.superiorClassifyList = response.data;
        this.loading = false;
      });
      allList().then(response => {
        this.all = response.data;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 分类迁移取消按钮
    cancelMove() {
      this.migrationOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        superiorClassify: null,
        classify: null,
        classifyDescribe: null,
        classifyHierarchy: null
      };
      this.move = {
        source: null,
        target: null,
        include: true,
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
      this.reset();
      this.flag = false;
      this.open = true;
      this.title = "添加分类管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getManagement(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分类管理";
        this.flag = true;
      });
    },
    /** 物品迁移按钮操作 */
    handleMigration() {
      this.move = {
        source: null,
        target: null,
        include: true,
      };
      this.migrationOpen = true;
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
    /** 分类迁移提交按钮 */
    submitMove() {
      moveManagement(this.move).then(response => {
        this.$modal.msgSuccess("迁移成功");
        this.migrationOpen = false;
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除分类管理编号为"' + ids + '"的数据项？').then(function() {
        return delManagement(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('classify/management/export', {
        ...this.queryParams
      }, `management_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

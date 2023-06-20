<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="出入库时间" prop="entryExitTime">
        <el-date-picker clearable
          v-model="queryParams.entryExitTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出入库时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="类型" prop="entryExitTime">
        <el-select v-model="queryParams.type" placeholder="请选择类型">
          <el-option
            v-for="dict in dict.type.in_or_out"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="entryExitTime">
        <el-select v-model="queryParams.status" placeholder="请选择状态">
          <el-option
            v-for="dict in dict.type.warehouse_particulars_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库" prop="warehouse">
        <el-select v-model="queryParams.warehouse" placeholder="请选择仓库">
          <el-option
            v-for="item in warehouseList"
            :key="item.id"
            :label="item.entrepotName"
            :value="item.id"
          />
        </el-select>
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
          @click="handleBound(0)"
          v-hasPermi="['warehouse:storeroom:bound']"
        >入库</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="handleBound(1)"
          v-hasPermi="['warehouse:storeroom:bound']"
        >出库</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
    </el-row>

    <el-table v-loading="loading" :data="storeroomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index"/>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.in_or_out" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouse" />
      <el-table-column label="总数量" align="center" prop="total" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="录入人" align="center" prop="operator" />
      <el-table-column label="出入库时间" align="center" prop="entryExitTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryExitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="录入时间" align="center" prop="operationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.warehouse_particulars_type" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleInvalid(scope.row)"
            v-hasPermi="['warehouse:storeroom:edit']"
          >作废</el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['warehouse:storeroom:remove']"
          >查看</el-button>
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

    <!-- 添加或修改出入库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库" prop="warehouse">
          <el-input v-model="form.warehouse" placeholder="请输入仓库" />
        </el-form-item>
        <el-form-item label="出入库时间" prop="entryExitTime">
          <el-date-picker clearable
            v-model="form.entryExitTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出入库时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="录入时间" prop="operationTime">
          <el-date-picker clearable
            v-model="form.operationTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择录入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="录入人" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入录入人" />
        </el-form-item>
        <el-form-item label="备注" prop="info">
          <el-input v-model="form.info" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="总数量" prop="total">
          <el-input v-model="form.total" placeholder="请输入总数量" />
        </el-form-item>
        <el-form-item label="总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入总金额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addStoreroom,
  delStoreroom,
  getStoreroom,
  invalidStoreroom,
  listStoreroom,
  updateStoreroom
} from "@/api/business/storeroom";
import {listManagement} from "@/api/entrepot/management";

export default {
  name: "Storeroom",
  dicts: ['in_or_out', 'warehouse_particulars_type'],
  data() {
    return {
      // 仓库列表
      warehouseList: [],
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
      // 出入库管理表格数据
      storeroomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouse: null,
        type: null,
        entryExitTime: null,
        operationTime: null,
        operator: null,
        info: null,
        status: null,
        total: null,
        totalAmount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleInvalid(row) {
      invalidStoreroom(row.id).then(req => {
        this.$modal.msgSuccess("作废成功");
        this.getList();
      })
    },
    handleBound(id) {
      this.$router.push({
        path: "/business/warehouse/inbound_or_outbound", query: {id}
      });
    },
    /** 查询出入库管理列表 */
    getList() {
      this.loading = true;
      listStoreroom(this.queryParams).then(response => {
        this.storeroomList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listManagement().then(req => {
        this.warehouseList = req.rows;
      })
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
        warehouse: null,
        type: null,
        entryExitTime: null,
        operationTime: null,
        operator: null,
        info: null,
        status: 0,
        total: null,
        totalAmount: null
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
      this.open = true;
      this.title = "添加出入库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStoreroom(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出入库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStoreroom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStoreroom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除出入库管理编号为"' + ids + '"的数据项？').then(function() {
        return delStoreroom(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('warehouse/storeroom/export', {
        ...this.queryParams
      }, `storeroom_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

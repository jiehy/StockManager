<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="affiliateCustomers">
        <el-input
          v-model="queryParams.affiliateCustomers"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable>
          <el-option
            v-for="dict in dict.type.contract_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="录入时间" prop="entryTime">
        <el-date-picker v-model="queryParams.entryTime"
                        clearable
                        end-placeholder="结束日期"
                        start-placeholder="开始日期"
                        type="daterange"
                        value-format="yyyy-MM-dd">
        </el-date-picker>
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:contractManagement:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractManagementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="客户" align="center" prop="affiliateCustomers"/>
      <el-table-column label="合同名称" align="center" prop="title"/>
      <el-table-column label="合同编号" align="center" prop="no"/>
      <el-table-column label="合同金额（单位：万元）" align="center" prop="amount" width="180"/>
      <el-table-column label="开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电子附件" align="center" prop="electronicAccessories">
        <template slot-scope="scope">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="download(scope.row)"
          >下载
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="录入人" align="center" prop="entryPerson"/>
      <el-table-column label="录入时间" align="center" prop="entryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contract_state" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="是否盖章" align="center" prop="toStamp">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.whether_to_stamp" :value="scope.row.toStamp"/>
        </template>
      </el-table-column>
      <el-table-column label="是否作废" align="center" prop="invalid">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_invalid" :value="scope.row.invalid"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:contractManagement:edit']"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="auditSuccess(scope.row)"
            v-hasPermi="['business:contractManagement:edit']"
          >审核通过
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="auditPass(scope.row)"
            v-hasPermi="['business:contractManagement:edit']"
          >审核不通过
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="toStamp(scope.row)"
            v-hasPermi="['business:contractManagement:edit']"
          >确认盖章
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="invalid(scope.row)"
            v-hasPermi="['business:contractManagement:remove']"
          >作废
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

    <!-- 添加或修改合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户" prop="affiliateCustomers">
          <el-input v-model="form.affiliateCustomers" placeholder="请输入客户"/>
        </el-form-item>
        <el-form-item label="合同名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="合同编号" prop="no">
          <el-input v-model="form.no" placeholder="请输入编号"/>
        </el-form-item>
        <el-form-item label="合同金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额"/>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker clearable
                          v-model="form.startDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable
                          v-model="form.endDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="电子附件">
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
  addContractManagement,
  auditPass,
  auditSuccess,
  delContractManagement,
  downloadFile,
  getContractManagement,
  invalid,
  listContractManagement,
  toStamp,
  updateContractManagement,
} from "@/api/business/contractManagement";

export default {
  name: "ContractManagement",
  dicts: ['is_invalid', 'whether_to_stamp', 'contract_state'],
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
      // 合同管理表格数据
      contractManagementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        affiliateCustomers: null,
        entryTime: [],
        auditStatus: null,
      },
      fileList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    invalid(row) {
      invalid(row.id).then(response => {
        this.getList()
      })
    },
    toStamp(row) {
      toStamp(row.id).then(response => {
        this.getList()
      })
    },
    auditPass(row) {
      auditPass(row.id).then(response => {
        this.getList()
      })
    },
    auditSuccess(row) {
      auditSuccess(row.id).then(response => {
        this.getList()
      })
    },
    download(row) {
      downloadFile(row.id).then(res => {

      });
    },
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    /** 查询合同管理列表 */
    getList() {
      this.loading = true;
      listContractManagement(this.queryParams).then(response => {
        this.contractManagementList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        affiliateCustomers: null,
        title: null,
        no: null,
        amount: null,
        startDate: null,
        endDate: null,
        electronicAccessories: null,
        entryPerson: null,
        entryTime: null,
        changeTime: null,
        auditStatus: null,
        toStamp: null,
        invalid: null
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
      this.title = "添加合同管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContractManagement(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改合同管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let formData = new FormData();
          formData.append("id", this.form.id);
          formData.append("affiliateCustomers", this.form.affiliateCustomers);
          formData.append("title", this.fileList[0].name);
          formData.append("no", this.form.no);
          formData.append("amount", this.form.amount);
          formData.append("startDate", this.form.startDate);
          formData.append("endDate", this.form.endDate);

          formData.append("electronicAccessories", this.fileList[0].raw);
          if (this.form.id != null) {
            updateContractManagement(formData).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContractManagement(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
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
      this.$modal.confirm('是否确认删除合同管理编号为"' + ids + '"的数据项？').then(function () {
        return delContractManagement(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/contract/export', {
        ...this.queryParams
      }, `contractManagement_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="关键字" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          clearable
          placeholder="客户/方式"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="拜访方式" prop="visitWay">
        <el-select v-model="queryParams.visitWay" clearable placeholder="请选择拜访方式">
          <el-option
            v-for="dict in dict.type.visit_way"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="拜访时间">
        <el-date-picker
          v-model="daterangeVisitDate"
          end-placeholder="结束日期"
          range-separator="-"
          start-placeholder="开始日期"
          style="width: 240px"
          type="daterange"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['customer:visit:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="visitList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="序号" prop="id"/>
      <el-table-column align="center" label="企业名称" prop="customerName"/>
      <el-table-column align="center" label="联系人名称" prop="contactPersonName"/>
      <el-table-column align="center" label="拜访原因" prop="visitReason"/>
      <el-table-column align="center" label="拜访方式" prop="visitWay">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.visit_way" :value="scope.row.visitWay"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="拜访日期" prop="visitDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.visitDate), '{y}-{m}-{d}' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="交流情况" prop="communication"/>
      <el-table-column align="center" label="录入人" prop="entryPerson"/>
      <el-table-column align="center" label="录入时间" prop="entryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改客户拜访对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="企业名称" prop="customerName">
          <el-select v-model="form.customerName" clearable placeholder="企业名称"
                     @change="handleCustomerChange(form.customerName)">
            <el-option
              v-for="item in customerList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="联系人名称" prop="contactPersonName">
          <el-select v-model="form.contactPersonName" clearable placeholder="请输入联系人名称">
            <el-option
              v-for="item in contactList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="拜访原因" prop="visitReason">
          <el-input v-model="form.visitReason" placeholder="请输入拜访原因"/>
        </el-form-item>
        <el-form-item label="拜访方式" prop="visitWay">
          <el-select v-model="form.visitWay" placeholder="请选择拜访方式">
            <el-option
              v-for="dict in dict.type.visit_way"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拜访时间" prop="visitDate">
          <el-date-picker v-model="form.visitDate"
                          clearable
                          placeholder="请选择拜访时间"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="交流情况" prop="communication">
          <el-input v-model="form.communication" placeholder="请输入交流情况"/>
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
import {addVisit, delVisit, getVisit, listVisit, updateVisit} from "@/api/customer/visit";
import {contactList, customerList} from "@/api/customer/contact";

export default {
  name: "Visit",
  dicts: ['visit_way'],
  data() {
    return {
      customerList: [],
      contactList: [],
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
      // 客户拜访表格数据
      visitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 录入时间时间范围
      daterangeVisitDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        visitReason: null,
        visitWay: null,
        visitDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerName: [
          {required: true, message: "企业名称不能为空", trigger: "blur"}
        ],
        contactPersonName: [
          {required: true, message: "联系人名称不能为空", trigger: "blur"}
        ],
        visitReason: [
          {required: true, message: "拜访原因不能为空", trigger: "blur"}
        ],
        visitWay: [
          {required: true, message: "拜访方式不能为空", trigger: "change"}
        ],
        visitDate: [
          {required: true, message: "拜访时间不能为空", trigger: "blur"}
        ],
        communication: [
          {required: true, message: "交流情况不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    handleCustomerChange(customerName) {
      // 根据选择的企业名称获取联系人列表
      contactList(customerName).then(response => {
        this.contactList = response.data;
      });
    },
    /** 查询客户拜访列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeVisitDate && '' != this.daterangeVisitDate) {
        this.queryParams.params["beginVisitDate"] = this.daterangeVisitDate[0];
        this.queryParams.params["endVisitDate"] = this.daterangeVisitDate[1];
      }
      if (this.customerList != null) {
        // 获取客户列表
        customerList().then(response => {
          this.customerList = response.data;
          // 根据客户列表获取联系人列表
          contactList(this.customerList).then(response => {
            this.contactList = response.enterenterpriseCustomers;
          });
          // 获取拜访列表
          listVisit(this.queryParams).then(response => {
            this.visitList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        });
      }
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
        customerName: null,
        contactPersonName: null,
        visitReason: null,
        visitWay: null,
        visitDate: null,
        communication: null,
        entryPerson: null,
        entryTime: null
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
      this.daterangeVisitDate = [];
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
      this.form.customerName = null; // 重置选中的企业名称
      this.open = true;
      this.title = "添加客户拜访";
      this.$nextTick(() => {
        // 在下一个DOM更新周期中触发获取联系人列表的操作
        this.handleCustomerChange(this.form.customerName); // 获取联系人列表
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getVisit(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户拜访";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVisit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVisit(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除客户拜访编号为"' + ids + '"的数据项？').then(function () {
        return delVisit(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('customer/visit/export', {
        ...this.queryParams
      }, `visit_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

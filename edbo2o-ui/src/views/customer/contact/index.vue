<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="关键字" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          clearable
          placeholder="所属联系人/电话模糊查询"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属企业" prop="enterpriseCustomers">
        <el-select v-model="queryParams.enterpriseCustomers" clearable placeholder="所属企业">
          <el-option
            v-for="item in customerList"
            :key="item"
            :label="item"
            :value="item"
            @keyup.enter.native="handleQuery"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="任职状态" prop="employmentStatus">
        <el-select v-model="queryParams.employmentStatus" clearable placeholder="请选择任职状态">
          <el-option
            v-for="dict in dict.type.employment_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['customer:contact:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['customer:contact:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['customer:contact:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contactList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="序号" prop="id"/>
      <el-table-column align="center" label="企业" prop="enterpriseCustomers"/>
      <el-table-column align="center" label="联系人名字" prop="name"/>
      <el-table-column align="center" label="性别" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.customer_gender" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="年龄" prop="age"/>
      <el-table-column align="center" label="电话" prop="phone"/>
      <el-table-column align="center" label="职位" prop="job"/>
      <el-table-column align="center" label="部门" prop="dept"/>
      <el-table-column align="center" label="任职状态" prop="employmentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.employment_status" :value="scope.row.employmentStatus"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="录入人" prop="entryPerson"/>
      <el-table-column align="center" label="录入时间" prop="entryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['customer:contact:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['customer:contact:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
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

    <!-- 添加或修改客户联系人对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="所属企业" prop="enterpriseCustomers">
          <el-select v-model="form.enterpriseCustomers" clearable placeholder="所属企业">
            <el-option
              v-for="item in customerList"
              :key="item"
              :label="item"
              :value="item"
              @keyup.enter.native="handleQuery"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="联系人名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入联系人名字"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.customer_gender"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄"/>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话"/>
        </el-form-item>
        <el-form-item label="职位" prop="job">
          <el-input v-model="form.job" placeholder="请输入职位"/>
        </el-form-item>
        <el-form-item label="部门" prop="dept">
          <el-input v-model="form.dept" placeholder="请输入部门"/>
        </el-form-item>
        <el-form-item v-if="form.id!=null" label="任职状态" prop="employmentStatus">
          <el-select v-model="form.employmentStatus" placeholder="请选择任职状态">
            <el-option
              v-for="dict in dict.type.employment_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import {addContact, customerList, delContact, getContact, listContact, updateContact} from "@/api/customer/contact";

export default {
  name: "Contact",
  dicts: ['employment_status', 'customer_gender'],
  data() {
    return {
      enterpriseCustomers: null,
      customerList: [],
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
      //customer对象
      customer: {},
      // 客户联系人表格数据
      contactList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enterpriseCustomers: null,
        keyword: null,
        name: null,
        employmentStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          {required: true, message: "主键不能为空", trigger: "blur"}
        ],
        enterpriseCustomers: [
          {required: true, message: "企业不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "联系人名字不能为空", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "性别不能为空", trigger: "change"}
        ],
        age: [
          {required: true, message: "年龄不能为空", trigger: "blur"}
        ],
        phone: [
          {required: true, message: "电话不能为空", trigger: "blur"}
        ],
        job: [
          {required: true, message: "职位不能为空", trigger: "blur"}
        ],
        dept: [
          {required: true, message: "部门不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户联系人列表 */
    getList() {
      this.loading = true;
      listContact(this.queryParams).then(response => {

        this.contactList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      customerList().then(response => {
        this.customerList = response.data;
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
        enterpriseCustomers: null,
        name: null,
        sex: null,
        age: null,
        phone: null,
        job: null,
        dept: null,
        employmentStatus: null,
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
      this.title = "添加客户联系人";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContact(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户联系人";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContact(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContact(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除客户联系人编号为"' + ids + '"的数据项？').then(function () {
        return delContact(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('customer/contact/export', {
        ...this.queryParams
      }, `contact_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

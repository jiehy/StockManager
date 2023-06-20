<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="关键字" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="企业名称/代表人/所属行业"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经营状态" prop="operatingStatus">
        <el-select v-model="queryParams.operatingStatus" clearable placeholder="请选择经营状态">
          <el-option
            v-for="dict in dict.type.customer_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属地区" prop="province">
        <el-select v-model="queryParams.province" clearable placeholder="请选择所属地区">
          <el-option
            v-for="dict in dict.type.customer_province"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="录入时间">
        <el-date-picker
          v-model="daterangeEntryTime"
          end-placeholder="结束日期"
          style="width: 240px"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
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
          v-hasPermi="['customer:information:add']"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          type="primary"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['customer:information:edit']"
          plain
          :disabled="single"
          size="mini"
          icon="el-icon-edit"
          @click="handleUpdate"
          type="success"
        >修改
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="informationList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="序号" prop="id"/>
      <el-table-column align="center" label="企业名称" prop="name"/>
      <el-table-column align="center" label="法定代表人" prop="legalRepresentative"/>
      <el-table-column align="center" label="成立日期" prop="dateOfEstablishment" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dateOfEstablishment, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="所属行业" prop="industry"/>
      <el-table-column align="center" label="所属地区" prop="province">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.customer_province" :value="scope.row.province"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="经营状态" prop="operatingStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.customer_status" :value="scope.row.operatingStatus"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="录入人" prop="entryPerson"/>
      <el-table-column align="center" label="营销人" prop="saleMan"/>
      <el-table-column align="center" label="录入时间" prop="entryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['customer:information:edit']"
            @click="handleUpdate(scope.row)"
            icon="el-icon-edit"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['customer:information:remove']"
            @click="handleDelete(scope.row)"
            icon="el-icon-delete"
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

    <!-- 添加或修改客户基本信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入公司名称"/>
        </el-form-item>
        <el-form-item label="法定代表人" prop="legalRepresentative">
          <el-input v-model="form.legalRepresentative" placeholder="请输入法定代表人"/>
        </el-form-item>
        <el-form-item label="成立日期" prop="dateOfEstablishment">
          <el-date-picker v-model="form.dateOfEstablishment"
                          clearable
                          placeholder="请选择成立日期"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="经营状态" prop="operatingStatus">
          <el-select v-model="form.operatingStatus" placeholder="请选择经营状态">
            <el-option
              v-for="dict in dict.type.customer_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="注册资金" prop="registeredCapital">
          <el-input v-model="form.registeredCapital" placeholder="请输入注册资金"/>
        </el-form-item>
        <el-form-item label="所属行业" prop="industry">
          <el-input v-model="form.industry" placeholder="请输入所属行业"/>
        </el-form-item>
        <el-form-item label="所属省份" prop="province">
          <el-select v-model="form.province" placeholder="请选择所属省份">
            <el-option
              v-for="dict in dict.type.customer_province"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="注册地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入注册地址"/>
        </el-form-item>
        <el-form-item label="经营范围" prop="businessScope">
          <el-input v-model="form.businessScope" placeholder="请输入经营范围"/>
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
  addInformation,
  delInformation,
  getInformation,
  listInformation,
  updateInformation
} from "@/api/customer/information";

export default {
  name: "Information",
  dicts: ['customer_province', 'customer_status'],
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
      // 客户基本信息管理表格数据
      informationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 录入时间时间范围
      daterangeEntryTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: null,
        name: null,
        operatingStatus: null,
        province: null,
        entryTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "公司名称不能为空", trigger: "blur"}
        ],
        legalRepresentative: [
          {required: true, message: "法定代表人不能为空", trigger: "blur"}
        ],
        dateOfEstablishment: [
          {required: true, message: "成立日期不能为空", trigger: "blur"}
        ],
        operatingStatus: [
          {required: true, message: "经营状态不能为空", trigger: "change"}
        ],
        registeredCapital: [
          {required: true, message: "注册资金不能为空", trigger: "blur"}
        ],
        industry: [
          {required: true, message: "所属行业不能为空", trigger: "blur"}
        ],
        province: [
          {required: true, message: "所属省份不能为空", trigger: "change"}
        ],
        address: [
          {required: true, message: "注册地址不能为空", trigger: "blur"}
        ],
        businessScope: [
          {required: true, message: "经营范围不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户基本信息管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeEntryTime && '' != this.daterangeEntryTime) {
        this.queryParams.params["beginEntryTime"] = this.daterangeEntryTime[0];
        this.queryParams.params["endEntryTime"] = this.daterangeEntryTime[1];
      }
      listInformation(this.queryParams).then(response => {
        this.informationList = response.rows;
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
        name: null,
        legalRepresentative: null,
        dateOfEstablishment: null,
        operatingStatus: null,
        registeredCapital: null,
        industry: null,
        province: null,
        address: null,
        businessScope: null,
        entryPerson: null,
        saleMan: null,
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
      this.daterangeEntryTime = [];
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
      this.title = "添加客户基本信息管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInformation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户基本信息管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInformation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInformation(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除客户基本信息管理编号为"' + ids + '"的数据项？').then(function () {
        return delInformation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('customer/information/export', {
        ...this.queryParams
      }, `information_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

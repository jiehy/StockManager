<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程类型" prop="bpmnType">
        <el-select v-model="queryParams.bpmnType" placeholder="请选择流程类型" clearable>
          <el-option
            v-for="dict in dict.type.bpmn_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部署时间">
        <el-date-picker
          v-model="daterangeDeployTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
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
          type="primary"
          plain
          size="mini"
          @click="handleAdd"
          v-hasPermi="['flowdefine:bpmninfo:deploy']"
        >流程文件部署</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
    </el-row>

    <el-table v-loading="loading" :data="bpmninfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程类型" align="center" prop="bpmnType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bpmn_type" :value="scope.row.bpmnType"/>
        </template>
      </el-table-column>
      <el-table-column label="key" align="center" prop="processDefinitio" />
      <el-table-column label="部署时间" align="center" prop="deployTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deployTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="描述信息" align="center" prop="info" />
      <el-table-column label="流程文件" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="resourcesOpen(scope.row,'xml')"
            v-hasPermi="['flowdefine:bpmninfo:resources']"
          >查看</el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程图" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="resourcesOpen(scope.row,'img')"
            v-hasPermi="['flowdefine:bpmninfo:resources']"
          >查看</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleCancel(scope.row)"
            v-hasPermi="['flowdefine:bpmninfo:cancel']"
          >撤销</el-button>
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

    <!-- 添加或修改审核流程定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="流程类型" prop="bpmnType">
          <el-select v-model="form.bpmnType" placeholder="请选择流程类型">
            <el-option
              v-for="dict in dict.type.bpmn_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="请选择文件: ">
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
        <el-form-item label="描述信息" prop="info">
          <el-input v-model="form.info" placeholder="请输入描述信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="流程文件" :visible.sync="xmlOpen" width="800px" append-to-body>
      <div>{{resources}}</div>
    </el-dialog>
    <el-dialog title="流程图" :visible.sync="imgOpen" width="1200px" append-to-body>
      <div v-html="resources"></div>
    </el-dialog>
  </div>
</template>

<script>
import { listBpmninfo, getBpmninfo, resourcesBpmninfo, addBpmninfo, cancelBpmninfo } from "@/api/business/flowdefine/bpmninfo";

export default {
  name: "Bpmninfo",
  dicts: ['bpmn_type'],
  data() {
    return {
      xmlOpen: false,
      imgOpen: false,
      resources: '',
      fileList:[],
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
      // 审核流程定义表格数据
      bpmninfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 描述信息时间范围
      daterangeDeployTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bpmnType: null,
        deployTime: null,
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
    handleChange(file, fileList){
      console.log(file);
      console.log(fileList);
      this.fileList=fileList;
    },
    resourcesOpen(row,type) {
      if('xml' === type) {
        this.xmlOpen = true;
        resourcesBpmninfo(row.id,type).then((req)=>{
          this.resources = req;
        })
      }
      if('img' === type) {
        this.imgOpen = true;
        resourcesBpmninfo(row.id,type).then((req)=>{
          this.resources = req;
        })
      }
    },
    /** 查询审核流程定义列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDeployTime && '' != this.daterangeDeployTime) {
        this.queryParams.params["beginDeployTime"] = this.daterangeDeployTime[0];
        this.queryParams.params["endDeployTime"] = this.daterangeDeployTime[1];
      }
      listBpmninfo(this.queryParams).then(response => {
        this.bpmninfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.fileList=[];
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        bpmnLabel: null,
        bpmnType: null,
        processDefinitio: null,
        deployTime: null,
        version: null,
        info: null
      };
      this.resetForm("form");
      this.fileList=[];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDeployTime = [];
      this.fileList=[];
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
      this.fileList=[];
      this.reset();
      this.open = true;
      this.title = "添加审核流程定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBpmninfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改审核流程定义";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let formData = new FormData();
          formData.append("bpmnType",this.form.bpmnType);
          formData.append("bpmnInfo",this.form.info);
          formData.append("bpmnLabel",this.fileList[0].name);
          formData.append("file",this.fileList[0].raw);
          addBpmninfo(formData).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 撤销按钮操作 */
    handleCancel(row) {
      const id = row.id;
      this.$modal.confirm('是否确认撤销审核流程定义编号为"' + id + '"的数据项？').then(function() {
        return cancelBpmninfo(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("撤销成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowdefine/bpmninfo/export', {
        ...this.queryParams
      }, `bpmninfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

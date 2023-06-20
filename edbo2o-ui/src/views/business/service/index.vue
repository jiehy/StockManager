<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入服务项名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否套餐" prop="carPackage">
        <el-select v-model="queryParams.carPackage" placeholder="请输入是否套餐">
          <el-option
            v-for="dict in dict.type.service_package"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务分类" prop="serviceCatalog">
        <el-select v-model="queryParams.serviceCatalog" placeholder="请输入服务分类">
          <el-option
            v-for="dict in dict.type.service_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请输入审核状态">
          <el-option
            v-for="dict in dict.type.review_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上架状态" prop="saleStatus">
        <el-select v-model="queryParams.saleStatus" placeholder="请输入上架状态">
          <el-option
            v-for="dict in dict.type.on_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          @click="handleAdd"
          v-hasPermi="['service:service:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          size="mini"
          @click="handleAudit"
          v-hasPermi="['service:service:audit']"
        >发起审核
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
    </el-row>

    <el-table v-loading="loading" :data="serviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="服务名称" align="center" prop="name"/>
      <el-table-column label="服务原价" align="center" prop="originalPrice"/>
      <el-table-column label="服务折扣价" align="center" prop="discountPrice"/>
      <el-table-column label="是否套餐" align="center" prop="carPackage">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.service_package" :value="scope.row.carPackage"/>
        </template>
      </el-table-column>
      <el-table-column label="服务分类" align="center" prop="serviceCatalog">
        <template slot-scope="scope">
        <dict-tag :options="dict.type.service_type" :value="scope.row.serviceCatalog"/>
      </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.review_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上架状态" align="center" prop="saleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.on_state" :value="scope.row.saleStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="info" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['service:service:edit']"
            v-if="!(scope.row.auditStatus === 1 || scope.row.saleStatus === 1)"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleSaleOn(scope.row)"
            v-hasPermi="['service:service:saleOn']"
            v-if="!(scope.row.auditStatus === 0 || scope.row.auditStatus === 1 || scope.row.auditStatus === 3 || scope.row.saleStatus === 1)"
          >上架
          </el-button>
          <span
            type="text"
            v-if="scope.row.auditStatus === 1 && scope.row.saleStatus === 0"
          >请等待审核
          </span>
          <el-button
            size="mini"
            type="text"
            @click="handleSaleOff(scope.row)"
            v-hasPermi="['service:service:saleOff']"
            v-if="scope.row.saleStatus === 1"
          >下架
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

    <!-- 添加或修改服务项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="服务名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入服务名称"/>
        </el-form-item>
        <el-form-item label="服务原价" prop="originalPrice">
          <el-input v-model="form.originalPrice" placeholder="请输入服务原价"/>
        </el-form-item>
        <el-form-item label="服务折扣价" prop="discountPrice">
          <el-input v-model="form.discountPrice" placeholder="请输入服务折扣价"/>
        </el-form-item>
        <el-form-item label="是否套餐" prop="carPackage" v-if="form.id == null">
          <el-radio-group v-model="form.carPackage">
            <el-radio
              v-for="dict in dict.type.service_package"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="服务分类" prop="serviceCatalog">
          <el-select v-model="form.serviceCatalog" placeholder="请选择服务分类">
            <el-option
              v-for="dict in dict.type.service_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="info">
          <el-input v-model="form.info" placeholder="请输入备注信息"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 审核服务项对话框 -->
    <el-dialog title="审核服务项" :visible.sync="auditOpen" width="500px" append-to-body>
      <el-form ref="form" :model="auditInfo" :rules="rules" label-width="110px">
        <el-form-item label="服务名称" prop="name">
          <el-input v-model="auditInfo.serviceItem.name" placeholder="请输入服务名称" disabled/>
        </el-form-item>
        <el-form-item label="服务原价" prop="originalPrice">
          <el-input v-model="auditInfo.serviceItem.originalPrice" placeholder="请输入服务原价" disabled/>
        </el-form-item>
        <el-form-item label="服务折扣价" prop="discountPrice">
          <el-input v-model="auditInfo.serviceItem.discountPrice" placeholder="请输入服务折扣价" disabled/>
        </el-form-item>
        <el-form-item label="审核人(老板)" prop="shopOwner">
          <el-select v-model="auditInfo.shopOwnerId" placeholder="请选择审核人">
            <el-option
              v-for="item in auditInfo.shopOwner"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="审核人(财务)" prop="finances" v-if="auditInfo.serviceItem.discountPrice >= auditInfo.limit">
        <el-select v-model="auditInfo.financeId" placeholder="请选择审核人">
          <el-option
            v-for="item in auditInfo.finances"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId"
          />
        </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="info">
          <el-input v-model="auditInfo.info" placeholder="请输入备注信息"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAudit">确 定</el-button>
        <el-button @click="cancelAudit">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listService,
    getService,
    delService,
    addService,
    updateService,
    saleOn,
    saleOff,
    auditInfoService,
    auditService,
  } from "@/api/business/service/service";

  export default {
    name: "Service",
    dicts: ['service_type', 'service_package', 'review_status', 'on_state'],
    data() {
      return {
        auditInfo: {
          serviceItem: {},
          shopOwnerId: null,
          financeId: null,
          shopOwner: [],
          finances: [],
          info:'',
        },
        rows: [],
        // 审核服务项对话框
        auditOpen: false,
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
        // 服务项表格数据
        serviceList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          originalPrice: null,
          discountPrice: null,
          carPackage: null,
          info: null,
          serviceCatalog: null,
          auditStatus: null,
          saleStatus: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [
            {required: true, message: "服务名称不能为空", trigger: "blur"}
          ],
          originalPrice: [
            {required: true, message: "服务原价不能为空", trigger: "blur"}
          ],
          discountPrice: [
            {required: true, message: "服务折扣价不能为空", trigger: "blur"}
          ],
          carPackage: [
            {required: true, message: "是否套餐不能为空", trigger: "blur"}
          ],
          serviceCatalog: [
            {required: true, message: "服务分类不能为空", trigger: "blur"}
          ],
          shopOwner: [
            {required: true, message: "审核人(老板)不能为空", trigger: "blur"}
          ],
          finances: [
            {required: true, message: "审核人(财务)不能为空", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询服务项列表 */
      getList() {
        this.loading = true;
        listService(this.queryParams).then(response => {
          this.serviceList = response.rows;
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
          originalPrice: null,
          discountPrice: null,
          carPackage: null,
          info: null,
          createTime: null,
          serviceCatalog: null,
          auditStatus: 0,
          saleStatus: 0,
          boss: 0,
          finances: 0,
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
        this.ids = selection.map(item => item.id);
        this.rows = selection.map(item => item);
        this.single = selection.length !== 1;
        this.multiple = !selection.length;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加服务项";
      },
      /** 发起审核按钮操作 */
      handleAudit() {
        if(this.rows.length === 0) {
          this.$modal.msgError("请选择需要审核的套餐");
          return;
        }
        if(this.rows.length !== 1) {
          this.$modal.msgError("一次只能审核一个");
          return;
        }
        if(this.rows[0].carPackage !== 1) {
          this.$modal.msgError("必须是套餐才能审核");
          return;
        }
        if(!(this.rows[0].auditStatus === 0 || this.rows[0].auditStatus === 3)) {
          this.$modal.msgError("必须是初始化/重新编辑才能审核");
          return;
        }
        auditInfoService(this.rows[0].id).then((request)=>{
          this.auditOpen = true;
          this.auditInfo = request.data;
        })
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getService(id).then(response => {
          this.form = response.data;
          this.form.serviceCatalog = response.data.serviceCatalog.toString();
          this.form.carPackage = response.data.carPackage.toString();
          this.open = true;
          this.title = "修改服务项";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateService(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addService(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 提交审核按钮操作 */
      submitAudit() {
        let params = {
          id: this.rows[0].id,
          shopOwnerId: this.auditInfo.shopOwnerId,
          financeId: this.auditInfo.financeId,
          info: this.auditInfo.info,
        };
        auditService(params).then(()=>{
          this.auditOpen = false;
          this.$modal.msgSuccess("审核发起成功");
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除服务项编号为"' + ids + '"的数据项？').then(function () {
          return delService(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 上架按钮操作 */
      handleSaleOn(row) {
        const ids = row.id;
        this.$modal.confirm('" ' + row.name + ' "' + ' 确认上架吗').then(function () {
          return saleOn(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        }).catch(() => {
        });
      },
      /** 下架按钮操作 */
      handleSaleOff(row) {
        const ids = row.id;
        this.$modal.confirm('" ' + row.name + ' "' + ' 确认下架吗').then(function () {
          return saleOff(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("下架成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('service/service/export', {
          ...this.queryParams
        }, `service_${new Date().getTime()}.xlsx`)
      },
      cancelAudit() {
        this.auditOpen = false;
        this.auditInfo =  {
          serviceItem: {},
          shopOwnerId: null,
          financeId: null,
          shopOwner: [],
          finances: [],
        }
      }
    },
  };
</script>

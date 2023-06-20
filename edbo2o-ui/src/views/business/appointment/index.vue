<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模糊搜索" prop="licensePlate">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话号码" prop="licensePlate">
        <el-input
          v-model="queryParams.customerPhone"
          placeholder="请输入电话号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号码" prop="licensePlate">
        <el-input
          v-model="queryParams.licensePlate"
          placeholder="请输入车牌号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约时间" prop="dateRange">
        <el-date-picker
          v-model="dateRange"
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
          v-hasPermi="['appointment:appointment:add']"
        >新增
        </el-button>
        <el-button
          type="danger"
          plain
          size="mini"
          @click="handleBatchDelete"
          v-hasPermi="['appointment:appointment:batchDelete']"
        >批量删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
    </el-row>

    <el-table v-loading="loading" :data="appointmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="客户名称" align="center" prop="customerName"/>
      <el-table-column label="联系方式" align="center" prop="customerPhone"/>
      <el-table-column label="预约时间" align="center" prop="appointmentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.appointmentTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到店时间" align="center" prop="actualArrivalTime" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.actualArrivalTime != null">{{ parseTime(scope.row.actualArrivalTime) }}</span>
          <span v-if="scope.row.actualArrivalTime == null">未到店</span>
        </template>
      </el-table-column>
      <el-table-column label="车牌号码" align="center" prop="licensePlate"/>
      <el-table-column label="汽车类型" align="center" width="80" prop="carSeries"/>
      <el-table-column label="服务类型" align="center" width="80" prop="serviceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.service_type" :value="scope.row.serviceType"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="info" :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.appointment_type" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="165"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleArrl(scope.row)"
            v-hasPermi="['appointment:appointment:arrl']"
            v-if="scope.row.status === 0"
          >到店
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['appointment:appointment:edit']"
            v-if="scope.row.status === 0"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleSettlement(scope.row)"
            v-hasPermi="['appointment:appointment:settlement']"
            v-if="scope.row.status === 1 || scope.row.status === 4 || scope.row.status === 5"
          >{{scope.row.status === 5 ? '明细' : '结算'}}
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleSync(scope.row)"
            v-hasPermi="['appointment:appointment:sync']"
          >同步信息
          </el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                       v-hasPermi="['appointment:appointment:close', 'appointment:appointment:remove']">
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"/>更多
                </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleClose" icon="el-icon-close" v-if="scope.row.status === 0"
                                v-hasPermi="['appointment:appointment:close']">取消
              </el-dropdown-item>
              <el-dropdown-item command="handleDelete" icon="el-icon-delete"
                                v-hasPermi="['appointment:appointment:remove']">删除
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 添加或修改养修信息预约对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户姓名" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户姓名"/>
        </el-form-item>
        <el-form-item label="联系方式" prop="customerPhone">
          <el-input v-model="form.customerPhone" placeholder="请输入联系方式"/>
        </el-form-item>
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker clearable
                          v-model="form.appointmentTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:dd"
                          placeholder="请选择预约时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车牌号码" prop="licensePlate">
          <el-input v-model="form.licensePlate" placeholder="请输入车牌号码"/>
        </el-form-item>
        <el-form-item label="汽车类型" prop="carSeries">
          <el-input v-model="form.carSeries" placeholder="请输入汽车类型"/>
        </el-form-item>
        <el-form-item label="服务类型" prop="serviceType">
          <el-select v-model="form.serviceType" placeholder="请选择服务类型">
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
  </div>
</template>

<script>
  import {
    listAppointment,
    getAppointment,
    delAppointment,
    addAppointment,
    updateAppointment,
    arrlAppointment,
    closeAppointment,
    batchDelAppointment,
    getSettlement,
    syncAppointment
  } from "@/api/business/appointment/appointment";

  export default {
    name: "Appointment",
    dicts: ['service_type', 'appointment_type'],
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
        // 养修信息预约表格数据
        appointmentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        dateRange:[],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          customerName: null,
          customerPhone: null,
          appointmentTime: null,
          actualArrivalTime: null,
          licensePlate: null,
          carSeries: null,
          serviceType: null,
          info: null,
          status: null,
          isdelete: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          customerName: [
            {required: true, message: "姓名不能为空", trigger: "blur"}
          ],
          customerPhone: [
            {required: true, message: "号码不能为空", trigger: "blur"},
            {min: 11, max: 11, message: '号码长度要等于11位', trigger: 'blur'}
          ],
          licensePlate: [
            {required: true, message: "车牌号码不能为空", trigger: "blur"},
          ],
          carSeries: [
            {required: true, message: "汽车类型不能为空", trigger: "blur"},
          ],
          appointmentTime: [
            {required: true, message: "预约时间不能为空", trigger: "blur"},
          ],
          serviceType: [
            {required: true, message: "预约时间不能为空", trigger: "blur"},
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      handleCommand(command, row) {
        switch (command) {
          case "handleClose":
            this.handleClose(row);
            break;
          case "handleDelete":
            this.handleDelete(row);
            break;
          default:
            break;
        }
      },
      /** 查询养修信息预约列表 */
      getList() {
        this.loading = true;
        listAppointment(this.addDateRange(this.queryParams,this.dateRange)).then(response => {
          this.appointmentList = response.rows;
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
          customerName: null,
          customerPhone: null,
          appointmentTime: null,
          actualArrivalTime: null,
          licensePlate: null,
          carSeries: null,
          serviceType: null,
          createTime: null,
          info: null,
          status: 0,
          isdelete: null,
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
        this.dateRange = [];
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
        this.title = "添加养修信息预约";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getAppointment(id).then(response => {
          this.form = response.data;
          this.form.serviceType = response.data.serviceType.toString();
          this.open = true;
          this.title = "修改养修信息预约";
        });
      },
      /** 同步按钮操作 */
      handleSync(row) {
        syncAppointment(row).then(response => {
          this.$modal.msgSuccess("同步成功");
        });
      },
      /** 结算/明细按钮操作 */
      handleSettlement(row) {
        if(row.status === 5) {
          const id = row.id;
          getSettlement(id).then((response) => {
            this.$router.push({
              path:"/business/statement/item",
              query:{id: response.data}
            });
          }).catch(() => {
          });
        } else {
          const id = row.id;
          this.$modal.confirm('是否确认结算 " ' + row.customerName + ' " 客户的预约单?').then(function () {
            return getSettlement(id);
          }).then((response) => {
            this.$router.push({
              path:"/business/statement/item",
              query:{id: response.data}
            });
          }).catch(() => {
          });
        }
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateAppointment(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addAppointment(this.form).then(response => {
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
        const id = row.id;
        this.$modal.confirm('是否确认删除 " ' + row.customerName + ' " 客户的预约信息?').then(function () {
          return delAppointment(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 批量删除按钮操作 */
      handleBatchDelete() {
        const ids = this.ids;
        this.$modal.confirm('是否确认删除养修信息预约编号为"' + ids + '"的数据项？').then(function () {
          return batchDelAppointment(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 到店按钮操作 */
      handleArrl(row) {
        const id = row.id;
        this.$modal.confirm('是否确认"' + id + '"的数据项到店？').then(function () {
          return arrlAppointment(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("到店成功");
        }).catch(() => {
        });
      },
      /** 用户取消预约按钮操作 */
      handleClose(row) {
        const id = row.id;
        this.$modal.confirm('是否确认"' + id + '"的数据项取消预约？').then(function () {
          return closeAppointment(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("取消成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('appointment/appointment/export', {
          ...this.queryParams
        }, `appointment_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>

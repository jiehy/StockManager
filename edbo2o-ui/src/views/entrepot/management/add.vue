<template>
  <div class="app-container">

    <!-- 添加或修改仓库管理对话框 -->
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="text-align: center">
        <el-form-item label="仓库名称" prop="entrepotName" height="129px">
          <el-input
            type="textarea"
            autosize
            placeholder="请输入内容"
            v-model="form.entrepotName">
          </el-input>
        </el-form-item>
        <el-form-item label="地址" prop="entrepotAddress"  style="text-align: center">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="form.entrepotAddress">
          </el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="submitForm">立即提交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
  </div>
</template>

<script>
import { listManagement, addManagement} from "@/api/entrepot/management";

export default {
  name: "Management",
  data() {
    return {
      entrepotName: '',
      entrepotAddress: '',
      message: '',
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        entrepotName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" },
          { max: 50, message: '仓库名称长度不能超过50个字', trigger: 'blur' }
        ],
        entrepotAddress: [
          { required: true, message: "仓库地址不能为空", trigger: "blur" },
          { max: 250, message: '仓库地址长度不能超过250个字', trigger: 'blur' }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询仓库管理列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows;
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
        entrepotName: null,
        entrepotAddress: null
      };
      this.resetForm("form");
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            addManagement(this.form).then(response => {
              this.$modal.alert("操作成功","温馨提示");
              this.$router.push({
                path:'/entrepot/management/index'
              });
            }).catch(() => {});
          }
      });
    },
  }
};
</script>

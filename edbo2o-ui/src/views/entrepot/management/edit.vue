<template>
  <div class="app-container">

    <!-- 添加或修改仓库管理对话框 -->
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="仓库名称" prop="entrepotName">
        <el-input
          type="textarea"
          placeholder="请输入内容"
          v-model="form.entrepotName">
        </el-input>
      </el-form-item>
      <el-form-item label="地址" prop="entrepotAddress">
        <el-input
          type="textarea"
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
  import { getManagement,delManagement,updateManagement } from "@/api/entrepot/management";

  export default {
    name: "Management",
    data() {
      return {
        entrepotName: '',
        entrepotAddress: '',
        //仓库Id
        id: this.$route.query.id,
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
        },
        // 查询参数
        queryParams: {
          id: 4,
        },
      };
    },
    created() {
      this.getList();
    },
    mounted() {
      this.ReInit();
    },
    methods: {
      /** 查询仓库管理列表 */
      getList() {
        this.loading = true;
        console.log(this.queryParams.id);
        getManagement(this.queryParams.id).then(response => {
          this.form = response.data;
        });
      },
      ReInit() {
        if (this.id!= null) {
          getManagement(this.id).then(response => {
            this.form = response.data;
          });
        }else {
          getManagement(this.queryParams.id).then(response => {
            this.form = response.data;
          });
        }
      },
      // 取消按钮
      cancel() {
        this.form = {
          id: null,
          entrepotName: null,
          entrepotAddress: null
        };
        this.resetForm("form");
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
            updateManagement(this.form).then(response => {
              this.$modal.alert("操作成功","温馨提示");
              this.$router.push({
                path:'/entrepot/management/index'
              });
            }).catch(() => {});
          }
        });
      }
    }
  };
</script>

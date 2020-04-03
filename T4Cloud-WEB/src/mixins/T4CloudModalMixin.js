/**
 *  弹框
 */
import { removeSame } from "@/utils/util";
import { mapGetters } from "vuex";
import { httpClient, GET } from "@/utils/http";

export const T4CloudModalMixin = {
  data() {
    return {
      treeList: [],
      title: "",
      isShow: false,
      loading: false,
      disableSubmit: false,
      forms: {},
      relationForm: {},
      relationData: {}
    };
  },
  computed: {
    ...mapGetters(["device"])
  },
  props: {
    refresh: {
      type: Boolean,
      default: false
    }
  },
  created() {},
  methods: {
    //新增方法
    add() {
      //新增的时候合并父类的值
      this.edit(removeSame(this.initForm(), this.relationForm));
    },
    //编辑
    edit(record) {
      //填充记录，以后者为准
      this.forms = removeSame(this.initForm(), record);
      if (this.refresh == true && this.forms.id) {
        this.getDetail();
      }
      this.isShow = true;
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.save();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 保存或更新
    save() {
      let url = "";
      let method = "";
      //判断是保存还是更新
      if (!this.forms.id) {
        if (!this.url.save) {
          this.$message.error("保存需要配置data中url.save的属性!");
          return;
        }
        url += this.url.save;
        method = "PUT";
      } else {
        if (!this.url.update) {
          this.$message.error("编辑需要配置data中url.update的属性!");
          return;
        }
        url += this.url.update;
        method = "POST";
      }
      //进行数据预处理
      if (!this.beforeSave()) {
        return;
      }
      let data = { ...this.forms };
      //loading开启
      this.loading = true;
      //调用接口
      httpClient(method, url, data)
        .then(res => {
          if (res.success) {
            this.$message.success(res.message);
            this.$emit("success");
            this.close();
          } else {
            this.$message.warning(res.message);
          }
        })
        .finally(() => {
          this.loading = false;
        });
    },
    //预留的保存前事件，重写此方法可以在保存前对数据做一些操作
    beforeSave() {
      return true;
    },
    //关闭窗口
    close() {
      //如果有树状选择框的话，清除该组件的选择项
      if (this.$refs.treeSelect) {
        this.$refs.treeSelect.clearHandle();
      }
      // 如果有表单的话，清除表单的值
      if (this.$refs.baseForm) {
        this.$refs["baseForm"].resetFields();
      }
      this.isShow = false;
    },
    // 根据id获取详情
    getDetail() {
      if (!this.url.detail) {
        this.$message.error("刷新数据需要配置data中url.detail的属性");
        return;
      }
      this.loading = true;
      const params = {
        id: this.forms.id
      };
      GET(this.url.detail, params).then(res => {
        if (res.success) {
          this.forms = removeSame(this.initForm(), res.result);
        } else {
          this.$message.warning(res.message);
        }
        this.loading = false;
      });
    },
    // 检查确认重复信息
    ckeckFieldInfo(key, value) {
      if (!this.url.check) {
        this.$message.error("检查重复数据需要配置data中url.check的属性");
        return;
      }
      const data = {
        key,
        value
      };
      return new Promise(resolve => {
        GET(this.url.check, data).then(res => {
          if (res.success) {
            resolve(res.result);
          } else {
            resolve(false);
          }
        });
      });
    }
  }
};

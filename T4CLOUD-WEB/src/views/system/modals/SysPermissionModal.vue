<template>
  <div class="SysPermissionModal">
    <el-dialog :title="title" :visible.sync="isShow">
      <el-form :model="forms" :rules="rules" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="菜单类型">
          <el-radio-group v-model="forms.menuType" :disabled="disableSubmit">
            <el-radio :label="0">一级菜单</el-radio>
            <el-radio :label="1">子菜单</el-radio>
            <el-radio :label="2">按钮权限</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="forms.name" :disabled="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" prop="parentId" v-if="forms.menuType!=0">
          <TTreeSelect :options="tableData" @getValue="handleTreeSelect" :value="forms.parentId" :disabled="disableSubmit"></TTreeSelect>
        </el-form-item>
        <el-form-item label="菜单路径" prop="url">
          <el-input v-model="forms.url" :disabled="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="前端组件" prop="component" v-if="forms.menuType!=2">
          <el-input v-model="forms.component" :disabled="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="授权标示" v-if="forms.menuType==2">
          <el-input v-model="forms.perms" placeholder="如:system:SysPermission:ADD" :disabled="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" v-if="forms.menuType!=2">
          <el-input v-model="forms.icon" :disabled="disableSubmit">
            <el-button slot="append" icon="el-icon-setting" @click="iconClick" :disabled="disableSubmit"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="forms.sortNo" :disabled="disableSubmit"></el-input-number>
        </el-form-item>
        <el-form-item label="隐藏路由" v-if="forms.menuType!=2">
          <el-switch v-model="forms.hidden" :active-value="1" :inactive-value="0" :disabled="disableSubmit">
          </el-switch>
        </el-form-item>
        <el-form-item label="内部打开" v-if="forms.menuType!=2">
          <el-switch v-model="forms.openType" :active-value="0" :inactive-value="1" :disabled="disableSubmit">
          </el-switch>
        </el-form-item>
        <el-form-item label="状态" v-if="forms.menuType==2">
          <el-switch v-model="forms.status" :active-value="0" :inactive-value="1" :disabled="disableSubmit">
          </el-switch>
        </el-form-item>
        <el-form-item style="text-align:right;">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <TIconSelect ref="iconModal" @onSelectIcon="handSelectIcon"></TIconSelect>
  </div>
</template>
<script>
import TTreeSelect from '../../../components/T4Cloud/TTreeSelect'
import TIconSelect from '../../../components/T4Cloud/TIconSelect'
import { httpClient } from '@/utils/http'
import { removeSame } from '@/utils/util'
export default {
  name: 'SysPermissionModal',
  data() {
    return {
      title: '',
      isShow: false,
      loading: false,
      disableSubmit: false,
      forms: {},
      rules: {
        name: { required: true, message: '请输入菜单名称', trigger: 'blur' },
        url: { required: true, message: '请输入菜单路径', trigger: 'blur' },
        component: { required: true, message: '请输入前端组件', trigger: 'blur' },
        parentId: { required: true, message: '请选择父级菜单', trigger: 'blur' }
      },
      url: {
        save: '/T4Cloud-System/SysPermission/save',
        update: '/T4Cloud-System/SysPermission/update'
      }
    }
  },
  props: {
    tableData: {
      type: Array,
      default: () => { return [] }
    }
  },
  components: {
    TTreeSelect,
    TIconSelect
  },
  created() {
    this.initForm()
  },
  methods: {
    initForm() {
      this.forms = {
        id: '',
        parentId: '',
        menuType: 0,
        name: '',
        url: '',
        component: '',
        icon: '',
        sortNo: '',
        phone: '',
        perms: '',
        hidden: 0, // 隐藏路由: 0-隐藏,1-展示
        openType: 0, // 打开方式（0-框架内打开，1-新的页面打开）
        status: 0 // 按钮权限状态(0无效1有效)
      }
    },
    //新增方法
    add() {
      this.initForm()
      this.edit({ status: 1 })
    },
    //编辑
    edit(record) {
      this.initForm()
      this.forms = removeSame(this.forms, record)//Object.assign({}, record)
      this.isShow = true
      // this.$nextTick(() => {
      //   this.$refs['ruleForm'].setFieldsValue(pick(this.model, 'cityName', 'jobCount', 'status', 'description'))
      //   //时间格式化
      // })
    },
    //关闭窗口
    close() {
      this.isShow = false
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.save()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 保存或更新
    save() {
      this.loading = true
      let url = ''
      let method = ''
      let data = { ...this.forms }
      //判断是保存还是更新
      if (!this.forms.id) {
        url += this.url.save
        method = 'PUT'
      } else {
        url += this.url.update
        method = 'POST'
      }
      //调用接口
      httpClient(method, url, data)
        .then(res => {
          if (res.success) {
            this.$message.success(res.message)
            this.$emit('success')
            this.close()
          } else {
            this.$message.warning(res.message)
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 
    handleTreeSelect(value) {
      console.log(value)
      this.forms.parentId = value
    },
    // 菜单选择 
    iconClick(){
      this.$refs.iconModal.show = true
    },
    // icon选中返回
    handSelectIcon(icon){
      this.forms.icon = icon
    }
  }
}
</script>

<style lang="scss" scoped>
.SysPermissionModal {
}
</style>

<template>
  <div :class="{fixedDialog:true,fixedHeightDialog:device!='mobile'}">
    <el-dialog :title="title" :visible.sync="isShow" :fullscreen="device=='mobile'" :before-close="close">
      <el-form class="dialogForm" :model="forms" :rules="rules" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="菜单类型">
          <el-radio-group v-model="forms.menuType" :disabled="disableSubmit">
            <el-radio :label="0">一级菜单</el-radio>
            <el-radio :label="2">按钮权限</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="forms.name" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <!-- <el-form-item label="上级菜单" :prop="forms.menuType==0?'':'parentId'">
          <TTreeSelect ref="treeSelect" :options="treeList" v-model="forms.parentId" :disabled="disableSubmit"></TTreeSelect>
        </el-form-item> -->
        <el-form-item label="上级菜单" :prop="forms.menuType==0?'':'parentId'">
          <t-tree-select ref="treeSelect" :options="treeList" v-model="forms.parentId" :disabled="disableSubmit"></t-tree-select>
        </el-form-item>
        <el-form-item label="菜单路径" prop="url" v-show="forms.menuType!=2">
          <el-input v-model="forms.url" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="前端组件" prop="component" v-show="forms.menuType!=2">
          <el-input v-model="forms.component" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="授权标示" v-show="forms.menuType==2">
          <el-input v-model="forms.perms" placeholder="如:system:SysPermission:ADD" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" v-show="forms.menuType!=2">
          <el-input v-model="forms.icon" :readonly="disableSubmit">
            <el-button slot="append" icon="el-icon-setting" @click="iconClick" :disabled="disableSubmit"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="forms.sortNo" :disabled="disableSubmit"></el-input-number>
        </el-form-item>
        <el-form-item label="是否隐藏" v-show="forms.menuType!=2">
          <t-dict v-model="forms.hidden" dictCode="hidden" :disabled="disableSubmit"></t-dict>
        </el-form-item>
        <el-form-item label="打开方式" v-show="forms.menuType!=2">
          <t-dict v-model="forms.openType" type="radio" dictCode="open_type" :disabled="disableSubmit"></t-dict>
        </el-form-item>
        <el-form-item label="状态">
          <t-dict v-model="forms.status" type="radio" dictCode="common_status" :readonly="disableSubmit"></t-dict>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog__footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit">确定</el-button>
      </div>
    </el-dialog>
     <el-dialog title="图标选择" :visible.sync="iconShow" :fullscreen="device=='mobile'">
        <!-- <TIconSelect ref="iconModal" @onSelectIcon="handChangeIcon"></TIconSelect> -->
        <t-icon-select @onChangeIcon="handChangeIcon"></t-icon-select>
     </el-dialog>
  </div>
</template>
<script>
// import TTreeSelect from '@/components/T4Cloud/TTreeSelect'
// import TIconSelect from '@/components/T4Cloud/TIconSelect'
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name: 'SysPermissionModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
        name: { required: true, message: '请输入菜单名称', trigger: 'blur' },
        url: { required: true, message: '请输入菜单路径', trigger: 'blur' },
        component: { required: false, message: '请输入前端组件', trigger: 'blur' },
        parentId: { required: true, message: '请选择父级菜单', trigger: 'blur' },
      },
      url: {
        detail: '/T4Cloud-System/SysPermission/detail',
        save: '/T4Cloud-System/SysPermission/save',
        update: '/T4Cloud-System/SysPermission/update'
      },
      iconShow: false
    }
  },
  props: {
    tableData: {
      type: Array,
      default: () => { return [] }
    },
    refresh: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    tableData: {
      handler(newValue) {
        let arr = JSON.parse(JSON.stringify(newValue));
        this.treeList = this.initTreeList(arr)
      },
      deep: true
    }
  },
  components: {
    // TTreeSelect,
    // TIconSelect
  },
  methods: {
    initForm() {
      return {
        id: null,
        parentId: null,
        menuType: 0,
        name: null,
        url: null,
        component: null,
        icon: null,
        sortNo: null,
        perms: null,
        hidden: 1, // 隐藏路由: 0-隐藏,1-展示
        openType: 0, // 打开方式（0-框架内打开，1-新的页面打开）
        status: 1 // 按钮权限状态(0无效1有效)
      }
    },
    //重写Tree数据，过滤其中的按钮选项
    initTreeList(data) {
      let tree = []
      data.forEach(item => {
        if (item.menuType != 2) {
          if (item.children && item.children.length > 0) {
            item.children = this.initTreeList(item.children)
          }
          tree.push(item)
        }
      })
      return tree
    },
    // 图标选择框
    iconClick() {
      this.iconShow = true
      // this.$refs.iconModal.show = true
    },
    // icon选中返回
    handChangeIcon(icon) {
      this.forms.icon = icon
      this.iconShow = false
    }
  }
}
</script>

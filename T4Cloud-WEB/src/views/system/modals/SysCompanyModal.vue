<template>
  <div>
      <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
          <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
            <el-form-item label="公司名称" prop="name">
                <el-input placeholder="输入公司名" v-model="forms.name" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="英文名" prop="nameEn">
                <el-input placeholder="输入英文名" v-model="forms.nameEn" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="名称缩写" prop="nameAbbr">
                <el-input placeholder="输入公司名缩写" v-model="forms.nameAbbr" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description">
                <el-input placeholder="输入描述" v-model="forms.description" type="textarea" :rows="2" :readonly="disableSubmit">
                </el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input placeholder="输入联系方式" v-model="forms.phone" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="传真" prop="fax">
                <el-input placeholder="输入传真" v-model="forms.fax" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
                <el-input placeholder="输入地址" type="textarea" v-model="forms.address" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input placeholder="输入备注" v-model="forms.remark" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <t-dict v-model="forms.status" type="radio" dictCode="common_status" :readonly="disableSubmit"></t-dict>
            </el-form-item>
          </el-form>
          <span slot="footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['system:SysCompany:ADD','system:SysCompany:EDIT']">确定</el-button>
          </span>
      </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name:'SysCompanyModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
        name: { required: true, message: '公司名不允许为空', trigger: 'blur' },
      },
      url: {
          detail: '/T4Cloud-System/SysCompany/detail',
          save: '/T4Cloud-System/SysCompany/save',
          update: '/T4Cloud-System/SysCompany/update',
      }
    }
  },
  methods: {
      //初始化form表单字段
      initForm() {
          return {
              id: null,
              name: null, // 公司名
              nameEn: null, // 英文名
              nameAbbr: null, // 公司名缩写
              description: null, // 描述
              phone: null, // 联系方式
              fax: null, // 传真
              address: null, // 地址
              remark: null, // 备注
              status: 1, // 状态
          }
      },
  }
}
</script>

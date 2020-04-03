<template>
  <div>
    <el-dialog width="600px" :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">

        <el-row>
          <el-col :span="12">
            <el-form-item label="权限策略" prop="policy">
              <t-dict v-model="forms.policy" dictCode="policy_type" :readonly="disableSubmit"></t-dict>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="有效天数" prop="expire">
              <el-input-number placeholder="输入有效天数" v-model="forms.expire" :disabled="disableSubmit">
              </el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="资源上传" prop="url">
          <el-input placeholder="输入完整资源路径" v-model="forms.url" :readonly="disableSubmit"></el-input>
          <!--                <t-upload :policy="forms.policy" :expire="forms.expire"></t-upload>-->
          <div class="tips">这里应该是个upload组件（类似pic-upload组件，但是这个是通用的传啥都行，组件定义两个参数【公开或私有/有效天数】）</div>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="close">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name: 'SupResourceUploadModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
    }
  },
  methods: {
  }
}
</script>

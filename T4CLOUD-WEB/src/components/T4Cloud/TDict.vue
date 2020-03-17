<template>
  <span v-loading="loading">
    <!--   如果不选择样式，会根据字典的数量自动选择     -->
    <!--   switch样式    -->
    <el-switch v-if="(!type && dictOptions.length == 2) || type=='switch'"  v-model="dictValue"  :active-value="dictOptions[0].value" :inactive-value="dictOptions[1].value"
               :active-text="dictOptions[0].text" :inactive-text="dictOptions[1].text" :disabled="readonly" @change="handleInput">
    </el-switch>
    <!--   radio样式    -->
    <el-radio-group :size="size" v-model="dictValue" v-if="(!type && dictOptions.length <= 4 && dictOptions.length > 2) || type=='radio'"
      @change="handleInput" :disabled="readonly" :readonly="readonly">
      <el-radio-button v-for="(item, key) in dictOptions" :key="key" :label="item.value">{{item.text}}</el-radio-button>
    </el-radio-group>
    <!--   select样式     -->
    <el-select v-model="dictValue" :size="size" v-if="(!type && dictOptions.length >= 5) || type=='select' ||type=='list'"
      placeholder="请选择" :disabled="readonly" :readonly="readonly">
      <el-option v-for="item in dictOptions" :key="item.value" :label="item.text" :value="item.value"></el-option>
    </el-select>
  </span>
</template>

<script>
import { loadDictValue } from '@/apis/api'

export default {
  name: 'TDict',
  props: {
    dictCode: String,
    placeholder: String,
    readonly: Boolean,
    value: Number,
    size: String,
    type: String
  },
  data() {
    return {
      loading: false,
      dictOptions: [
        {
          value: 1,
          text: ''
        }, {
          value: 2,
          text: ''
        }
      ],
      dictValue: this.value
    }
  },
  watch: {
    //监听字典code的变化
    dictCode: {
      immediate: true,
      handler() {
        this.loadDict()
      }
    },
    //监听父组件key值的变化
    value: {
      immediate: true,
      handler(n) {
        this.dictValue = n
        // console.log("value",n)
      }
    },
  },
  methods: {
    //根据字典Code, 初始化字典数组
    loadDict() {
      this.loading = true
      loadDictValue(this.dictCode).then(res => {
        if (res.success) {
          // console.log(res.result);
          this.dictOptions = res.result;
        } else {
          this.$message.error("获取字典失败");
        }
        this.loading = false
      }).catch(val => {
        this.$message.error("获取字典失败");
        console.log(val);
      })
    },
    //处理回调事件，向父组件传值
    handleInput(e) {
      let val = e
      this.$emit('input', val)
      this.$emit('change', val)
    },
  }
}
</script>

<style scoped>
</style>

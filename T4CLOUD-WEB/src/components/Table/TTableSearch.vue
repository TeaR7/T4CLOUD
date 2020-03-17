<template>
  <div class="table-search" v-if="this.searchArr.length > 0">
    <template v-for="(item, index) in searchArr">
      <div v-if="item.query" :key="item.key" :class="{lineDiv:true,hidden:!(index<(isSpread?searchArr.length:4))}">
        <span class="lineSpan">{{item.name}}：</span>
        <!-- 是否数字类型 -->
        <el-input-number v-if="item.type == 'Number'" class="lineInput" :placeholder="'请输入' +item.name" clearable
          v-model="queryParam[item.key]" @change="searchClick"></el-input-number>
        <!-- 是否需要使用字典 -->
        <t-dict v-else-if="item.dict" class="lineInput" :placeholder="'请选择' +item.name" :dictCode="item.dict"
          clearable v-model="queryParam[item.key.split('_')[0]]" :type="item.dictType" @change="searchClick"></t-dict>
        <!-- 复发识别的都当做普通类型 -->
        <el-input v-else class="lineInput" :placeholder="'请输入'+item.name" clearable v-model="queryParam[item.key]"
          @change="searchClick"></el-input>
      </div>
    </template>
    <!-- <span class="fromSpan">状态： </span>
        <el-select v-model="selectStatus" placeholder="请选择" @change="getOrderList">
          <el-option v-for="item in statusList" :key="item.id" :label="item.text" :value="item.id">
          </el-option>
        </el-select> -->
    <div class="lineDiv">
      <el-button type="primary" @click="searchClick">查询</el-button>
      <el-button type="primary" @click="clearClick">重置</el-button>
      <el-button v-if="searchArr.length>4" type="text" @click="spreadClick">{{isSpread?'收缩':'展开'}}
        <i :class="isSpread?'el-icon-arrow-down':'el-icon-arrow-up'"></i>
      </el-button>
    </div>

  </div>
</template>
<script>
export default {
  data() {
    return {
      isSpread: false,
      searchArr: []
    }
  },
  props: {
    searchCol: {
      type: Array,
      default: null
    },
    queryParam: {
      type: Object,
      default: null
    },
  },
  created(){
    var arr = []
    this.searchCol.forEach(item => {
      if(item.query){
        arr.push(item)
      }
    });
    this.searchArr = arr
  },
  methods: {
    // 查询
    searchClick() {
      this.$emit('search', this.$parent.queryParam)
    },
    // 重置
    clearClick() {
      this.resetData()
      this.$emit('search', this.$parent.queryParam)
    },
    resetData() {
      let dic = {}
      this.$parent.queryParam = dic
    },
    // 展开 收缩
    spreadClick() {
      this.isSpread = !this.isSpread
    }
  }
}
</script>
<style lang="scss">
.table-search {
  .el-input-number .el-input__inner {
    text-align: left;
  }
}
</style>

<style lang="scss" scoped>
.table-search {
  background-color: #fff;
  padding: 5px 20px 15px;
  font-size: 15px;

  .lineDiv {
    margin-top: 10px;
    font-size: 13px;
  }
}

@media screen and (min-width: 650px) {
  .table-search {
    .lineDiv {
      display: inline-block;
      margin-right: 20px;
      .lineSpan {
        display: inline-block;
      }
      .lineInput {
        width: 200px;
      }
    }
    .hidden {
      display: none;
    }
  }
}

@media screen and (max-width: 650px) {
  .table-search {
    .lineDiv {
      display: flex;
      align-items: center;

      .lineSpan {
        flex-shrink: 0;
        width: 75px;
      }

      .lineInput {
        flex-grow: 1;
      }
    }
    .hidden {
      display: none;
    }
  }
}
</style>

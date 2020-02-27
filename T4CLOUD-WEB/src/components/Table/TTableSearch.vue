<template>
    <div class="table-search">
        <template v-for="item in searchCol">
            <div v-if="item.query" class="lineDiv" :key="item.key">
                <span class="lineSpan">{{item.name}}：</span>
                <el-input v-if="item.type == String" class="lineInput" :placeholder="'请输入'+item.name"
                          clearable v-model="queryParam[item.key]"></el-input>
                <el-input-number v-if="item.type == Number" class="lineInput" :placeholder="'请输入'+item.name"
                          clearable v-model="queryParam[item.key]"></el-input-number>
            </div>
        </template>
        <!-- <span class="fromSpan">状态： </span>
        <el-select v-model="selectStatus" placeholder="请选择" @change="getOrderList">
          <el-option v-for="item in statusList" :key="item.id" :label="item.text" :value="item.id">
          </el-option>
        </el-select> -->
        <el-button type="primary" @click="searchClick">查询</el-button>
        <el-button type="primary" @click="clearClick">重置</el-button>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                // queryParam: {}
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
        created() {
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
            }

        }
    }
</script>

<style lang="scss" scoped>
    .table-search {
        background-color: #fff;
        padding: 15px 20px;
        font-size: 15px;

        .lineDiv {
            display: flex;
            align-items: center;
            margin-bottom: 10px;

            .lineSpan {
                flex-shrink: 0;
            }
        }
    }

    @media screen and (min-width: 650px) {
        .table-search {
            .lineDiv {
                margin-bottom: 0px;
                display: inline-block;
                margin-right: 20px;

                .lineSpan {
                    display: inline-block;
                }

                .lineInput {
                    width: 250px;
                }
            }
        }
    }
</style>

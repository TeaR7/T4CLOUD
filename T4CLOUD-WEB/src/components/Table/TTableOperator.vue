<template>
    <!-- 操作按钮区域 -->
    <div class="table-operator">
        <el-button v-if="add" type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
        <slot></slot>
        <el-dropdown v-if="selectedRows.length > 0" split-button type="primary" @command="handleCommand" style="margin-left:10px;">
            批量操作
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="delete" v-if="this.delete">删除</el-dropdown-item>
                <slot name="dropdown"></slot>
            </el-dropdown-menu>
        </el-dropdown>
    </div>

</template>
<script>
    export default {
        data() {
            return {
                fileList: []
            }
        },
        props: {
            add: {
                type: Boolean,
                default: true,
            },
            delete: {
                type: Boolean,
                default: true,
            },
            selectedRows: {
                type: Array,
                default: function () {
                    return []
                }
            }
        },
        methods: {
            handleAdd() {
                this.$emit('onAdd')
            },
            handleCommand(command) {
                console.log(command);
                if (command == 'delete') {
                    let arr = []
                    this.selectedRows.forEach(item => {
                        arr.push(item.id)
                    });
                    this.$emit('onDelete', arr)
                }
            }
        }
    }
</script>
<style lang="scss" scoped>
    .table-operator {
        background-color: #fff;
        margin-top: 12px;
        padding: 15px 20px;

        .upload-demo {
            margin-left: 10px;
            display: inline-block;
        }
    }

    @media screen and (max-width: 650px) {
        .table-operator {
            .el-button {
                margin-bottom: 10px;
            }
        }
    }
</style>





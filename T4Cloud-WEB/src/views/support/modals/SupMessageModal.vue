<template>
    <div>
        <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
            <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px"
                     v-loading="loading">
                <el-form-item label="标题" prop="title">
                    <el-input placeholder="输入消息标题（短信/微信填模板名称即可）" v-model="forms.title"
                              :readonly="disableSubmit"></el-input>
                </el-form-item>
                <el-form-item label="发送方式" prop="messageType">
                    <t-dict v-model="forms.messageType" dictCode="msg_template_type" :readonly="disableSubmit"></t-dict>
                </el-form-item>
                <el-form-item label="接收人" prop="target">
                    <el-select
                            placeholder="输入接收人"
                            v-model="forms.target"
                            :disabled="disableSubmit"
                            multiple
                            filterable
                            allow-create
                    >
                    </el-select>
                </el-form-item>
                <el-form-item label="参数" prop="param">
                    <el-input placeholder="输入动态参数" v-model="forms.param" :readonly="disableSubmit"></el-input>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <el-input placeholder="输入内容" v-model="forms.content" type="textarea" :rows="3"
                              :readonly="disableSubmit">
                    </el-input>
                </el-form-item>
                <el-form-item label="推送时间" prop="sendTime">
                    <t-date-time placeholder="选择推送时间" v-model="forms.sendTime" :readonly="disableSubmit"></t-date-time>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input placeholder="输入备注" v-model="forms.remark" :readonly="disableSubmit"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit"
                           v-auth="['support:SupMessage:ADD','support:SupMessage:EDIT']">确定</el-button>
          </span>
        </el-dialog>
    </div>
</template>
<script>
    import {T4CloudModalMixin} from '@/mixins/T4CloudModalMixin'
    // import TDateTime from "../../../components/T4Cloud/TDateTime";

    export default {
        name: 'SupMessageModal',
        mixins: [T4CloudModalMixin],
        components: {
            // TDateTime,
        },
        data() {
            return {
                rules: {
                    title: {required: true, message: '消息标题需要填写', trigger: 'blur'},
                    messageType: {required: true, message: '消息类型必选', trigger: 'blur'},
                    target: {required: true, message: '接收人不允许为空', trigger: 'blur'},
                    content: {required: true, message: '消息内容不能为空', trigger: 'blur'},
                },
                url: {
                    detail: '/T4Cloud-Support/SupMessage/detail',
                    save: '/T4Cloud-Support/SupMessage/save',
                    update: '/T4Cloud-Support/SupMessage/update',
                }
            }
        },
        methods: {
            //初始化form表单字段
            initForm() {
                return {
                    id: null,
                    title: null, // 消息标题
                    messageType: null, // 发送方式：1短信 2邮件 3微信 4站内信
                    target: null, // 接收人
                    param: null, // 动态参数：Json格式
                    content: null, // 内容
                    sendTime: null, // 推送时间
                    sendNum: null, // 发送次数 超过5次不再发送
                    sendStatus: null, // 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
                    sendResult: null, // 推送失败原因
                    remark: null, // 备注
                }
            },
            //处理target数据
            beforeSave() {
                if (this.forms.target != null && this.forms.target.length > 0) {
                    this.forms.target = this.forms.target.join(',')
                }
                return true
            }
        }
    }
</script>

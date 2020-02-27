<template>
    <div>
        <el-dialog :title="title" :visible.sync="isShow" :before-close="close">
            <el-form :model="forms" :rules="rules" ref="baseForm" label-width="100px" v-loading="loading">
                <el-form-item label="用户账号" prop="username">
                    <el-input v-model="forms.username"></el-input>
                </el-form-item>
                <el-form-item v-if="!forms.id" label="登录密码" prop="password">
                    <el-input v-model="forms.password"></el-input>
                </el-form-item>
                <el-form-item v-if="!forms.id" label="确认密码" prop="rePassword">
                    <el-input v-model="forms.rePassword"></el-input>
                </el-form-item>
                <el-form-item label="真实姓名" prop="realname">
                    <el-input v-model="forms.realname"></el-input>
                </el-form-item>
                <el-form-item label="工号" prop="workNo">
                    <el-input v-model="forms.workNo"></el-input>
                </el-form-item>
                <el-form-item label="生日">
                    <el-date-picker type="date" placeholder="选择生日" v-model="forms.birthday" format="yyyy-MM-dd"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="forms.gender" placeholder="请选择性别">
                        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="forms.email"></el-input>
                </el-form-item>
                <el-form-item label="手机">
                    <el-input v-model="forms.phone"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="forms.address"></el-input>
                </el-form-item>
                <el-form-item style="text-align:right;">
                    <el-button @click="close">取消</el-button>
                    <el-button type="primary" @click="submitForm('baseForm')">确定</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
    import {httpClient} from '@/utils/http'
    import {removeSame} from '@/utils/util'

    export default {
        name: 'SysPermissionModal',
        data() {
            return {
                title: '',
                isShow: false,
                loading: false,
                forms: {},
                rules: {
                    username: {required: true, message: '请输入用户账号', trigger: 'blur'},
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    rePassword: [
                        {required: true, message: '请输入确认密码', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    realname: {required: true, message: '请输入真实姓名', trigger: 'blur'},
                    workNo: {required: true, message: '请输入工号', trigger: 'blur'}
                },
                url: {
                    save: '/T4Cloud-User/SysUser/save',
                    update: '/T4Cloud-User/SysUser/update'
                },
                sexOptions: [{
                    label: '未知',
                    value: 0
                }, {
                    label: '男',
                    value: 1
                }, {
                    label: '女',
                    value: 2
                }]
            }
        },
        created() {
            this.initForm()
        },
        methods: {
            //此处可填充默认值
            initForm() {
                this.forms = {
                    username: '',
                    password: '',
                    rePassword: '',
                    realname: '',
                    workNo: '',
                    birthday: '',
                    gender: 1, // 性别(0-默认未知,1-男,2-女)
                    email: '',
                    phone: '',
                    address: '',
                    avatar: '',
                    id: '',
                    idCard: '',
                    post: '',
                    salt: '',
                    status: 0,
                }
            },
            //新增方法
            add() {
                this.initForm()
                this.edit({status: 1})
            },
            //编辑
            edit(record) {
                this.forms = removeSame(this.forms, record)//Object.assign({}, record)
                this.isShow = true
                // this.$nextTick(() => {
                //   this.$refs['ruleForm'].setFieldsValue(pick(this.model, 'cityName', 'jobCount', 'status', 'description'))
                //   //时间格式化
                // })
            },
            //关闭窗口
            close() {
                this.resetForm("baseForm")
                this.isShow = false
            },
            //重置表单内容
            resetForm(formName) {
                this.$refs[formName].resetFields();
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
                let data = {...this.forms}
                delete data.rePassword
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
        }
    }
</script>

<style lang="scss" scoped>
</style>

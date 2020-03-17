<template>
  <div class="picUploadWrap">
    <el-upload class="avatar-uploader" action="" :show-file-list="false" :disabled="readonly" accept=".png,.jpg"
      :before-upload="beforeAvatarUpload" :http-request="imgUpload" :style="{height:height,width:width}" @before-remove="handleRemove">
      <img v-if="imageUrl" :src="imageUrl" class="avatar" :style="{height:height,width:width}">
      <span v-if="imageUrl" class="deleteSpan">
        <i class="el-icon-edit delete-icon" :style="{'line-height':height}"></i>
        <i class="el-icon-delete delete-icon" @click="handleRemove" :style="{'line-height':height}"></i>
      </span>
      <i v-else class="el-icon-plus avatar-uploader-icon" :style="{height:height,width:width,'line-height':height}"></i>
    </el-upload>

    <label class="tipsLabel">{{tips}}</label>
  </div>
</template>
<script> 
import { upload, download } from "../../utils/http"
export default {
  data() {
    return {
      importImgUrl: '/T4Cloud-Support/file/upload',
      imageUrl: ''
    };
  },
  model: {
    prop: 'avatar',
    event: 'onSuccess'
  },
  props: {
    avatar: {
      type: String
    },
    readonly: {
      type: Boolean,
      default: true
    },
    tips: {
      type: String
    },
    height: {
      type: String,
      default: '150px'
    },
    width: {
      type: String,
      default: '150px'
    }
  },
  watch: {
    avatar: {
      handler() {
        // console.log('avatar', this.avatar)
        this.getViewPicture(this.avatar)
      }
    }
  },
  created() {
    if (this.avatar) {
      this.getViewPicture(this.avatar)
    }
  },
  methods: {
    // 上传前 判断
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },
    // 删除文件
    handleRemove(e) {
      e.stopPropagation()
      this.imageUrl = ''
      this.$emit('onSuccess', '')
    },
    // 图片上传
    imgUpload(params) {
      var formData = new FormData();
      formData.append('file', params.file);
      formData.append('policy', 2)
      upload(this.importImgUrl, formData).then(res => {
        if (res.success) {
          this.$message.success(res.message);
          const ids = res.result
          this.$emit('onSuccess', ids)
          this.getViewPicture(ids)
        } else {
          this.$message.warning(res.message);
        }
      })
    },
    // 资源预览
    getViewPicture(id) {
      if (!id||id.length <= 0) {
        this.imageUrl = ''
      } else if (id.indexOf('https://') >= 0) {
        this.imageUrl = id
      } else {
        download('/T4Cloud-Support/file/view/' + id).then(res => {
          let blob = new Blob([res]);
          let url = window.URL.createObjectURL(blob);
          this.imageUrl = url
        })
      }
    }
  }
}
</script>
<style lang="scss">
@import "../../styles/theme.scss";
.picUploadWrap {
  .hide .el-upload--picture-card {
    display: none;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: $mainColor;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    text-align: center;
  }
  .avatar {
    display: block;
  }
  .tipsLabel {
    margin: 0px;
    font-size: 12px;
    color: $mainColor;
  }
  .deleteSpan {
    position: absolute;
    right: 0px;
    bottom: 0px;
    width: 100%;
    height: 100%;
    opacity: 0;
    color: #fff;
    font-size: 18px;
    background-color: rgba($color: #000000, $alpha: 0.6);
    .delete-icon {
      width: 50px;
      height: 50px;
    }
  }
  .deleteSpan:hover {
    opacity: 1;
  }
}
</style>
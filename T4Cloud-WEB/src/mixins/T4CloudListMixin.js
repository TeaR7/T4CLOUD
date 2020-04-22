/**
 * 新增修改完成调用 直接调用loadData刷新
 */
// import { filterNull, isPermission, changePrefix } from "t4cloud-util/lib/util";
import { mapGetters } from "vuex";

export const T4CloudListMixin = {
  data() {
    return {
      /* 加载就加载数据 默认为true false的情况是页面里面的内联表*/
      openRefresh: true,
      /* 树表参数 */
      treeProps: {
        children: "children",
        hasChildren: "hasChildren"
      },
      /* 高级查询条件前缀 */
      prefixQueryParam: {},
      /* 查询条件*/
      queryParam: {},
      /* 排序参数 */
      sorter: {
        column: "createTime",
        order: "desc"
      },
      /* 数据源 */
      tableData: [],
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 10,
        total: 0,
        pageSizeOptions: [10, 20, 30],
        show: true,
        showQuickJumper: true,
        showSizeChanger: true
      },
      /* table加载状态 */
      loading: false,
      /* table选中records*/
      selectionRows: [],
      selectionRowKeys: [],
      /* 关联表可见状态 & 关联数据 */
      relationshipVisible: false,
      relationData: {},
      relationId: null
    };
  },
  computed: {
    ...mapGetters(["device"])
  },
  created() {
    //尝试加载数据
    if (this.openRefresh) {
      this.loadData();
    }
    //判断是否为子项
    // this.handleRelationshipModal()
  },
  methods: {
    //加载数据
    loadData(arg) {
      if (this.url.page) {
        //优先加载普通表数据
        this.loadTable(arg);
      } else {
        //尝试加载树表
        this.loadTree();
      }
      //判断是否为子项
      this.handleRelationshipModal();
    },
    //加载普通表数据
    loadTable(arg) {
      if (!this.url.page) {
        this.$message.error("加载数据需要配置data中url.page的属性");
        return;
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1;
      }
      var params = this.getQueryParams(); //查询条件
      this.loading = true;
      // console.log("params", params);
      this.$http
        .GET(this.url.page, params)
        .then(res => {
          if (res.success) {
            if (res.result.records) {
              this.tableData = res.result.records;
              this.ipagination.total = res.result.total;
            } else {
              this.tableData = res.result;
            }
          } else {
            this.$message.warning(res.message);
          }
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    //加载树状表数据
    loadTree() {
      if (!this.url.tree) {
        this.$message.error("加载树状数据需要配置data中url.tree的属性");
        return;
      }
      var params = this.getQueryParams(); //查询条件
      this.loading = true;
      // console.log("params", params);
      this.$http
        .GET(this.url.tree, params)
        .then(res => {
          if (res.success) {
            this.tableData = res.result;
            this.ipagination.total = res.result.total;
          } else {
            this.$message.warning(res.message);
          }
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    //获取查询参数
    getQueryParams() {
      //获取查询条件
      const params = this.combineQueryParams();
      const sorter = { ...this.sorter };
      var param = Object.assign(sorter, params, this.filters);
      param.pageNo = this.ipagination.current;
      param.pageSize = this.ipagination.pageSize;
      return this.$util.filterNull(param, true);
    },
    // 合并查询参数
    combineQueryParams() {
      let dic = { ...this.queryParam };
      let deleteArr = [];
      for (var key in this.queryParam) {
        const prefix = this.prefixQueryParam[key];
        // if (prefix == "between") {
        //   if (dic[key + "_begin"]) {
        //     delete dic[key + "_begin"];
        //   } else if (dic[key + "_end"]) {
        //     delete dic[key + "_end"];
        //   }
        // }
        if (dic[key] && dic[key].length > 0 && prefix) {
          if (prefix == "between") {
            if (dic[key].length > 0) {
              dic[key + "_ge"] = dic[key][0];
            }
            if (dic[key].length > 1) {
              dic[key + "_le"] = dic[key][1];
            }
            deleteArr.push(key);
          } else {
            const backP = this.$util.changePrefix(prefix);
            if (backP.length > 0) {
              deleteArr.push(key);
              if (backP == "_in") {
                dic[key + backP] = dic[key].join(",");
              } else {
                dic[key + backP] = dic[key];
              }
            }
          }
        }
      }
      deleteArr.forEach(key => {
        delete dic[key];
      });
      // console.log("aaa", dic);
      return dic;
    },
    // table 选中的行修改
    handSelectRowChange(selectionRows) {
      this.selectionRows = selectionRows;
      this.selectionRowKeys = [];
      selectionRows.forEach(item => {
        this.selectionRowKeys.push(item.id);
      });
      // console.log("this.selectionRows", this.selectionRows);
      // console.log("this.selectionRowKeys", this.selectionRowKeys);
    },
    /* 删除事件
     * idArr 要删除的数据id
     * params 可以自定义删除的参数
     */
    handleDelete: function(idArr, params) {
      if (!this.url.delete) {
        this.$message.error("删除需要配置data中url.delete的属性!");
        return;
      }
      let data = {
        ids: idArr.join(",")
      };
      if (params) {
        data = { ...params };
      }
      // if (!idArr) {
      //   idArr = this.selectionRowKeys;
      // }

      var that = this;
      this.$http.DELETE(that.url.delete, data).then(res => {
        if (res.success) {
          that.$message.success(res.message);
          that.loadData();
        } else {
          that.$message.error(res.message);
        }
      });
    },
    //编辑事件，打开弹窗
    handleEdit: function(record) {
      this.$refs.entityModal.edit(record);
      this.$refs.entityModal.title = "编辑";
      this.$refs.entityModal.disableSubmit = false;
    },
    //详情事件，打开弹窗
    handleDetail: function(record) {
      this.$refs.entityModal.edit(record);
      this.$refs.entityModal.title = "详情";
      this.$refs.entityModal.disableSubmit = true;
    },
    //新增事件
    handleAdd: function() {
      this.$refs.entityModal.add();
      this.$refs.entityModal.title = "新增";
      this.$refs.entityModal.disableSubmit = false;
    },
    // 添加子菜单
    handleAddSub(record) {
      this.$refs.entityModal.title = "新增子节点";
      this.$refs.entityModal.disableSubmit = false;
      this.$refs.entityModal.edit({ parentId: record.id });
    },

    //分页插件变更响应事件
    handPageSizeChange(pagination) {
      // console.log(pagination)
      if (pagination) {
        this.ipagination = pagination;
      }
      this.loadData();
    },
    // 表格上方四个按钮处理 筛选 导入 导出 刷新
    handlerTableHeadCommand(type, arr) {
      if (type == "refresh") {
        this.loadData();
      } else if (type == "export") {
        this.handleExportXls();
      } else if (type == "templteExport") {
        this.handleExportXls(-1);
      } else if (type == "tableColumn") {
        this.tableColumn = JSON.parse(JSON.stringify(arr));
      }
    },
    search(queryParam, prefixQueryParam) {
      this.queryParam = { ...queryParam };
      this.prefixQueryParam = { ...prefixQueryParam };
      // console.log("this.$parent.relationshipVisible",this.$parent.relationshipVisible)
      // console.log("this.$parent.relationId",this.$parent.relationId)
      //在存在父级的情况下，保持关联ID的查询限定
      if (this.relationshipVisible) {
        this.queryParam[this.relationId] = this.relationData.id;
      }
      // console.log("queryParam",queryParam)
      this.loadData();
    },
    // 判断是否有权限
    hasAuth(permission) {
      return this.$util.isPermission(permission, 0);
    },
    hasRole(role) {
      return this.$util.isPermission(role, 1);
    },
    //文件导出 rowType -1 下载模板
    handleExportXls(rowType) {
      //判断导出URL
      if (!this.url.exportXls) {
        this.$message.error("导入功能需要配置data中url.exportXls");
        return;
      }
      let param = this.combineQueryParams();
      // 导出 指定行
      if (this.selectionRowKeys && this.selectionRowKeys.length > 0) {
        param.selectedRowKeys = this.selectionRowKeys.join(",");
      }
      if (rowType == -1) {
        param.selectedRowKeys = "-1";
      }
      // 导出 指定列
      if (this.tableColumn && this.tableColumn.length > 0) {
        let columnArr = [];
        this.tableColumn.forEach(item => {
          if (item.isShow == true) {
            columnArr.push(item.key);
          }
        });
        if (
          columnArr.length != 0 &&
          columnArr.length != this.tableColumn.length
        ) {
          param.selectedColKeys = columnArr.join(",");
        }
      }
      // console.log("导出参数", param);
      this.$http.download(this.url.exportXls, param).then(data => {
        if (!data || data.type == "application/json") {
          this.$message.warning("文件下载失败");
          return;
        }
        var fileName = data.filename;
        if (rowType == -1) {
          fileName = fileName + "模版";
        }
        delete data.filename;
        if (typeof window.navigator.msSaveBlob !== "undefined") {
          window.navigator.msSaveBlob(new Blob([data]), fileName + ".xls");
        } else {
          let url = window.URL.createObjectURL(new Blob([data]));
          let link = document.createElement("a");
          link.style.display = "none";
          link.href = url;
          link.setAttribute("download", fileName + ".xls");
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link); //下载完成移除元素
          window.URL.revokeObjectURL(url); //释放掉blob对象
        }
      });
    },
    //打开关联表List
    handleRelationship(record, relationId) {
      //传递主表数据
      this.$refs.relationshipList.relationData = record;
      this.$refs.relationshipList.relationId = relationId;
      this.$refs.relationshipList.queryParam[relationId] = record.id;
      // console.log(this.$refs.relationshipList.masterData)
      this.$refs.relationshipList.relationshipVisible = true;
    },
    //处理父级关联参数
    handleRelationshipModal() {
      this.$nextTick(() => {
        if (this.relationshipVisible && this.$refs.entityModal) {
          this.$refs.entityModal.relationForm[
            this.relationId
          ] = this.relationData.id;
          this.$refs.entityModal.relationData = this.relationData;
          // console.log("forms",this.$refs.entityModal.forms)
          // console.log("relationData",this.$refs.entityModal.relationData)
        }
      });
    },
    //关闭关联表List
    handleRelationshipClose() {
      //传递主表数据
      this.relationData = {};
      this.relationshipVisible = false;
    },
    // 获取字典数组
    getDictArr(dictCode) {
      this.loading = true;
      this.$http
        .GET(process.env.VUE_APP_DICTAPI + dictCode)
        .then(res => {
          if (res.success) {
            // console.log(res.result);
            this.dictOptions = res.result;
          } else {
            this.$message.error("获取字典失败");
          }
          this.loading = false;
        })
        .catch(() => {
          this.$message.error("获取字典失败");
          this.loading = false;
        });
    }
  }
};

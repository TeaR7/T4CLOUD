/**
 * 新增修改完成调用 直接调用loadData刷新
 */
import { filterNull } from "@/utils/util";
import { DELETE, GET } from "@/utils/http";

export const T4CloudListMixin = {
  data() {
    return {
      /* 查询条件*/
      queryParam: {},
      /* 数据源 */
      tableData: [],
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 10,
        total: 0,
        pageSizeOptions: ["10", "20", "30"],
        showQuickJumper: true,
        showSizeChanger: true
      },
      /* table加载状态 */
      loading: false,
      /* table选中records*/
      selectionRows: [],
      selectionRowKeys: []
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    //加载数据
    loadData(arg) {
      if (this.url.page) {
        //优先加载普通表数据
        this.loadTable(arg);
      }else {
        //尝试加载树表
        this.loadTree();
      }
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
      console.log("params", params);
      GET(this.url.page, params).then(res => {
        if (res.success) {
          this.tableData = res.result.records;
          this.ipagination.total = res.result.total;
        } else {
          this.$message.warning(res.message);
        }
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
      console.log("params", params);
      GET(this.url.tree, params).then(res => {
        if (res.success) {
          this.tableData = res.result;
          this.ipagination.total = res.result.total;
        } else {
          this.$message.warning(res.message);
        }
        this.loading = false;
      });
    },
    //获取查询参数
    getQueryParams() {
      //获取查询条件
      let queryParam = {};
      var param = Object.assign(queryParam, this.queryParam, this.filters);
      param.pageNo = this.ipagination.current;
      param.pageSize = this.ipagination.pageSize;
      return filterNull(param);
    },
    // table 选中的行修改
    handSelectRowChange(selectionRows) {
      this.selectionRows = selectionRows;
      this.selectionRowKeys = [];
      selectionRows.forEach(item => {
        this.selectionRowKeys.push(item.id);
      });
      console.log("this.selectionRows", this.selectionRows);
      console.log("this.selectionRowKeys", this.selectionRowKeys);
    },
    //删除事件
    handleDelete: function(idArr) {
      if (!this.url.delete) {
        this.$message.error("删除需要配置data中url.delete的属性!");
        return;
      }
      if (!idArr) {
        idArr = this.selectionRowKeys;
      }
      var that = this;
      DELETE(that.url.delete, { ids: idArr.join(",") }).then(res => {
        if (res.success) {
          that.$message.success(res.message);
          that.loadData();
        } else {
          that.$message.warning(res.message);
        }
      });
    },
    //编辑事件，打开弹窗
    handleEdit: function(record) {
      this.$refs.entityModal.edit(record);
      this.$refs.entityModal.title = "编辑";
      this.$refs.entityModal.disableSubmit = false;
    },
    //新增事件
    handleAdd: function() {
      this.$refs.entityModal.add();
      this.$refs.entityModal.title = "新增";
      this.$refs.entityModal.disableSubmit = false;
    },
    //分页插件变更响应事件
    handPageSizeChange(pagination) {
      if (pagination) {
        this.ipagination = pagination;
      }
      this.loadData();
    },
    search(queryParam) {
      this.queryParam = queryParam;
      this.loadData();
    }
  }
};

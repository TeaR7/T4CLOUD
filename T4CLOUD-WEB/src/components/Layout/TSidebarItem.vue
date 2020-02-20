<template>
  <div>
    <template v-for="(item,index) in routes">
      <el-menu-item :index="item.path" :key="index">
        <i class="el-icon-menu"></i>
        <span slot="title">{{item.name}}</span>
      </el-menu-item>
      <el-submenu :index="item.path" v-if="(item.hidden===1)&&item.children" :key="index">
        <template slot="title">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </template>
        <template v-for="(child,index) in item.children">
          <t-sidebar-item class='menu-indent' v-if='child.children&&child.children.length>0' :routes='[child]' :key="index">
          </t-sidebar-item>
        </template>
      </el-submenu>
    </template>
  </div>
</template>

<script>
export default {
  name: 'TSidebarItem',
  props: {
    routes: {
      type: Array
    }
  }
}
</script>

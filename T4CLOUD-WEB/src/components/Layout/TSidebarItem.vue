<template>
  <fragment>
    <template v-for="item in routes">
      <el-menu-item :index="item.path" :key="item.id" v-if="item.hidden===1&&!item.children">
        <i :class="item.icon&&item.icon.indexOf('el-icon-')>=0?item.icon:'el-icon-menu'"></i>
        <span slot="title">{{item.name}}</span>
      </el-menu-item>

      <el-submenu :index="item.path" v-if="item.hidden===1&&item.children" :key="item.id">
        <template slot="title">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </template>
        <template v-for="child in item.children">
          <t-sidebar-item class='menu-indent' :routes='[child]' :key="child.id">
          </t-sidebar-item>
        </template>
      </el-submenu>
    </template>
  </fragment>
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

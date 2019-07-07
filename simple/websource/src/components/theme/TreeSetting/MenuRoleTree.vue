<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>
    <el-row>
      <el-col :span="10">
        <el-row>
          <el-col :span="12">
            <h3>所有菜单</h3>
          </el-col>
          <el-col :span="12">
            <el-button
              type="primary"
              size="small"
              class="floatRight"
              @click="insertMenuRole">
              添加=>角色菜单
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div class="tree-div">
              <el-tree
                :data="menuTree"
                show-checkbox
                node-key="id"
                @check-change="handleCheckChange"
                :default-expand-all="true"
                :props="defaultProps"/>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="10">
        <el-row>
          <el-col :span="12">
            <h3>当选角色菜单</h3>
          </el-col>
          <el-col :span="12">
            <el-button
              type="danger"
              size="small"
              class="floatRight"
              @click="removeMenuTree">
              删除选中角色菜单
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div class="tree-div">
              <el-tree
                :data="menuRoleTree"
                show-checkbox
                node-key="id"
                @check-change="handleCheckChange1"
                :default-checked-keys="menuRoleSelect"
                :default-expand-all="true"
                :props="defaultProps"/>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="4">
        <el-row>
          <el-col :span="16">
            <h3>角色角色选择</h3>
          </el-col>
          <el-col :span="8">
            <el-button
              type="primary"
              size="small"
              class="floatRight">
              O
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div class="tree-div">
              <el-radio-group v-model="rediosSelect" @change="redioChange">
                <el-radio
                  v-for="(item,index) in redios"
                  :key="index"
                  :label="item.id"
                  border>
                  {{ item.rolName }}
                </el-radio>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../plugins/TableBase'

  @Component
  export default class MenuRoleTree extends Mixins(TableBase) {
    defaultProps = {
      children: 'children',
      label: 'cname',
      id: 'id'
    }
    menuTree = []
    menuRoleTree = []
    redios = []
    rediosSelect = ''
    allnodeSelcted = []
    menuRoleSelect = []
    refreshTree () {
      this.select('data/menuFunction/list', {}, true).then(data => {
        this.menuTree = this.getTree(data, 'children', 'pid')
      })
    }
    roleList () {
      this.select('data/roleInfo/list', {}, true).then(data => {
        this.redios = data
      })
    }
    handleCheckChange (data, checked, indeterminate) {
      if (!indeterminate) {
        if (checked) {
          this.allnodeSelcted.push(data)
        } else {
          let i = 0
          this.allnodeSelcted.forEach(ele => {
              if (data.id === ele.id) {
                this.allnodeSelcted.splice(i, 1)
                return
              }
              i++
          })
        }
      }
    }
    handleCheckChange1 (data, checked, indeterminate) {
      if (!indeterminate) {
        if (checked) {
          this.menuRoleSelect.push(data)
        } else {
          let i = 0
          this.menuRoleSelect.forEach(ele => {
            if (data.id === ele.id) {
              this.menuRoleSelect.splice(i, 1)
              return
            }
            i++
          })
        }
      }
    }
    getRoleMenu () {
      if (this.rediosSelect === '') {
        return
      }
      let params = {
          basicsParams: [{
            key: 'roleId',
            type: 'eq',
            value: this.rediosSelect
          }]
        }
        this.select('data/menuFunctionRole/list', params, true).then(data => {
            let param = {
              basicsParams: [{
                key: 'id',
                type: 'in',
                value: data.map(ele => ele.menuFunctionId)
              }]
            }
            this.select('data/menuFunction/list', param, true).then(data1 => {
                 this.menuRoleTree = this.getTree(data1, 'children', 'pid')
            })
        })
    }
    redioChange () {
      this.getRoleMenu()
    }
    insertMenuRole () {
      if (this.rediosSelect === '') {
        this.message('请先选择角色', '友情提示')
        return
      }
      if (this.allnodeSelcted.length === 0) {
        this.message('请选择要添加的菜单', '友情提示')
        return
      }
      let tempnodes = []
      this.allnodeSelcted.forEach(ele => {
       let meunuRole = {
         menuFunctionId: ele.id,
         roleId: this.rediosSelect
       }
        tempnodes.push(meunuRole)
      })
      if (tempnodes.length > 0) {
        this.insert({url: 'data/menuFunctionRole', params: tempnodes}).then(data => {
              this.getRoleMenu()
        })
      }
    }
    removeMenuTree () {
      if (this.rediosSelect === '') {
        this.message('请先选择角色', '友情提示')
        return
      }
      if (this.menuRoleSelect.length === 0) {
        this.message('请选角色菜单', '友情提示')
        return
      }
      let params = {
        basicsParams: [{
          key: 'roleId',
          type: 'eq',
          value: this.rediosSelect
        }, {
          key: 'menuFunctionId',
          type: 'in',
          value: this.menuRoleSelect.map(ele => ele.id)
        }]
      }
      this.remove('data/menuFunctionRole/params', params, true).then(data => {
        this.menuRoleSelect = []
        this.getRoleMenu()
      })
    }
    created () {
      this.refreshTree()
      this.roleList()
    }
  }
</script>
<style>
  .tree-div {
    width: 100%;
    height: 550px;
    overflow: scroll;
  }
  .floatRight {
     float: right;
  }
</style>

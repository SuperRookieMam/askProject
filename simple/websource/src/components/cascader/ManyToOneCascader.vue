<template>
  <div>
    <el-cascader
      :options="options"
      filterable
      placeholder="输入搜索条件"
      @change="handleItemChange"
      :props="props"/>
  </div>
</template>
<script>
  import { Component, Prop } from 'vue-property-decorator'
  import Vue from 'vue'
  @Component({})
  export default class ManyToOneCascader extends Vue {
    @Prop({type: Array, default: () => []})
    dataArr
    @Prop({type: Object, default: () => {}})
    props
    @Prop({type: Array, default: () => []})
    ruleArr

    handleItemChange (val) {
      this.$emit('select-change', val)
    }

    options = []

    /**
     * 多对一组装级联选择器
     * dataArr: [{},{},{}],
     * keyArr:[{lable: '', value: '' ,parent: 'id', chiled: 'filedname',isroot: true },...]
     * */
    cascaderMake (dataArr, ruleArr) {
      let cloneKey = []
      ruleArr.forEach(ele => {
        cloneKey.push(Object.assign({}, ele))
      })
      let arr = []
      dataArr.forEach(ele => {
        arr.push(Object.assign({}, ele))
      })

      let obj = {}
      this.getKeyObj(arr, cloneKey, obj)
      for (let i = 0; i < ruleArr.length - 1; i++) {
        let key = ruleArr[i].chiled
        let key1 = ruleArr[i + 1].chiled
        let parrent = obj[key]
        let parr = []
        parrent.forEach(ele => {
          ele.label = ele[key1.label]
          ele.value = ele[key1.value]
          parr.push(ele)
        })
        arr = this.getchiled(arr, parr, ruleArr[i])
      }
      let key = ruleArr[ruleArr.length - 1]
      arr.forEach(ele => {
        ele.label = ele[key.label]
        ele.value = ele[key.value]
      })
      return arr
    }

    getchiled (subarr, parentArr, keyObj) {
      for (let m = 0; m < parentArr.length; m++) {
        for (let i = 0; i < subarr.length; i++) {
          if ((parentArr[m])[keyObj.parent] === ((subarr[i])[keyObj.chiled])[keyObj.parent]) {
            if (!parentArr[m].children) {
              parentArr[m].children = []
            }
            subarr[i].label = subarr[i][keyObj.label]
            subarr[i].value = subarr[i][keyObj.value]
            parentArr[m].children.push(subarr[i])
            break
          }
        }
      }
      return parentArr
    }

    getKeyObj (dataArr, keyArr, obj) {
      if (keyArr.length > 0) {
        let key = keyArr[0].chiled
        let arr = []
        if (key === 'root') {
          return
        }
        dataArr.forEach(ele => {
          arr.push(ele[key])
        })
        obj[key] = arr
        keyArr.splice(0, 1)
        this.getKeyObj(arr, keyArr, obj)
      }
    }

    created () {
      this.options = this.cascaderMake(this.dataArr, this.ruleArr)
    }
  }
</script>

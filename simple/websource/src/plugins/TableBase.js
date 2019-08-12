import Vue from 'vue'
import {namespace} from 'vuex-class'
import {Component} from 'vue-property-decorator'

const mapModul = namespace('ServerMap')
const Formstate = namespace('Formstate')

@Component({
  inject: ['reload', 'setParames', 'getParames', 'beforeGetData', 'afterGetData', 'beforeSubmit', 'submitSucess', 'interruptJump']
})
export default class TableBase extends Vue {
  @Formstate.Action('get')
  Get
  @Formstate.Action('del')
  Del
  @Formstate.Action('post')
  update
  @Formstate.Action('put')
  insert
  @Formstate.Action('upfile')
  upfile
  @mapModul.Getter('url')
  serverUrl

  servername = 'ask'

  formName = 'formData'

  tabPosition = 'top'

  tableData = []

  formData = {}

  pageSizes = [10, 20, 50, 100]

  totalCount = 0

  serchObj = {}

  activeName = 'base'

  loading = false

  params = {
    pageSize: 50,
    pageNum: 1
  }

  filterByserchObj () {
    this.loading = true
    let params = this.getParames(this.currentHtml)
    // 如果自定义设置参数方法的执行自定义参数设置方法
    if (this.setRequestParam) {
      this.beforeGetData(this.setRequestParam, this.params)
    } else {
      this.setSearchObjToParams(params)
    }
    if (params.type === 'rtable') {
      this.getRtableData(params)
      this.loading = false
       return
    }
    this.select(this.getPageUrl(), this.params, true).then(data => {
      if (this.sucessResult) {
        this.afterGetData(this.sucessResult, data)
        return
      }
      //  如果没有定义 自定数据设置方法则,执行默认
      if (data.list) {
        this.tableData = data.list
        this.params.pageSize = data.pageSize
        this.params.pageNum = data.pageNum
        this.totalCount = data.total
        this.loading = false
      }
    })
    this.loading = false
  }
  /**
   * 处理 rtable 的方法
   * */
  getRtableData (params) {
    this.tableData = params.parent.formData[params.fileName] ? params.parent.formData[params.fileName] : []
    if (params.rid !== 'new') {
      let flage = false
      this.tableData.forEach(ele => {
        if (ele.rid) {
          flage = true
          ele.rid = `${params.rid}`
        }
      })
      if (flage) {
        let url = this.getPageUrl()
        this.update({url: url.substring(0, url.lastIndexOf('/')), params: this.tableData}).then(data => {
          if (this.sucessResult) {
            this.afterGetData(this.sucessResult, data)
          }
          params.parent.formData[params.fileName] = data.data
          this.tableData = data.data
          this.loading = false
        })
      }
    }
    this.loading = false
  }

  /**
   * 默认的参数设置
   * */
  setSearchObjToParams (params) {
    let basicsParams = []
    if (params && params.type === 'rtable') {
      basicsParams.push({key: 'tableName', type: 'eq', value: params.tableName})
      basicsParams.push({key: params.fileName, type: 'eq', value: params.rid})
    }
    for (var key in this.serchObj) {
      if (this.serchObj[key] !== '') {
        basicsParams.push({key: key, type: 'eq', value: this.serchObj[key]})
      }
    }
    this.params.basicsParams = basicsParams
  }

  /**
   * 一对多表格数据获取
   * params 是一个obj，跳转前的一个面设置的，
   * 设置即将跳转的页面需要的参数，参数
   * {
   * type: 'table'(普通表格)| 'rtable'(一对多表格)| 'form'(普通表单)|'rform'(一对多表单) | rmform(多对表单)
   * url: 'http://...' 对应的借口地址
   * rUrl:'http://...' 一对多引用放的地址
   * rid: '' 如果是一对多，在是一的id
   * id: '' 是form 表单的 需要请求的id
   * tableName: '' 一的一方entity名字
   * fileName: '' 一对多引用字段名，如果是自己自己手动维护的字段名字
   * routName: '' 即将跳转的路由名称
   * }
   * */
  getFormData () {
    this.loading = true
    let params = this.getParames(this.currentHtml)
    // 因为在getformdata 不需要参数，但是可能需要执行某些东西，所以还是调用下
    if (this.setRequestParam) {
      this.beforeGetData(this.setRequestParam)
    }
    // 获取表单
    if (params.id === 'new') {
      if (this.sucessResult) {
        this.afterGetData(this.sucessResult, this.formData)
        this.loading = false
        return
      }
      this.loading = false
      return
    }
    return this.select(`${this.getFromUrl()}/${params.id}`).then(data => {
      if (this.sucessResult) {
        this.afterGetData(this.sucessResult, data)
        this.loading = false
        return
      }
      this.formData = data
      this.loading = false
    })
  }
  // 新增表单
  add () {
    this.edit({id: 'new'})
  }

  // 编辑
  edit (data) {
    let params = this.getParames(this.currentHtml)
    if (params.type === 'rtable') {
      this.setParames('exam', {
        type: 'rform',
        id: data.id,
        tableName: 'Food',
        fileName: 'exams',
        rid: params.rid,
        parent: this,
        rparent: params.parent
      })
      if (this.replaceEdit) {
        this.interruptJump(this.replaceEdit, data)
        return
      }
    }
    let nextParams
    // 这里设置的nextParams 请按照上面参数设置
    if (this.SetNextParams) {
      nextParams = this.SetNextParams(data)
    } else {
      nextParams = {id: data.id, type: 'form', parent: this}
    }
    let routeName = this.currentHtml.substring(0, this.currentHtml.length - 1)
    this.setParames(routeName, nextParams)
    if (this.replaceEdit) {
      this.interruptJump(this.replaceEdit, data)
      return
    }
    this.$router.push({path: `/${routeName}/${data.id}`})
  }
  // 删除一行
  deleteRow (params) {
    this.$confirm('确认删除？', '友情提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      let pageurl = this.getPageUrl()
      let url = pageurl.substring(0, pageurl.lastIndexOf('/'))
      this.Del({url: `${url}/${params.id}`}).then(data => {
        if (data.code === 1) {
          if (this.alertMsg) {
            this.afterGetData(this.alertMsg, {type: 'del', result: data})
          } else {
            this.message('服务器未知错误', '友情提示')
          }
        } else {
          this.message('数据删除成功', '友情提示')
        }
        this.filterByserchObj()
      })
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '操作失败'
      })
    })
  }

  // 充值表单
  resetForm (formName) {
    this.$router.go(-1)
  }

  submitForm (formName) {
    this.$refs[formName].validate((valid) => {
      if (valid) {
        let params = this.getParames(this.currentHtml)
        if (this.eidtLastFormData) {
          this.beforeSubmit(this.eidtLastFormData, this.formData)
        }
        if (params.id === 'new') {
          if (params.type === 'rform') {
            this.rformSubmit(params)
            return
          }
          if (params.type === 'rmform') {
            this.rmSumbmitForm(params)
            return
          }
          this.insert({url: this.getFromUrl(), params: [this.formData]}).then(ele => {
            if (ele.code === 0) {
              this.formData = ele.data[0]
              this.message('数据保存成功', '友情提示')
              //  如果自定了跳转
              if (this.replaceJump) {
                this.interruptJump(this.replaceJump)
                return
              }
              this.$router.go(-1)
            } else {
              this.message(ele.msg, '友情提示')
            }
          })
        } else {
          this.update({url: this.getFromUrl(), params: [this.formData]}).then(ele => {
            // 如果自定自定一乐返回拦截
            if (this.beforResetFormData) {
              this.submitSucess(this.beforResetFormData, ele)
              return
            }
            if (ele.code === 0) {
              this.message('修改数据成功', '友情提示')
              this.formData = ele.data[0]
              if (this.replaceJump) {
                this.interruptJump(this.replaceJump)
                return
              }
              this.$router.go(-1)
            } else {
              this.message(ele.msg, '友情提示')
            }
          })
        }
      } else {
        return false
      }
    })
  }
  /**
   * 处理一对多的情况
   * */
  rformSubmit (params) {
    if (!params.rparent.formData[params.fileName]) {
      params.rparent.formData[params.fileName] = []
    }
    this.formData.tableName = params.tableName
    this.formData.rid = params.rid
    params.rparent.formData[params.fileName].push(this.formData)
    params.parent.tableData = params.rparent.formData[params.fileName]
    if (this.beforResetFormData) {
      this.submitSucess(this.beforResetFormData, this.formData)
      this.message('数据保存成功', '友情提示')
    }
    if (this.replaceJump) {
      this.interruptJump(this.replaceJump)
      return
    }
    this.$router.go(-1)
  }

  rmSumbmitForm (params) {
    params.parent[params.fileName] = this.formData
    this.message('数据保存成功', '友情提示')
    // 如果自定自定一乐返回拦截
    if (this.beforResetFormData) {
      this.submitSucess(this.beforResetFormData, params.parent[params.fileName])
      return
    }
    if (this.replaceJump) {
      this.interruptJump(this.replaceJump)
      return
    }
    this.$router.go(-1)
  }

  message (msg, title) {
    this.$alert(msg, title, {
      confirmButtonText: '确定',
      callback: action => {
        this.$message({
          type: 'info',
          message: `action: ${ action }`
        })
      }
    })
  }
  messageSure (msg, title, fun, params) {
    this.$confirm(msg, title, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      fun(params)
      this.$message({
        type: 'success',
        message: '操作成功!'
      })
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '操作失败'
      })
    })
  }

  getTree (arr, treeChieldName, pidname) {
    let parent = {}
    let all = {}
    arr.forEach(ele => {
      if (parent.hasOwnProperty(ele.pid) && ele.pid !== 0) {
        parent[ele[pidname]].push(ele)
      } else if (ele[pidname] !== 0) {
        parent[ele[pidname]] = [ele]
      }
      all[ele.id] = 1
    })
    let tree = []
    arr.forEach(ele => {
      ele[treeChieldName] = parent[ele.id]
      if (ele.pid === 0) {
        tree.push(ele)
      } else {
        if (!all.hasOwnProperty(ele.pid)) {
          tree.push(ele)
        }
      }
    })
    return tree
  }

  geturl (url) {
    return `${this.serverUrl.base[this.servername]}${url}`
  }

  handleSizeChange (val) {
    this.params.pageSize = val
    this.filterByserchObj()
  }

  handleCurrentChange (val) {
    this.params.pageNum = val
    this.filterByserchObj()
  }

  // tab 标签点击事件
  handleClick (tab, event) {
  }
  // get 请求，如果时是使用后台生成的接口用的是 dynamicParams 名字请用 dynamicParams请传true
  select (url, params, dynamicParams, flags) {
    let paramobj
    if (params && dynamicParams) {
      paramobj = {'dynameicParams': JSON.stringify(params)}
      if (flags) {
        if (Array.isArray(flags)) {
          flags.forEach(ele => {
            paramobj[ele.key] = ele.value
          })
        } else {
          paramobj[flags.key] = flags.value
        }
      }
    }
    return this.Get({url: url, params: paramobj}).then(ele => {
      return ele.data
    })
  }

  remove (url, params, dynamicParams, flags) {
    let paramobj
    if (params && dynamicParams) {
      paramobj = {'dynameicParams': JSON.stringify(params)}
      if (flags) {
        if (Array.isArray(flags)) {
          flags.forEach(ele => {
            paramobj[ele.key] = ele.value
          })
        } else {
          paramobj[flags.key] = flags.value
        }
      }
    }
    return this.Del({url: url, params: paramobj}).then(ele => {
      return ele.data
    })
  }

  /**
   * 多对一组装级联选择器
   * dataArr: [{},{},{}],
   * keyArr:[{lable: '', value: '' ,parent: 'id', chiled: 'filedname',isroot: true },...]
   * */
  cascaderMake (dataArr, keyArr) {
    let cloneKey = []
    for (let i = 0; i < keyArr.length; i++) {
      cloneKey.push(keyArr[i])
    }
    let obj = {}
    this.getKeyObj(dataArr, cloneKey, obj)
    let arr = dataArr
    for (let i = 0; i < keyArr.length - 1; i++) {
      let key = keyArr[i].chiled
      arr = this.getchiled(arr, obj[key], keyArr[i])
    }
    let key = keyArr[keyArr.length - 1]
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
    let params = this.getParames(this.currentHtml)
    if (params && params.type.search('table') >= 0) {
      this.filterByserchObj()
    } else if (params && params.type.search('form') >= 0) {
      this.getFormData()
    }
  }
}

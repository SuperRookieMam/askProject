 import Vue from 'vue'
 import { namespace } from 'vuex-class'
 import { Component } from 'vue-property-decorator'

 const mapModul = namespace('ServerMap')
 // const AdminModul = namespace('admin')
 const Formstate = namespace('Formstate')

 @Component
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
   // 获取表单数据,根据searchObj过滤
   filterByserchObj () {
     this.loading = true
     let basicsParams = []
     let url = this.getPageUrl()
     if (this.currentHtml === 'rtable') {
       let rarr = this.rmsg.split('_')
       //  需要用到的表都会设定一个tableName的字段表示，那个实体引用的这个表
       basicsParams.push({key: 'tableName', type: 'eq', value: rarr[0]})
       //  维护一对多的的表字段属性名不知道这里需要传进来
       basicsParams.push({key: rarr[1], type: 'eq', value: rarr[2]})
     }
     this.howSearch(basicsParams)
     this.params.basicsParams = basicsParams
     this.select(url, this.params, true).then(data => {
       if (data.list) {
         this.tableData = data.list
         this.params.pageSize = data.pageSize
         this.params.pageNum = data.pageNum
         this.totalCount = data.total
         this.loading = false
       }
     })
   }
   // 获取表单数据
   getFormData () {
     if (this.id === 'new') {
       return
     }
     return this.select(`${this.getFromUrl()}/${this.id}`).then(data => {
       this.formData = data
       return data
     })
   }
   // 新增表单
   add () {
     this.edit({id: 'new'})
   }

   // 编辑
   edit (data) {
     // 因为表格 只能存在一对多的引用，多对一是实体与实体之间的跳转，
     let currentHtml = 'form'
     let rmsg = 'none_'
     if (this.currentHtml === 'rtable') {
       currentHtml = 'rform'
       rmsg = this.rmsg
     }
     this.$router.push({path: `/${this.jumpName}/${currentHtml}/${data.id}/${rmsg}`})
   }
   // 删除一行
   deleteRow (params) {
     let pageurl = this.getPageUrl()
     let url = pageurl.substring(0, pageurl.lastIndexOf('/'))
     this.Del({url: `${url}/${params.id}`}).then(data => {
       this.filterByserchObj()
     })
   }

   resetForm (formName) {
     this.$router.go(-1)
   }
   submitForm (formName) {
     this.$refs[formName].validate((valid) => {
       if (valid) {
         if (this.id === 'new') {
           // 一对多的情况
           if (this.currentHtml === 'rform') {
             let arr = this.rmsg.split('_')
             this.formData.tableName = arr[0]
             this.formData[arr[1]] = arr[2]
           }
           this.insert({url: this.getFromUrl(), params: [this.formData]}).then(ele => {
             if (ele.code === 0) {
               // 多对一的情况
               if (this.currentHtml.search('rmform') >= 0) {
                 let arr = this.rmsg.split('_')
                 let urlarr = arr[0].split('.')
                 let rurl = this.serverUrl
                 urlarr.forEach(ele => {
                   rurl = rurl[ele]
                 })
                 rurl = this.geturl(rurl)
                 this.select(`${rurl}/${arr[2]}`).then(data => {
                   let rdata = data
                   rdata[arr[1]] = ele.data[0]
                   this.update({url: rurl, params: [rdata]}).then(ele => {
                     if (ele.code === 0) {
                       this.formData = ele.data[0]
                       this.$forceUpdate()
                       // this.$router.go(-1)
                     } else {
                       this.message(ele.msg, '友情提示')
                     }
                   })
                 })
                 return
               }
               this.formData = ele.data[0]
               this.$router.go(-1)
             } else {
               this.message(ele.msg, '友情提示')
             }
           })
         } else {
           this.update({url: this.getFromUrl(), params: [this.formData]})
             .then(ele => {
               if (ele.code === 0) {
                 this.formData = ele.data[0]
                 if (this.currentHtml === 'rform') {
                   this.$forceUpdate()
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

   created () {
     if (this.currentHtml.search('table') >= 0) {
       this.filterByserchObj()
     } else if (this.currentHtml.search('form') >= 0) {
       this.getFormData()
       this.tabPosition = this.currentHtml === 'rmform' ? 'right' : 'top'
     }
   }
 }

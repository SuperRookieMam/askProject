 import Vue from 'vue'
 import { namespace } from 'vuex-class'
 import { Component } from 'vue-property-decorator'

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

   tableData = []

   formData = {}

   totalCount = 0
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
      return this.Get({url: url, params: paramobj}).then(ele => { return ele.data })
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
     return this.Del({url: url, params: paramobj}).then(ele => { return ele.data })
   }
  edit (name, data) {
    this.$router.push({name: name, params: {id: data.id}})
  }
  add (name) {
    let data = {id: 'new'}
    this.edit(name, data)
  }

  getFormData (url, id) {
     if (id !== 'new') {
       this.select(`${url}/${id}`).then(data => {
           this.formData = data
         })
     }
  }
  resetForm (formName) {
    this.$router.go(-1)
  }
  submitForm (formName, id, url) {
    this.$refs[formName].validate((valid) => {
      if (valid) {
        if (id === 'new') {
          this.insert({url: url, params: [this.formData]}).then(ele => {
              if (ele.code === 0) {
                this.formData = ele.data[0]
                this.$router.go(-1)
              } else {
                this.message(ele.msg, '友情提示')
              }
            })
        } else {
          this.update({url: url, params: [this.formData]})
              .then(ele => {
              if (ele.code === 0) {
                this.formData = ele.data[0]
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
 }

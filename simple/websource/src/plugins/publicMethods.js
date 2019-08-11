const templateLastParams = {}
export default {
  // 设置即将跳转页面需要的参数，obj期待一个object
  //  key期待是页面的名字
  setParames: (key, obj) => {
    templateLastParams[key] = obj
  },
  // 根据页面获取对应的参数
  getParames: (key) => {
    return templateLastParams[key]
  },
  //  fun 期待是一个函数，params 是fun函数的实际参数数组
  beforeGetData: (fun, params) => {
    if (fun) {
      if (params) {
        fun(params)
      } else {
        fun()
      }
    }
  },
  //  获取表单，或者table数据以后 ，如果需要对数据组什么组装调用
  //  fun 是一个函数，params fun 的参数数组
  afterGetData: (fun, params) => {
    if (fun) {
      if (params) {
        fun(params)
      } else {
        fun()
      }
    }
  },
  //  提交表单以前需要做些神，
  //  fun期待一个函数，params fun的参数数组
  beforeSubmit: (fun, params) => {
    if (fun) {
      if (params) {
        fun(params)
      } else {
        fun()
      }
    }
  },
  //  当提交成功后 想要做的事, fun 期待一个参数,params 参数数组
  submitSucess: (fun, params) => {
    if (fun) {
      if (params) {
        fun(params)
      } else {
        fun()
      }
    }
  },
  // 如果是组件想要替换跳转默认的跳转方式，可自定跳转方法跳转
  interruptJump (fun, params) {
    if (fun) {
      if (params) {
        fun(params)
      } else {
        fun()
      }
    }
  }
}

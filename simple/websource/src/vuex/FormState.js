import qs from 'qs'
import { httpInstance } from '../config'
export default class FormState {
  namespaced = true
  state ={
    data: {}
  }
  getters = {
    data: state => { return state.data }
  }
  mutations = {
    data: (state, data) => {
      state.data = data
    }
  }
  actions = {
    get ({commit, state}, {url, params}) {
      if (params) {
        return httpInstance.get(`${url}?` + qs.stringify(params)).then(result => {
          return result
        })
      } else {
        return httpInstance.get(`${url}`).then(result => {
          return result
        })
      }
    },
    post ({ state }, {url, params}) {
      return httpInstance.post(`${url}`, params).then(result => {
        return result
      })
    },
    put ({ state }, { url, params }) {
      return httpInstance.put(`${url}`, params).then(result => {
        return result
      })
    },
    del ({ state }, { url, params }) {
      if (params) {
        return httpInstance.delete(`${url}?` + qs.stringify(params)).then(result => {
          return result
        })
      } else {
        return httpInstance.delete(`${url}`).then(result => {
          return result
        })
      }
    },
    // element 上传文公共方法
    upfile ({ state }, params) {
      if (!params.file) {
        this.$alert('请选择文件', '友情提示', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            })
          }
        })
      }
      let form = new FormData()
      if (params.data) {
        for (var key in params.data) {
          form.append(key, params.data[key])
        }
      }
      let headers = {}
      headers['Content-Type'] = 'multipart/form-data'
      let user = JSON.parse(sessionStorage.getItem('user'))
      headers.username = user.username
      headers.password = user.password
      if (params.headers) {
        for (var headername in params.headers) {
          headers[headername] = params.data[headername]
        }
      }
      let fileName = `${user.username}` + '_' + new Date().getMilliseconds()
      form.append(fileName, params.file)
      return httpInstance.post(params.action, form, { headers: headers }).then(data => {
        if (params.onSuccess) {
          params.onSuccess()
        }
        return data
      }).catch(t => {
        if (params.onError) {
          params.onError()
        }
      })
    }
  }
}

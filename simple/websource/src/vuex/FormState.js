import qs from 'qs'
import { httpInstance } from '../config'
const ip = CONTEXT_PATH
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
          return httpInstance.get(`${CONTEXT_PATH}${url}?` + qs.stringify(params)).then(result => {
            return result
          })
        } else {
          return httpInstance.get(`${CONTEXT_PATH}${url}`).then(result => {
            return result
          })
        }
    },
    post ({ state }, {url, params}) {
      return httpInstance.post(`${CONTEXT_PATH}${url}`, params).then(result => {
          return result
      })
    },
    put ({ state }, { url, params }) {
      return httpInstance.put(`${ip}${url}`, params).then(result => {
          return result
      })
    },
    del ({ state }, { url, params }) {
      if (params) {
        return httpInstance.delete(`${CONTEXT_PATH}${url}?` + qs.stringify(params)).then(result => {
          return result
        })
      } else {
        return httpInstance.delete(`${CONTEXT_PATH}${url}`).then(result => {
          return result
        })
      }
    }
  }
}

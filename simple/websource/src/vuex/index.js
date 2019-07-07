import Vue from 'vue'
import Vuex, { Store } from 'vuex'
import { httpInstance } from '../config'
import Login from './login'
import FormState from './FormState'

Vue.use(Vuex) // use必须在创建store实例之前调用
const CTX = CONTEXT_PATH + 'data/'
export default new Store({
  namespaced: true,
  state: {
    title: '应用',
    user: {},
    token: {},
    refresh_token: '',
    error: {
      count: 0,
      message: ''
    },
    loadingCount: 0,
    loginType: '',
    theme: 'left',
    menus: [],
    url: {
      authorityMenus: CTX + 'menuFunctionRole/menus',
      login: CTX + 'login',
      logout: CTX + 'logout'
    }
  },
  getters: {
    menus: state => { return state.menus },
    loginType: state => { return state.loginType },
    token: state => { return state.token },
    user: state => { return state.user }
  },
  mutations: {
    updateTitle (state, { title }) {
      if (title) {
        state.title = title
      }
    },
    switchTheme (state, theme) {
      state.theme = theme
    },
    addError (state, payload) {
      let count = 1 + state.error.count
      let message = payload
      state.error = {
        count,
        message
      }
    },
    updateUser: (state, user) => {
      state.user = user
    },
    loading (state) {
      state.loadingCount++
      console.debug('after loading the loading count is ', state.loadingCount)
    },
    loadingComplete (state) {
      state.loadingCount--
      console.debug('after complete the loading count is ', state.loadingCount)
    },
    updateMenu (state, menus) {
      state.menus = menus
    },
    updateToken (state, data) {
      state.token = data
    },
    loginType: (state, loginType) => {
      state.loginType = loginType
    }
  },
  actions: {
    loadMenu ({commit, state: { url }}) {
      return httpInstance.get(url.authorityMenus).then(({ data }) => {
              let parent = {}
              let all = {}
              data.forEach(ele => {
                if (parent.hasOwnProperty(ele.pid) && ele.pid !== 0) {
                  parent[ele.pid].push(ele)
                } else if (ele.pid !== 0) {
                  parent[ele.pid] = [ele]
                }
                all[ele.id] = 1
              })
              let menus = []
              data.forEach(ele => {
                ele.submenus = parent[ele.id]
                if (ele.pid === 0) {
                    menus.push(ele)
                } else {
                   if (!all.hasOwnProperty(ele.pid)) {
                     menus.push(ele)
                   }
                }
              })
              commit('updateMenu', menus)
              return menus
           })
    }
  },
  modules: {
    Login: Login,
    Formstate: new FormState()
  }
})

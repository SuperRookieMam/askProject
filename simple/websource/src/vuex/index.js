import Vue from 'vue'
import Vuex, { Store } from 'vuex'
import { httpInstance } from '../config'
import Login from './login'
import FormState from './FormState'
import ServerMap from './ServerMap'
Vue.use(Vuex) // use必须在创建store实例之前调用
const CTX = CONTEXT_PATH + 'data1/'
export default new Store({
  namespaced: true,
  state: {
    serverName: '',
    user: {},
    token: {},
    error: {
      count: 0,
      message: ''
    },
    loadingCount: 0,
    menus: [],
    url: {
      login: CTX + 'login',
      logout: CTX + 'logout'
    },
    serverMap: {
      oauthserver: CONTEXT_PATH + 'data1/',
      ask: CONTEXT_PATH + 'data1/'
    },
    currentServer: {}
  },
  getters: {
    menus: state => {
      let parent = {}
      let all = {}
      state.menus.forEach(ele => {
        if (parent.hasOwnProperty(ele.pid) && ele.pid !== 0) {
          parent[ele.pid].push(ele)
        } else if (ele.pid !== 0) {
          parent[ele.pid] = [ele]
        }
        all[ele.id] = 1
      })
      let menus = []
      state.menus.forEach(ele => {
        ele.submenus = parent[ele.id]
        if (ele.pid === 0) {
          menus.push(ele)
        } else {
          if (!all.hasOwnProperty(ele.pid)) {
            menus.push(ele)
          }
        }
      })
      return menus
    },
    token: state => { return state.token },
    user: state => { return state.user },
    currentServer: state => { return state.currentServer },
    serverMap: state => { return state.serverMap }
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
    currentServer: (state, server) => {
      state.currentServer = server
    }
  },
  actions: {
    loadMenu ({commit, state: {serverMap}}, {url}) {
      return httpInstance.get(`${url}`).then(({ data }) => {
        commit('updateMenu', data)
      })
    }
  },
  modules: {
    Login: Login,
    Formstate: new FormState(),
    ServerMap: new ServerMap()
  }
})

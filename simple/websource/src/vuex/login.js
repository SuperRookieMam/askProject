import qs from 'qs'
import axios from 'axios'

const loginInstance = axios.create({
  // 登录的数据格式，使用form表单格式
  transformRequest: [(data, headers) => qs.stringify(data, { arrayFormat: 'repeat' })]
})
const CT = CONTEXT_PATH + 'data1/'
export default {
  namespaced: true,
  state: {
    loginType: '',
    url: {
      oautLogin: CT + 'oauth/token',
      userInfo: CT + 'userDetailes/userInfo',
      login: CT + 'login',
      logout: CT + 'logout'
    },
    oauth: {
      grant_type: 'password',
      client_id: '2c9eaf716b9e9352016b9e9370190000',
      client_secret: '123456'
    }
  },
  getters: {
    loginType: state => { return state.loginType }
  },
  mutations: {},
  actions: {
    login ({commit, state: { url, loginType, oauth }}, data) {
      if (loginType === 'oauth') {
        let params = {
          grant_type: oauth.grant_type,
          client_id: oauth.client_id,
          client_secret: oauth.client_secret,
          username: data.username,
          password: data.password
        }
        return loginInstance.post(url.oautLogin, params).then(({ data }) => {
           commit('updateToken', data, {root: true})
           sessionStorage.setItem('token', JSON.stringify(data))
           return loginInstance.get(`${url.userInfo}?access_token=${data.access_token}`).then(({data}) => {
             commit('updateUser', data.data, {root: true})
              sessionStorage.setItem('user', JSON.stringify(data.data))
              return true
            })
        })
      } else {
       return loginInstance.post(url.login, data).then(({data}) => {
          commit('updateUser', data, {root: true})
          sessionStorage.setItem('user', JSON.stringify(data))
          return true
        })
      }
    }
  }
}

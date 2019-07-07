import { httpInstance } from '../config'

export default {
  install (Vue, instance = httpInstance) {
    Vue.prototype.$http = instance
    Vue.http = instance
}
}

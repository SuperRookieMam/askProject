const CTX = CONTEXT_PATH + 'data1/'
export default class ServerMap {
  namespaced = true
  state = {
    Url: {
      base: {
        fileServer: CTX,
        ask: CTX
      },
      file: {
        upload: 'fileInfos',
        preview: 'fileInfos',
        delete: 'fileInfos',
        fileInfoUpdate: 'fileInfos',
        fileInfoList: 'fileInfos/list',
        fileInfoPage: 'fileInfos/page'
      },
      ask: {
        menus: 'menuFunctionRole/menus',
        menutreeList: 'menuFunction/list',
        menutreeUpdate: 'menuFunction',
        menuRoleList: 'menuFunctionRole/list',
        menuRoleUpdate: 'menuFunctionRole',
        menuRoleUpdateParams: 'menuFunctionRole/params',
        oauthUserDetailsPage: 'oAthUserDetailes/page',
        oauthUserDetailsUpdate: 'oAthUserDetailes',
        oauthUserDetailsList: 'oAthUserDetailes/list',
        roleList: 'roleInfo/list',
        roleInfoPage: 'roleInfo/page',
        roleInfoUpdate: 'roleInfo',
        userRolePage: 'userRole/page',
        userRoleUpdate: 'userRole',
        userRoleList: 'userRole/list',
        foodPage: 'food/page',
        foodUpdate: 'food',
        examPage: 'exam/page',
        examUpdate: 'exam',
        resultPage: 'result/page',
        resultUpdate: 'result'
      },
      resource: {
        resourceServer: 'menuFunctionRoles/resourceServers'
      }
    }
  }
  getters = {
    map: state => { return state.map },
    url: state => { return state.Url }
  }
}

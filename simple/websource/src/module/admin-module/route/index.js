
export default [{
  name: 'login',
  path: '/login',
  component: () => import('@/components/theme/login/login')
}, {
    name: '',
    path: '/',
    component: () => import('@/components/theme/left-main/Leftmain'),
    children: [ {
      name: 'menuFunctionRole',
      path: '/menuFunctionRole/:id',
      component: () => import('../component/MenuFunctionRloe/MenuFunctionRole')
    }, {
      name: 'menuFunctionRoles',
      path: '/menuFunctionRoles',
      component: () => import('../component/MenuFunctionRloe/MenuFunctionRoles'),
      props: true
    }, {
      name: 'menuFunctions',
      path: '/menuFunctions',
      component: () => import('../../admin-module/component/MenuFunction/MenuFunctions')
    }, {
      name: 'menuFunction',
      path: '/menuFunction/:id',
      component: () => import('../../admin-module/component/MenuFunction/MenuFunction'),
      props: true
    }, {
      name: 'menutree',
      path: '/menutree',
      component: () => import('@/components/theme/TreeSetting/MenuTree'),
      props: true
    }, {
      name: 'menuRoleTree',
      path: '/menuRoleTree',
      component: () => import('@/components/theme/TreeSetting/MenuRoleTree'),
      props: true
    }, {
      name: 'food',
      path: '/food',
      component: () => import('../../admin-module/component/food/food'),
      props: true
    }, {
    name: 'foods',
      path: '/foods',
      component: () => import('../../admin-module/component/food/foods'),
      props: true
    }
      ]
   }

]

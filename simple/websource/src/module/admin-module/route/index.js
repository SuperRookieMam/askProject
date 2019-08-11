
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
      path: '/food/:id',
      component: () => import('../../admin-module/component/food/food'),
      props: true
    }, {
    name: 'foods',
      path: '/foods',
      component: () => import('../../admin-module/component/food/foods'),
      props: true
    }, {
      name: 'exam',
      path: '/exam/:id',
      component: () => import('../../admin-module/component/Exams/Exam'),
      props: true
    }, {
      name: 'exams',
      path: '/exams',
      component: () => import('../../admin-module/component/Exams/Exams'),
      props: true
    }, {
      name: 'result',
      path: '/result/:id',
      component: () => import('../../admin-module/component/Result/Result'),
      props: true
    }, {
      name: 'results',
      path: '/results',
      component: () => import('../../admin-module/component/Result/Results'),
      props: true
    },
      {
        name: 'oAthUserDetailes',
        path: '/oAthUserDetailes/:id',
        component: () => import('../../admin-module/component/OAthUserDetailes/OAthUserDetailes'),
        props: true
      }, {
        name: 'oAthUserDetailess',
        path: '/oAthUserDetailess',
        component: () => import('../../admin-module/component/OAthUserDetailes/OAthUserDetailess'),
        props: true
      }, {
        name: 'roleInfo',
        path: '/roleInfo/:id',
        props: true,
        component: () => import('../component/RoleInfo/RoleInfo')
      }, {
        name: 'roleInfos',
        path: '/roleInfos',
        props: true,
        component: () => import('../component/RoleInfo/RoleInfos')
      }, {
        name: 'userRole',
        path: '/userRole/:id',
        props: true,
        component: () => import('../component/UserRole/UserRole')
      }, {
        name: 'userRoles',
        path: '/userRoles',
        props: true,
        component: () => import('../component/UserRole/UserRoles')
      }
    ]
   }

]

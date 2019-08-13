###这个当说明及做一些约定：
    在获取请求都调用下publicMethods里连的方法；
#### beforeGetData：方法说明
    在获取数据前，发送ajax以前，可以对参数做一些处理
    这里约定一个名为 
    如果你再你页面定义了这个方法我就会调用这个方法
    params 是需要设置的参数：
    如果你定义了这个方法讲不会执行默认的参数设置，
    所以你定义完成请完成整个参数的设置
    params 你需要设置的参数对象
    setRequestParam(params) {
      // todo 
    }
####afterGetData：方法说明
    在成功获取数据后，你需要对数据做什么事情，你可以在你的页面定义一个
    的方法：
    如果你定义这个方法将不会执行默认的数据设置方法，
    所以请设置完所有的事情
    sucessResult (data) {
      // todo 
    } 
####interruptJump：方法说明
     如果你需要自定义跳转，或者不跳转，或者只是刷新页面，你需要提供一个
     拦截跳转的方法：这里我们约定一个方法名为请
     replaceJump (obj) {
        // todo 
     }
    的方法： obj 当前行数据对象，打开或者跳转下一个组件前请，请务必设置，
             因为执行了自定放讲不会默认的流程，无法跳转，
             下个页面讲无法获取值
####beforeSubmit: 方法说明             
    如果你想在保单以前想做一些什么事情，请自定一个方法约定一个名字为
    eidtLastFormData （formdata） {
    
    }
    formdata 在提交表单以前需要对表单做一些什么事情
####submitSucess: 方法说明             
    如果在提交表成功以后需要做的事情，你需要自定的话就自定一个为
    beforResetFormData （formdata） {
    
    }
    formdata 在提交表单以前需要对表单做一些什么事情
####templateLastParams: 参数说明：
      此obj 主要时维护每个页面最后一次请求的参数，且此obje 时个全局参数，
      此obj以页面的路由为key, 以obj为value,记录此页面需要的参数
     {
       //  table 普通表格页面需要的参数
      type：'table'(普通表格)| 'form'(普通表单)|'rform'(一对多表单) | rmform(多对表单)
      formRoute: 表格页面需要需要跳转到表单页面的路有名字
     
      //  'rtable'(一对多表格)页面需要参数如下
      type：'rtable' 即将跳转的页面是对多类型的表格页面
      tableName: '' 如果是一对多需要设置此致，多方维护了一个那个表引用的表名字段
      fileName: '' 如果是一对多需要设置，表示一方的那个字段名引用的多方
      rid: '3'  表示 是那个一方引用多方
     
       // form 类型需要的参数
      type：'form' 表明即将跳转的页面是普通的form表单页面
      id: 'new' 如果是form表单的 需要请求的id，新增为new
     
       // rform 类型需要的参数
       type：'rform' 表明是一多表格页面跳转到的表单页面
       id: 'new' 如果是form表单的 需要请求的id，新增为new
       tableName: '' 这个就是rtable接受的参数穿过来的
       fileName: '' 这个也是rtable接受的参数穿过来的
       rid: '3'  这个也是rtable接受的参数穿过来的
       parent: this, //代表格的类
       rparent: params.parent //引用表格的父类

       // rmform 类型需要的参数  多对一
      type：'rform' 表明是一多表格页面跳转到的表单页面
      id: 'new' 如果是form表单的 需要请求的id，新增为new
      fileName：'' 引用放字段名
      parent: this, //代表单的类

      ... 如果你有特殊的需求还需要特定的传参，请自定义
     }
###setParames:方法说明
    设置templateLastParams 的全局方法： 
    setParames (key, obj)  key为页面路由名： obj参数对象
###getParames:方法说明
    获取templateLastParams 的全局方法： 
    setParames (key)  要获取与哪一个参数对象

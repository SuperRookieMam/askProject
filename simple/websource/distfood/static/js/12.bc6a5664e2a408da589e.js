(window.webpackJsonp=window.webpackJsonp||[]).push([[12,1,2,3,4,9,10,11,13,14,15,16,17,18,19,20,21,22,23],{8:function(e,t,a){"use strict";a.d(t,"a",function(){return H});var r,i,s,n,l,u,o,m,c,f,h,p,g,b,d,v=a(14),y=a.n(v),D=a(2),k=a.n(D),N=a(3),P=a.n(N),J=a(4),S=a.n(J),O=a(5),R=a.n(O),w=a(13),j=a.n(w),F=a(6),$=a.n(F),G=a(15),z=a.n(G),E=(a(38),a(17)),x=a(30),A=a(1),B=Object(x.e)("ServerMap"),U=Object(x.e)("Formstate"),H=(r=Object(A.a)({inject:["reload","setParames","getParames","beforeGetData","afterGetData","beforeSubmit","submitSucess","interruptJump"]}),i=U.Action("get"),s=U.Action("del"),n=U.Action("post"),l=U.Action("put"),u=U.Action("upfile"),o=B.Getter("url"),r((c=function(e){function s(){var e,t;k()(this,s);for(var a=arguments.length,r=new Array(a),i=0;i<a;i++)r[i]=arguments[i];return t=S()(this,(e=R()(s)).call.apply(e,[this].concat(r))),y()(t,"Get",f,j()(t)),y()(t,"Del",h,j()(t)),y()(t,"update",p,j()(t)),y()(t,"insert",g,j()(t)),y()(t,"upfile",b,j()(t)),y()(t,"serverUrl",d,j()(t)),t.servername="ask",t.formName="formData",t.tabPosition="top",t.tableData=[],t.formData={},t.pageSizes=[10,20,50,100],t.totalCount=0,t.serchObj={},t.activeName="base",t.loading=!1,t.params={pageSize:50,pageNum:1},t}return $()(s,e),P()(s,[{key:"filterByserchObj",value:function(){var t=this;this.loading=!0;var e=this.getParames(this.currentHtml);if(this.setRequestParam?this.beforeGetData(this.setRequestParam,this.params):this.setSearchObjToParams(e),"rtable"===e.type)return this.getRtableData(e),void(this.loading=!1);this.select(this.getPageUrl(),this.params,!0).then(function(e){t.sucessResult?t.afterGetData(t.sucessResult,e):e.list&&(t.tableData=e.list,t.params.pageSize=e.pageSize,t.params.pageNum=e.pageNum,t.totalCount=e.total,t.loading=!1)}),this.loading=!1}},{key:"getRtableData",value:function(t){var a=this;if(this.tableData=t.parent.formData[t.fileName]?t.parent.formData[t.fileName]:[],"new"!==t.rid){var r=!1;if(this.tableData.forEach(function(e){e.rid&&(r=!0,e.rid="".concat(t.rid))}),r){var e=this.getPageUrl();this.update({url:e.substring(0,e.lastIndexOf("/")),params:this.tableData}).then(function(e){a.sucessResult&&a.afterGetData(a.sucessResult,e),t.parent.formData[t.fileName]=e.data,a.tableData=e.data,a.loading=!1})}}this.loading=!1}},{key:"setSearchObjToParams",value:function(e){var t=[];for(var a in e&&"rtable"===e.type&&(t.push({key:"tableName",type:"eq",value:e.tableName}),t.push({key:e.fileName,type:"eq",value:e.rid})),this.serchObj)""!==this.serchObj[a]&&t.push({key:a,type:"eq",value:this.serchObj[a]});this.params.basicsParams=t}},{key:"getFormData",value:function(){var t=this;this.loading=!0;var e=this.getParames(this.currentHtml);return this.setRequestParam&&this.beforeGetData(this.setRequestParam),"new"===e.id?(this.sucessResult&&this.afterGetData(this.sucessResult,this.formData),void(this.loading=!1)):this.select("".concat(this.getFromUrl(),"/").concat(e.id)).then(function(e){if(t.sucessResult)return t.afterGetData(t.sucessResult,e),void(t.loading=!1);t.formData=e,t.loading=!1})}},{key:"add",value:function(){this.edit({id:"new"})}},{key:"edit",value:function(e){if(this.replaceEdit)this.interruptJump(this.replaceEdit,e);else{var t;t=this.SetNextParams?this.SetNextParams(e):{id:e.id,type:"form",parent:this};var a=this.getParames(this.currentHtml);if("rtable"!==a.type){var r=this.currentHtml.substring(0,this.currentHtml.length-1);this.setParames(r,t),this.replaceJump?this.interruptJump(this.replaceJump,e):this.$router.push({path:"/".concat(r,"/").concat(e.id)})}else this.rtableEdit(a,e)}}},{key:"rtableEdit",value:function(e,t){this.setParames("exam",{type:"rform",id:t.id,tableName:e.tableName,fileName:e.fileName,rid:e.rid,parent:this,rparent:e.parent}),this.replaceJump&&this.interruptJump(this.replaceJump,t)}},{key:"deleteRow",value:function(r){var i=this;this.$confirm("确认删除？","友情提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var e=i.getParames(i.currentHtml);if("rtable"!==e.type){var t=i.getPageUrl(),a=t.substring(0,t.lastIndexOf("/"));i.Del({url:"".concat(a,"/").concat(r.id)}).then(function(e){1===e.code?i.alertMsg?i.afterGetData(i.alertMsg,{type:"del",result:e}):i.message("服务器未知错误","友情提示"):i.message("数据删除成功","友情提示"),i.filterByserchObj()})}else i.delteRtable(e,r)}).catch(function(){i.$message({type:"info",message:"操作失败"})})}},{key:"delteRtable",value:function(e,t){for(var a=0;a<e.parent.formData[e.fileName].length;a++)JSON.stringify(t)===JSON.stringify(e.parent.formData[e.fileName][a])&&e.parent.formData[e.fileName].splice(a,1)}},{key:"resetForm",value:function(e){this.$router.go(-1)}},{key:"submitForm",value:function(e){var a=this;console.log(e),this.$refs[e].validate(function(e){if(!e)return!1;var t=a.getParames(a.currentHtml);if(!a.eidtLastFormData||a.beforeSubmit(a.eidtLastFormData,a.formData))if("new"===t.id){if("rform"===t.type)return void a.rformSubmit(t);if("rmform"===t.type)return void a.rmSumbmitForm(t);a.insert({url:a.getFromUrl(),params:[a.formData]}).then(function(e){if(0===e.code){if(a.formData=e.data[0],a.message("数据保存成功","友情提示"),a.replaceJump)return void a.interruptJump(a.replaceJump);a.$router.go(-1)}else a.message(e.msg,"友情提示")})}else a.update({url:a.getFromUrl(),params:[a.formData]}).then(function(e){if(a.beforResetFormData)a.submitSucess(a.beforResetFormData,e);else if(0===e.code){if(a.message("修改数据成功","友情提示"),a.formData=e.data[0],a.replaceJump)return void a.interruptJump(a.replaceJump);a.$router.go(-1)}else a.message(e.msg,"友情提示")})})}},{key:"rformSubmit",value:function(e){e.rparent.formData[e.fileName]||(e.rparent.formData[e.fileName]=[]),this.formData.tableName=e.tableName,this.formData.rid=e.rid,e.rparent.formData[e.fileName].push(this.formData),e.parent.tableData=e.rparent.formData[e.fileName],this.beforResetFormData&&(this.submitSucess(this.beforResetFormData,this.formData),this.message("数据保存成功","友情提示")),this.replaceJump?this.interruptJump(this.replaceJump):this.$router.go(-1)}},{key:"rmSumbmitForm",value:function(e){e.parent[e.fileName]=this.formData,this.message("数据保存成功","友情提示"),this.beforResetFormData?this.submitSucess(this.beforResetFormData,e.parent[e.fileName]):this.replaceJump?this.interruptJump(this.replaceJump):this.$router.go(-1)}},{key:"message",value:function(e,t){var a=this;this.$alert(e,t,{confirmButtonText:"确定",callback:function(e){a.$message({type:"info",message:"action: ".concat(e)})}})}},{key:"messageSure",value:function(e,t,a,r){var i=this;this.$confirm(e,t,{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){a(r),i.$message({type:"success",message:"操作成功!"})}).catch(function(){i.$message({type:"info",message:"操作失败"})})}},{key:"getTree",value:function(e,t,a){var r={},i={};e.forEach(function(e){r.hasOwnProperty(e.pid)&&0!==e.pid?r[e[a]].push(e):0!==e[a]&&(r[e[a]]=[e]),i[e.id]=1});var s=[];return e.forEach(function(e){e[t]=r[e.id],0===e.pid?s.push(e):i.hasOwnProperty(e.pid)||s.push(e)}),s}},{key:"geturl",value:function(e){return"".concat(this.serverUrl.base[this.servername]).concat(e)}},{key:"handleSizeChange",value:function(e){this.params.pageSize=e,this.filterByserchObj()}},{key:"handleCurrentChange",value:function(e){this.params.pageNum=e,this.filterByserchObj()}},{key:"handleClick",value:function(e,t){}},{key:"select",value:function(e,t,a,r){var i;return t&&a&&(i={dynameicParams:JSON.stringify(t)},r&&(Array.isArray(r)?r.forEach(function(e){i[e.key]=e.value}):i[r.key]=r.value)),this.Get({url:e,params:i}).then(function(e){return e.data})}},{key:"remove",value:function(e,t,a,r){var i;return t&&a&&(i={dynameicParams:JSON.stringify(t)},r&&(Array.isArray(r)?r.forEach(function(e){i[e.key]=e.value}):i[r.key]=r.value)),this.Del({url:e,params:i}).then(function(e){return e.data})}},{key:"cascaderMake",value:function(e,t){for(var a=[],r=0;r<t.length;r++)a.push(t[r]);var i={};this.getKeyObj(e,a,i);for(var s=e,n=0;n<t.length-1;n++){var l=t[n].chiled;s=this.getchiled(s,i[l],t[n])}var u=t[t.length-1];return s.forEach(function(e){e.label=e[u.label],e.value=e[u.value]}),s}},{key:"getchiled",value:function(e,t,a){for(var r=0;r<t.length;r++)for(var i=0;i<e.length;i++)if(t[r][a.parent]===e[i][a.chiled][a.parent]){t[r].children||(t[r].children=[]),e[i].label=e[i][a.label],e[i].value=e[i][a.value],t[r].children.push(e[i]);break}return t}},{key:"getKeyObj",value:function(e,t,a){if(0<t.length){var r=t[0].chiled,i=[];if("root"===r)return;e.forEach(function(e){i.push(e[r])}),a[r]=i,t.splice(0,1),this.getKeyObj(i,t,a)}}},{key:"created",value:function(){var e=this.getParames(this.currentHtml);e&&0<=e.type.search("table")?this.filterByserchObj():e&&0<=e.type.search("form")&&this.getFormData()}}]),s}(E.default),f=z()(c.prototype,"Get",[i],{configurable:!0,enumerable:!0,writable:!0,initializer:null}),h=z()(c.prototype,"Del",[s],{configurable:!0,enumerable:!0,writable:!0,initializer:null}),p=z()(c.prototype,"update",[n],{configurable:!0,enumerable:!0,writable:!0,initializer:null}),g=z()(c.prototype,"insert",[l],{configurable:!0,enumerable:!0,writable:!0,initializer:null}),b=z()(c.prototype,"upfile",[u],{configurable:!0,enumerable:!0,writable:!0,initializer:null}),d=z()(c.prototype,"serverUrl",[o],{configurable:!0,enumerable:!0,writable:!0,initializer:null}),m=c))||m)}}]);
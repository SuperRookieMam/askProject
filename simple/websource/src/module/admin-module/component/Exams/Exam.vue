<template>
  <div>
    <el-form :model="formData"
             ref="formData"
             label-width="100px"
             size="mini">
      <el-tabs>
        <el-tab-pane>
          <!--<el-row>-->
          <!--<el-col :span="12">-->
          <!--<el-form-item label="所属菜品" prop="score">-->
          <!--<many-to-one-cascader-->
          <!--:data-arr="foods"-->
          <!--@select-change="selectChange"-->
          <!--:props="props"-->
          <!--:rule-arr="ruleArr"-->
          <!--v-if="temp"/>-->
          <!--</el-form-item>-->
          <!--</el-col>-->
          <!--</el-row>-->
          <el-row>
            <el-col :span="8">
              <el-form-item label="题目类型: " prop="subjectName">
                {{ formData.subjectName }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="答案类型: " prop="choose">
                {{ formData.choose }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="分值" prop="score">
                <el-input type="number" @change="checkAndInsert1" v-model="formData.score"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="题目描述" prop="description">
                <el-input type="textarea"
                          :autosize="{ minRows: 3, maxRows: 6}"
                          @change="checkAndInsert1"
                          v-model="formData.description"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-for="(item,index) in formData.results" :key="index">
            <!--<el-form-item label="答案" prop="results">-->
            <!--<el-table :data="formData.results">-->
            <!--<el-table-column label="答案" prop="description">-->
            <!--<template>-->
            <!--<el-input/>-->
            <!--</template>-->
            <!--</el-table-column>-->
            <!--<el-table-column label="分值" prop="score">-->
            <!--<template>-->
            <!--<el-input/>-->
            <!--</template>-->
            <!--</el-table-column>-->
            <!--<el-table-column label="是否正确" prop="right" :formatter="statusFormatter">-->
            <!--<template>-->
            <!--<el-input/>-->
            <!--</template>-->
            <!--</el-table-column>-->
            <!--</el-table>-->
            <!--</el-form-item>-->
            <el-col :span="3">
              <el-row>
                <el-col>
                  <el-form-item label="" prop="right">
                    <el-switch
                      @change="checkAndInsert1"
                      v-model="item.right"
                      active-text="正确"
                      inactive-text="错误"/>
                  </el-form-item>
                  <el-form-item label="分值" prop="score">
                    <el-input-number @change="checkAndInsert1" v-model="item.score"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="18">
              <el-form-item label="答案" prop="description">
                <el-input @change="checkAndInsert1"
                          v-model="item.description"
                          type="textarea"
                          :autosize="{ minRows: 4, maxRows: 4}"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <!--<el-col :offset="12">-->
            <!--<el-button type="warning" round @click="checkAndInsert1">检查并确认</el-button>-->
            <!--</el-col>-->
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
  import {Component, Mixins, Prop} from 'vue-property-decorator'
  import vue from 'vue'
  import ManyToOneCascader from '../../../../components/cascader/ManyToOneCascader'
@Component({
components: {
ManyToOneCascader
}
})
  export default class Exam extends Mixins(vue) {
@Prop({type: Object, default: () => {}})
exam

formData = {}

created () {
this.formData = this.exam
}

  checkAndInsert1 () {
    this.$emit('add-exam', this.formData)
  }

  // currentHtml = 'exam'
  // options = [{label: '味型', value: '味型', type: '四选一'},
  //             {label: '主料', value: '主料', type: '八选四'},
  //             {label: '辅料', value: '辅料', type: '十选六'},
  //             {label: '调料', value: '调料', type: '十五选十'},
  //             {label: '成品特色', value: '成品特色', type: '四选一'},
  //             {label: '渊源及文化', value: '渊源及文化', type: '六选三'}]
  //
  // changeSelect () {
  //   this.options.forEach(ele => {
  //     if (this.formData.subjectName === ele.value) {
  //       this.formData.choose = ele.type
  //       if (ele.type === '四选一') {
  //         this.formData.chooseNum = 4
  //         this.formData.rightNum = 1
  //       } else if (ele.type === '八选四') {
  //         this.formData.chooseNum = 8
  //         this.formData.rightNum = 4
  //       } else if (ele.type === '十选六') {
  //         this.formData.chooseNum = 10
  //         this.formData.rightNum = 6
  //       } else if (ele.type === '六选三') {
  //         this.formData.chooseNum = 6
  //         this.formData.rightNum = 3
  //       } else if (ele.type === '十五选十') {
  //         this.formData.chooseNum = 15
  //         this.formData.rightNum = 10
  //       } else {
  //         this.message('无此选择类型请选择，请检查', '友情提示')
  //       }
  //     }
  //   })
  // }
  //
  // getFromUrl () {
  //   return this.geturl(this.serverUrl.ask.examUpdate)
  // }
  //
  // replaceJump (data) {
  //   let params = this.getParames('exam')
  //   this.formData = data
  //   params.parent.dialogVisible = false
  // }
  //
  // rules = {}
  }
</script>

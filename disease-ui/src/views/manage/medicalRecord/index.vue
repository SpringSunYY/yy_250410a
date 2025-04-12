<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="病历编号" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入病历编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入所属用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用模板" prop="templateId">
        <el-input
          v-model="queryParams.templateId"
          placeholder="请输入使用模板"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="病历标题" prop="recordTitle">
        <el-input
          v-model="queryParams.recordTitle"
          placeholder="请输入病历标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="严重程度" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择严重程度" clearable>
          <el-option
            v-for="dict in dict.type.d_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入所属部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="daterangeUpdateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="是否共享" prop="isShared">
        <el-select v-model="queryParams.isShared" placeholder="请选择是否共享" clearable>
          <el-option
            v-for="dict in dict.type.d_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:medicalRecord:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:medicalRecord:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:medicalRecord:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:medicalRecord:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="medicalRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="病历编号" align="center" v-if="columns[0].visible" prop="recordId"/>
      <el-table-column label="所属用户" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="userId"
      />
      <el-table-column label="使用模板" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="templateId"
      />
      <el-table-column label="病历标题" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="recordTitle"
      />
      <el-table-column label="病历图片" align="center" v-if="columns[4].visible" prop="recordImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.recordImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="病历内容" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="content"
      />
      <el-table-column label="严重程度" align="center" v-if="columns[6].visible" prop="level">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.d_level" :value="scope.row.level"/>
        </template>
      </el-table-column>
      <el-table-column label="所属部门" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="deptId"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="createBy"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[10].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否共享" align="center" v-if="columns[11].visible" prop="isShared">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.d_common_whether" :value="scope.row.isShared"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:medicalRecord:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:medicalRecord:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改病历信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入所属用户"/>
        </el-form-item>
        <el-form-item label="使用模板" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入使用模板"/>
        </el-form-item>
        <el-form-item label="病历标题" prop="recordTitle">
          <el-input v-model="form.recordTitle" placeholder="请输入病历标题"/>
        </el-form-item>
        <el-form-item label="病历图片" prop="recordImage">
          <image-upload v-model="form.recordImage"/>
        </el-form-item>
        <el-form-item label="病历内容">
          <el-input type="textarea" v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="严重程度" prop="level">
          <el-radio-group v-model="form.level">
            <el-radio
              v-for="dict in dict.type.d_level"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="所属部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入所属部门"/>
        </el-form-item>
        <el-form-item label="是否共享" prop="isShared">
          <el-radio-group v-model="form.isShared">
            <el-radio
              v-for="dict in dict.type.d_common_whether"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listMedicalRecord,
  getMedicalRecord,
  delMedicalRecord,
  addMedicalRecord,
  updateMedicalRecord
} from '@/api/manage/medicalRecord'

export default {
  name: 'MedicalRecord',
  dicts: ['d_level', 'd_common_whether'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '病历编号', visible: true },
        { key: 1, label: '所属用户', visible: true },
        { key: 2, label: '使用模板', visible: true },
        { key: 3, label: '病历标题', visible: true },
        { key: 4, label: '病历图片', visible: true },
        { key: 5, label: '病历内容', visible: true },
        { key: 6, label: '严重程度', visible: true },
        { key: 7, label: '所属部门', visible: true },
        { key: 8, label: '创建人', visible: true },
        { key: 9, label: '创建时间', visible: true },
        { key: 10, label: '更新时间', visible: true },
        { key: 11, label: '是否共享', visible: true },
        { key: 12, label: '备注', visible: true }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 病历信息表格数据
      medicalRecordList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordId: null,
        userId: null,
        templateId: null,
        recordTitle: null,
        recordImage: null,
        content: null,
        level: null,
        deptId: null,
        createBy: null,
        createTime: null,
        updateTime: null,
        isShared: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: '所属用户不能为空', trigger: 'blur' }
        ],
        recordTitle: [
          { required: true, message: '病历标题不能为空', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '病历内容不能为空', trigger: 'blur' }
        ],
        level: [
          { required: true, message: '严重程度不能为空', trigger: 'change' }
        ],
        deptId: [
          { required: true, message: '所属部门不能为空', trigger: 'blur' }
        ],
        createBy: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        isShared: [
          { required: true, message: '是否共享不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询病历信息列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listMedicalRecord(this.queryParams).then(response => {
        this.medicalRecordList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        recordId: null,
        userId: null,
        templateId: null,
        recordTitle: null,
        recordImage: null,
        content: null,
        level: null,
        deptId: null,
        createBy: null,
        createTime: null,
        updateTime: null,
        isShared: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加病历信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getMedicalRecord(recordId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改病历信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateMedicalRecord(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addMedicalRecord(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const recordIds = row.recordId || this.ids
      this.$modal.confirm('是否确认删除病历信息编号为"' + recordIds + '"的数据项？').then(function() {
        return delMedicalRecord(recordIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/medicalRecord/export', {
        ...this.queryParams
      }, `medicalRecord_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

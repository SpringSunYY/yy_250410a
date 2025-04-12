<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="备份编号" prop="backupId">
        <el-input
          v-model="queryParams.backupId"
          placeholder="请输入备份编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备份时间">
        <el-date-picker
          v-model="daterangeBackupTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['manage:dataBackup:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['manage:dataBackup:edit']"-->
      <!--        >修改</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:dataBackup:remove']"
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
          v-hasPermi="['manage:dataBackup:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataBackupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="备份编号" align="center" v-if="columns[0].visible" prop="backupId"/>
      <el-table-column label="备份时间" align="center" v-if="columns[1].visible" prop="backupTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.backupTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备份内容" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="backupContent"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="createBy"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRestore(scope.row)"
            v-hasPermi="['manage:dataBackup:edit']"
          >恢复数据
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:dataBackup:remove']"
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

    <!-- 添加或修改数据备份记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="备份内容" prop="backupContent">
          <el-input :rows="5" v-model="form.backupContent" type="textarea" placeholder="请输入内容"/>
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
  listDataBackup,
  getDataBackup,
  delDataBackup,
  addDataBackup,
  updateDataBackup,
  restore
} from '@/api/manage/dataBackup'

export default {
  name: 'DataBackup',
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '备份编号', visible: true },
        { key: 1, label: '备份时间', visible: true },
        { key: 2, label: '备份内容', visible: true },
        { key: 3, label: '创建人', visible: true }
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
      // 数据备份记录表格数据
      dataBackupList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 创建人时间范围
      daterangeBackupTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        backupId: null,
        backupTime: null,
        createBy: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        backupTime: [
          { required: true, message: '备份时间不能为空', trigger: 'blur' }
        ],
        backupContent: [
          { required: true, message: '备份内容不能为空', trigger: 'blur' }
        ],
        createBy: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询数据备份记录列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeBackupTime && '' != this.daterangeBackupTime) {
        this.queryParams.params['beginBackupTime'] = this.daterangeBackupTime[0]
        this.queryParams.params['endBackupTime'] = this.daterangeBackupTime[1]
      }
      listDataBackup(this.queryParams).then(response => {
        this.dataBackupList = response.rows
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
        backupId: null,
        backupTime: null,
        backupContent: null,
        createBy: null
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
      this.daterangeBackupTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.backupId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加数据备份记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const backupId = row.backupId || this.ids
      getDataBackup(backupId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改数据备份记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.backupId != null) {
            updateDataBackup(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addDataBackup(this.form).then(response => {
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
      const backupIds = row.backupId || this.ids
      this.$modal.confirm('是否确认删除数据备份记录编号为"' + backupIds + '"的数据项？').then(function() {
        return delDataBackup(backupIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleRestore(row) {
      this.$modal.confirm('是否确认恢复数据备份记录编号为"' + row.backupId + '"的数据项？').then(function() {
        return restore(row.backupId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('恢复成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/dataBackup/export', {
        ...this.queryParams
      }, `dataBackup_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

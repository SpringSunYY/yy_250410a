<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="提醒编号" prop="reminderId">
        <el-input
          v-model="queryParams.reminderId"
          placeholder="请输入提醒编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联病历" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入关联病历"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提醒时间">
        <el-date-picker
          v-model="daterangeRemindTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="提醒内容" prop="message">
        <el-input
          v-model="queryParams.message"
          placeholder="请输入提醒内容"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已读" prop="isRead">
        <el-select v-model="queryParams.isRead" placeholder="请选择已读" clearable>
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
          v-hasPermi="['manage:recordReminder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:recordReminder:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:recordReminder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:recordReminder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordReminderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="提醒编号" align="center" v-if="columns[0].visible" prop="reminderId" />
        <el-table-column label="关联病历" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="recordId" />
        <el-table-column label="提醒时间" align="center" v-if="columns[2].visible" prop="remindTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.remindTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="提醒内容" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="message" />
        <el-table-column label="已读" align="center" v-if="columns[4].visible" prop="isRead">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.d_common_whether" :value="scope.row.isRead"/>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:recordReminder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:recordReminder:remove']"
          >删除</el-button>
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

    <!-- 添加或修改病历提醒对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联病历" prop="recordId">
          <el-input v-model="form.recordId" placeholder="请输入关联病历" />
        </el-form-item>
        <el-form-item label="提醒时间" prop="remindTime">
          <el-date-picker clearable
            v-model="form.remindTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择提醒时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="提醒内容" prop="message">
          <el-input v-model="form.message" placeholder="请输入提醒内容" />
        </el-form-item>
        <el-form-item label="已读" prop="isRead">
          <el-radio-group v-model="form.isRead">
            <el-radio
              v-for="dict in dict.type.d_common_whether"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listRecordReminder, getRecordReminder, delRecordReminder, addRecordReminder, updateRecordReminder } from "@/api/manage/recordReminder";

export default {
  name: "RecordReminder",
  dicts: ['d_common_whether'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '提醒编号', visible: true },
          { key: 1, label: '关联病历', visible: true },
          { key: 2, label: '提醒时间', visible: true },
          { key: 3, label: '提醒内容', visible: true },
          { key: 4, label: '已读', visible: true },
          { key: 5, label: '备注', visible: true },
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
      // 病历提醒表格数据
      recordReminderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeRemindTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reminderId: null,
        recordId: null,
        remindTime: null,
        message: null,
        isRead: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recordId: [
          { required: true, message: "关联病历不能为空", trigger: "blur" }
        ],
        remindTime: [
          { required: true, message: "提醒时间不能为空", trigger: "blur" }
        ],
        message: [
          { required: true, message: "提醒内容不能为空", trigger: "blur" }
        ],
        isRead: [
          { required: true, message: "已读不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询病历提醒列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeRemindTime && '' != this.daterangeRemindTime) {
        this.queryParams.params["beginRemindTime"] = this.daterangeRemindTime[0];
        this.queryParams.params["endRemindTime"] = this.daterangeRemindTime[1];
      }
      listRecordReminder(this.queryParams).then(response => {
        this.recordReminderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reminderId: null,
        recordId: null,
        remindTime: null,
        message: null,
        isRead: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeRemindTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reminderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加病历提醒";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reminderId = row.reminderId || this.ids
      getRecordReminder(reminderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改病历提醒";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reminderId != null) {
            updateRecordReminder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecordReminder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reminderIds = row.reminderId || this.ids;
      this.$modal.confirm('是否确认删除病历提醒编号为"' + reminderIds + '"的数据项？').then(function() {
        return delRecordReminder(reminderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/recordReminder/export', {
        ...this.queryParams
      }, `recordReminder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

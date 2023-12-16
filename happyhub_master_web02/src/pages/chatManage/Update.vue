<template>
    <div>
        <div>通知列表</div>
        <br>
        <div>
            <div style="display: flex;justify-content: center;align-items: center;">
                <el-input style="width: 400px" v-model="keyword" placeholder="请输入您需要查询的信息"/>
                <el-button @click="getNotificationList">查询</el-button>
            </div>
            <div style="margin-top: 10px">
                <el-button @click="toggleSelection()">删除选中</el-button>
            </div>
            <el-table :data="notificationList" @selection-change="selectionChange" style="width: 100%" :stripe="true">
                <el-table-column type="selection" width="55"/>
                <el-table-column prop="notificationId" align="center" label="通知id" min-width="80"/>
                <el-table-column prop="userId" align="center" label="用户id" min-width="80"/>
                <el-table-column prop="title" align="center" label="通知标题" min-width="80"/>
                <el-table-column prop="message" align="center" label="通知内容" min-width="80"/>
                <el-table-column prop="status" align="center" label="状态" min-width="80"/>
                <el-table-column prop="created_at" align="center" label="发布时间" min-width="80"/>
                <el-table-column align="center" label="操作" min-width="80">
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.row),dialogFormVisible=true">修改</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
                <template #empty>
                    <el-empty description="暂无通知数据"/>
                </template>
            </el-table>

            <!--      <el-pagination background  layout="prev, pager, next" :total="100" />-->
        </div>
    </div>

    <el-dialog class="custom-dialog" v-model="dialogFormVisible" title="修改通知">
        <el-form :model="form">
            <el-form-item label="用户id&nbsp&nbsp&nbsp">
                <el-select v-model="notification.userId" placeholder="请选择用户id">
                    <el-option :value="userId" v-for="(userId,index) of userIds" :key="userId">{{
                        userId
                        }}
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="通知标题">
                <el-input v-model="notification.title"/>
            </el-form-item>
            <el-form-item label="通知内容">
                <el-input :rows="10" v-model="notification.message" type="textarea"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="onSubmit(),dialogFormVisible = false">
                确定
            </el-button>
            <el-button @click="dialogFormVisible = false">取消</el-button>
        </div>
    </el-dialog>

</template>
<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";


const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const notificationList = ref([]);
const keyword = ref('');
const ids = ref([]);
const notification = ref();
const dialogFormVisible = ref(false)
const userIds = ref([]);


const getNotificationList = () => {
    $http.get('/notification/getAll?keyword=' + keyword.value).then(resp => {
        notificationList.value = resp.data;
    })
}


const selectionChange = (selection) => {
    console.log("多选框选中数据");
    for (let selectionKey in selection) {
        if (ids.value.indexOf(selection[selectionKey].notificationId) === -1) {
            ids.value.push(selection[selectionKey].notificationId);
        }
    }
}
const toggleSelection = () => {
    console.log("多选框删除选中");
    console.log(ids.value)
    $http.get('/notification/deleteGroup?ids=' + ids.value.join(',')).then(() => {
        location.reload();
    })
}

const getUserIds = () => {
    $http.get('/notification/getAllUserId').then(resp => {
        console.log(resp.userIds)
        userIds.value = resp.userIds
        console.log(userIds.value)
    })
}

const handleEdit = row => {
    notification.value = row;
    getUserIds();
    console.log(notification.value)
}

const onSubmit = () => {
    console.log(notification.value)
    $http.post('/notification/updateNotification', notification.value).then(() => {

    })
}
const handleDelete = (row) => {
    console.log(row.notificationId);
    $http.get('/notification/deleteOne?id=' + row.notificationId).then(() => {
        location.reload();
    })
}
onMounted(() => {
    getNotificationList();
})
</script>


<style scoped lang="scss">
.notification-container {
    padding: 20px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    margin: 20px;

    > div:first-child {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
        text-align: center;
    }

    .search-area {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;

        .el-input {
            margin-right: 10px;
            border-radius: 4px;

            &:focus {
                border-color: #409eff;
                box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
            }
        }

        .el-button {
            border-radius: 4px;
            &:hover {
                transform: translateY(-2px);
            }
        }
    }

    .el-table {
        border-radius: 4px;
        border: 1px solid #ebeef5;

        .el-table__empty-block {
            border-top: 1px solid #ebeef5;
        }
    }

    .el-button {
        margin-right: 10px;
        &:last-child {
            margin-right: 0;
        }
    }

    .custom-dialog {
        .el-dialog__header {
            font-weight: bold;
            background-color: #f5f5f5;
            border-bottom: 1px solid #ebeef5;
        }

        .el-dialog__body {
            padding: 20px;
        }

        .dialog-footer {
            border-top: 1px solid #ebeef5;
            padding: 10px;
            background-color: #f5f5f5;
            display: flex;
            justify-content: flex-end;

            .el-button {
                margin-left: 10px;
                border-radius: 4px;
                &:hover {
                    transform: translateY(-2px);
                }

                &.el-button--primary {
                    background-color: #409eff;
                    border-color: #409eff;
                    color: #fff;
                    &:hover {
                        background-color: #66b1ff;
                        border-color: #66b1ff;
                    }
                }
            }
        }
    }
}
</style>

<template>
    <div class="account">
        <div class="search">
            <el-form @submit="onSearch">
                <el-form-item>
                    <el-form-item label="用户名">
                        <el-input v-model="searchForm.username" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间" :label-width="120">
                        <el-date-picker v-model="searchForm.createTime"
                                        type="daterange"
                                        unlink-panels
                                        date-format="YYYY-MM-DD"
                                        value-format="YYYY-MM-DD"
                                        range-separator="至"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期"
                        />
                    </el-form-item>
                </el-form-item>
                <el-form-item>
                    <el-form-item label="状态">
                        <el-radio-group v-model="searchForm.status">
                            <el-radio :label="-1">所有</el-radio>
                            <el-radio :label="2">正常</el-radio>
                            <el-radio :label="1">禁用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="更新时间" :label-width="120">
                        <el-date-picker v-model="searchForm.updateTime"
                                        type="daterange"
                                        unlink-panels
                                        date-format="YYYY-MM-DD"
                                        value-format="YYYY-MM-DD"
                                        range-separator="至"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期"
                        />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" native-type="submit">
                            <el-icon :size="24">
                                <Search/>
                            </el-icon>
                        </el-button>
                    </el-form-item>
                </el-form-item>
            </el-form>
        </div>
        <el-table :data="users">
            <el-table-column label="账号" prop="username"></el-table-column>
            <el-table-column label="创建时间" prop="createdAt"></el-table-column>
            <el-table-column label="更新时间" prop="updatedAt"></el-table-column>
            <el-table-column label="状态">
                <template #default="{row}">
                    <el-switch v-model="row.isDelete"
                               inline-prompt
                               style="--el-switch-on-color: #13ce66;--el-switch-off-color:  #ff4949"
                               :active-value="normal"
                               :inactive-value="deleted"
                               active-text="正常"
                               inactive-text="禁用"
                               @change="onStatusChange(row)"
                    />
                </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right">
                <template #default="{row}">
                    <el-button type="primary" size="small" @click="onAssociatedRoles(row)">关联角色</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="暂无账号数据"/>
            </template>
            <template #append>
                <el-pagination background
                               layout="prev,pager,next"
                               v-model:page-size="pageInfo.limit"
                               v-model:current-page="pageInfo.page"
                               :total="pageInfo.total"
                               @current-change="onPageChange"/>
            </template>
        </el-table>
        <associated-roles v-model:visible="associatedRolesDialog.visible" :user="associatedRolesDialog.users"/>
    </div>
</template>

<script setup>
import {getCurrentInstance, onActivated, onMounted, reactive, ref, watch} from "vue";
import {Search} from "@element-plus/icons-vue";
import AssociatedRoles from "@/pages/userManage/AssociatedRoles.vue";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const users = reactive([])
const searchForm = reactive({
    username: '',
    status: -1,
    createTime: [],
    updateTime: []
})
const pageInfo = reactive({
    page: 1,
    limit: 10,
    total: 0
})
const associatedRolesDialog = reactive({
    visible: false,
    users: {}
})

const onSearch = (e) => {
    e.preventDefault();
    loadAccounts();
}
const onStatusChange = (row) => {
    // TODO:发送请求，修改状态
}

const onAssociatedRoles = (row) => {
    associatedRolesDialog.users = row;
    associatedRolesDialog.visible = true;
}

const onPageChange = (newPage) => {
    pageInfo.page = newPage; // 更新当前页码
    loadAccounts(); // 执行查询操作
}
const loadAccounts = () => {


    let params = {
        page: pageInfo.page,
        limit: pageInfo.limit,
        username: searchForm.username,
        createdAt: searchForm.createTime,
        updatedAt: searchForm.updateTime,
        isDelete: searchForm.status
    }

    let searchString = '';
    if (searchForm.createTime.length > 0) {
        searchString += `createTime[0]=${searchForm.createTime[0]}`
        if (searchForm.createTime.length > 1) {
            searchString += `&createTime[1]=${searchForm.createTime[1]}`
        }
    }
    if (searchForm.updateTime.length > 0) {
        searchString += `&updateTime[0]=${searchForm.updateTime[0]}`
        if (searchForm.updateTime.length > 1) {
            searchString += `&updateTime[1]=${searchForm.updateTime[1]}`
        }
    }

    // if (searchForm.status !== -1){
    //   params.status = searchForm.status;
    // }
    console.log(searchString);
    $http.get('/user?' + encodeURI(searchString), {params}).then(res => {
        users.length = 0;
        users.push(...res.data);
        pageInfo.total = res.count;
    })
}
watch(() => associatedRolesDialog.visible, (val) => {
    if (!val) {
        loadAccounts();
    }
})
onMounted(loadAccounts);
onActivated(loadAccounts);
</script>

<style scoped lang="scss">
.account {
    width: 100%;
    height: 100%;

    .el-form-item {
        .el-button {
            margin-left: 10px;
            transition: all 0.3s; /* 添加按钮过渡效果 */
            &:hover {
                transform: scale(1.1); /* 鼠标悬停时按钮放大 */
            }
        }
    }

    .el-switch {
        transition: all 0.3s; /* 添加开关过渡效果 */
    }

    .el-table {
        .el-table-column {
            &.is-right {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .el-button {
                margin-left: 5px;
            }
        }
    }

    .el-pagination {
        margin: 10px;
    }
}
.fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s;
}
.fade-enter, .fade-leave-to {
    opacity: 0;
}
</style>
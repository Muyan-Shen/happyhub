package cn.shenmuyan.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * 账号角色映射表
 * @TableName user_role_mapping
 */
@Data
public class UserRoleMapping implements Serializable {
    /**
     * 用户ID
     */
    private Integer usersId;

    /**
     * 角色ID
     */
    private Integer roleId;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserRoleMapping other = (UserRoleMapping) that;
        return (this.getUsersId() == null ? other.getUsersId() == null : this.getUsersId().equals(other.getUsersId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUsersId() == null) ? 0 : getUsersId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", usersId=").append(usersId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
package cn.shenmuyan.bean;

/**
 * users_isdelete状态类
 * @Title: DeleteStatus
 * @Author ShenMuyan
 * @Package cn.shenmuyan.typeHandler
 * @Date 2023/12/12 22:59
 */

public class DeleteStatus {

    private final Integer status;

    DeleteStatus(Integer status) {
        this.status = status;
    }



    public Integer getStatus() {
        return status;
    }


    public static Integer fromString(String s) {
        switch (s) {
            case "deleted":
                return 1;
            case "normal":
                return 2;
            default:
                throw new IllegalArgumentException("Invalid value for DeleteStatus: " + s);
        }
    }
    public static String fromInt(int i) {
        switch (i) {
            case 1:
                return "deleted";
            case 2:
                return "normal";
            default:
                throw new IllegalArgumentException("Invalid value for DeleteStatus: " + i);
        }
    }
}

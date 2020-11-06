package com.shawntime.architect.notes.concurrency.bit;

/**
 * 权限操作类
 */
public class Permissions {

    /**
     * 二进制第1位是否允许查询，0表示否，1表示是
     * 1 << 0 = 0001  = 1
     */
    public static final int ALLOW_SELECT = 1 << 0;

    /**
     * 二进制第2位是否允许新增，0表示否，1表示是
     * 1 << 1 = 0010  = 2
     */
    public static final int ALLOW_INSERT = 1 << 1;

    /**
     * 二进制第3位是否允许修改，0表示否，1表示是
     * 1 << 2 = 0100 = 4
     */
    public static final int ALLOW_UPDATE = 1 << 2;

    /**
     * 二进制第4位是否允许删除，0表示否，1表示是
     * 1 << 3 = 1000 = 8
     */
    public static final int ALLOW_DELETE = 1 << 3;


    /**
     * 目前所拥有的权限状态
     */
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 新增1个或者多个权限
     * 按位或，有1为1，全0为0，按位或后有1的位置全都有了权限
     */
    public void addPermission(int flag) {
        this.flag = this.flag | flag;
    }

    /**
     * 删除1个或多个权限
     * 先按位取反，这样不需要删除的位置为1，删除的位置为0，在按位与，将对于为0的位置置0
     *
     */
    public void removePermission(int flag) {
        this.flag = this.flag & ~flag;
    }

    /**
     * 是否有某个操作的权限
     * 先按位与后如果操作位对应的是1，则操作位继续为1，否则全为0，得到的结果要么为flag，要么全为0
     */
    public boolean hasPermission(int flag) {
        return (this.flag & flag) == flag;
    }

    public boolean hasNotPermission(int flag) {
        return (this.flag & flag) == 0;
    }

    /**
     * flag : 0, insert : false
     * flag : 0, delete : false
     * flag : 0, update : false
     * flag : 0, select : false
     * 添加查询权限
     * flag : 1, insert : false
     * flag : 1, delete : false
     * flag : 1, update : false
     * flag : 1, select : true
     * 添加修改权限
     * flag : 5, insert : false
     * flag : 5, delete : false
     * flag : 5, update : true
     * flag : 5, select : true
     * 添加删除权限
     * flag : 13, insert : false
     * flag : 13, delete : true
     * flag : 13, update : true
     * flag : 13, select : true
     * 添加插入权限
     * flag : 15, insert : true
     * flag : 15, delete : true
     * flag : 15, update : true
     * flag : 15, select : true
     * 去掉查询权限
     * flag : 14, insert : true
     * flag : 14, delete : true
     * flag : 14, update : true
     * flag : 14, select : false
     * 去掉修改权限
     * flag : 10, insert : true
     * flag : 10, delete : true
     * flag : 10, update : false
     * flag : 10, select : false
     * 去掉删除权限
     * flag : 2, insert : true
     * flag : 2, delete : false
     * flag : 2, update : false
     * flag : 2, select : false
     * 去掉插入权限
     * flag : 0, insert : false
     * flag : 0, delete : false
     * flag : 0, update : false
     * flag : 0, select : false
     */
    public static void main(String[] args) {
        // 默认没有任何权限
        int flag = 0;
        Permissions permissions = new Permissions();
        permissions.setFlag(flag);
        permissions.print(permissions);
        // 添加查询权限
        System.out.println("添加查询权限");
        permissions.addPermission(Permissions.ALLOW_SELECT);
        permissions.print(permissions);
        // 添加修改权限
        System.out.println("添加修改权限");
        permissions.addPermission(Permissions.ALLOW_UPDATE);
        permissions.print(permissions);
        // 添加删除权限
        System.out.println("添加删除权限");
        permissions.addPermission(Permissions.ALLOW_DELETE);
        permissions.print(permissions);
        // 添加插入权限
        System.out.println("添加插入权限");
        permissions.addPermission(Permissions.ALLOW_INSERT);
        permissions.print(permissions);

        // 去掉查询权限
        System.out.println("去掉查询权限");
        permissions.removePermission(Permissions.ALLOW_SELECT);
        permissions.print(permissions);

        // 去掉修改权限
        System.out.println("去掉修改权限");
        permissions.removePermission(Permissions.ALLOW_UPDATE);
        permissions.print(permissions);

        // 去掉删除权限
        System.out.println("去掉删除权限");
        permissions.removePermission(Permissions.ALLOW_DELETE);
        permissions.print(permissions);

        // 去掉插入权限
        System.out.println("去掉插入权限");
        permissions.removePermission(Permissions.ALLOW_INSERT);
        permissions.print(permissions);
    }

    private void print(Permissions permissions) {
        System.out.println("flag : " + permissions.getFlag() + ", insert : " + permissions.hasPermission(Permissions.ALLOW_INSERT));
        System.out.println("flag : " + permissions.getFlag() + ", delete : " + permissions.hasPermission(Permissions.ALLOW_DELETE));
        System.out.println("flag : " + permissions.getFlag() + ", update : " + permissions.hasPermission(Permissions.ALLOW_UPDATE));
        System.out.println("flag : " + permissions.getFlag() + ", select : " + permissions.hasPermission(Permissions.ALLOW_SELECT));
    }

}

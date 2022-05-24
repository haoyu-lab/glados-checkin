package com.example.gladoscheckin.common;

public interface Status {
    /**
     * 服务器端出问题
     */
    static final int SERVER_ERROR = 500;
    static final String SERVER_ERROR_MSG = "服务器繁忙,请稍后";

    int QUERY_ERROR = 501;
    String QUERY_ERROR_MSG = "接口错误";
    /**
     * 请求成功
     */
    static final int SUCCESS = 200;
    static final String SUCCESS_MSG = "操作成功";

    /**
     * 请求成功
     */
    static final int ERROR = 250;
    static final String ERROR_MSG = "操作失败";


    static final int UNLOGIN_USERT = 207;
    static final String UNLOGIN_USER_MSGT = "请重新登录";


    /**
     * 用户尚未登录
     */
    static final int UNLOGIN_USER = 208;
    static final String UNLOGIN_USER_MSG = "用户尚未登录，请重新登录后再操作";

    /**
     * 参数不合法
     */
    static final int PARAMETER_ILLEGAL = 209;
    static final String PARAMETER_ILLEGAL_MSG = "参数不合法";

    /**
     * 用户名不存在
     */
    static final int UNAME_NOT_EXIST = 210;
    static final String UNAME_NOT_EXIST_MSG = "用户名或密码错误";

    /**
     * 登陆成功
     */
    static final int SUCCESS_LOGIN = 211;
    static final String SUCCESS_LOGIN_MSG = "登录成功";

    /**
     * 插入成功
     */
    static final int SUCCESS_INSERT = 212;
    static final String SUCCESS_INSERT_MSG = "插入成功";

    /**
     * 删除成功
     */
    static final int SUCCESS_DELETE = 213;
    static final String SUCCESS_DELETE_MSG = "删除成功";

    /**
     * 修改成功
     */
    static final int SUCCESS_UPDATE = 214;
    static final String SUCCESS_UPDATE_MSG = "修改成功";

    /**
     * 查询成功
     */
    static final int SUCCESS_SELECT = 215;
    static final String SUCCESS_SELECT_MSG = "查询成功";

    /**
     * 插入失败
     */
    static final int ERROR_INSERT = 216;
    static final String SERROR_INSERT_MSG = "插入失败";
    static final String ERROR_INSERT_MSG = "插入失败";

    /**
     * 插入失败
     */
    static final int NO_INSERT_ERROR = 503;
    static final String NO_INSERT_ERROR_MSG = "插入无效数据";
    /**
     * 删除成功
     */
    static final int ERROR_DELETE = 217;
    static final String ERROR_DELETE_MSG = "删除失败";

    /**
     * 修改成功
     */
    static final int ERROR_UPDATE = 218;
    static final String ERROR_UPDATE_MSG = "修改失败";

    /**
     * 查询失败
     */
    static final int ERRORSELECT = 219;
    static final String ERROR_SELECT_MSG = "查询失败";

    /*
     *注销成功
     */
    static final int SUCCESS_LOGOUT = 220;
    static final String SUCCESS_LOGOUT_MSG = "注销成功";
    /*
     *注销失败
     */
    static final int ERROR_LOGOUT = 221;
    static final String ERROR_LOGOUT_MSG = "注销失败";

    static final int NULL_CP_NAME = 222;
    static final String NULL_CP_NAME_MSG = "无交易对手名字";

    static final int SUCCESS_ADJUST = 223;
    static final String SUCCESS_ADJUST_MSG = "调级成功";

    static final int ERROR_ADJUST = 224;
    static final String ERROR_ADJUST_MSG = "调级失败";

    static final int AUDIT_NO_PERMISSION = 300;
    static final String AUDIT_NO_PERMISSION_MSG = "无审批权限";
    static final int AUDIT_NO_CONFIG = 301;
    static final String AUDIT_NO_CONFIG_MSG = "未配置流程";

    String REJECT = "4";
    String REJECT_MSG = "审批驳回";

    static final String AGREE = "100";
    static final String AGREE_MSG = "审批通过";

    int LOGIN_SUCCESS = 200;
    String LOGIN_SUCCESS_MSG = "登录成功";

    int LOGOUT_SUCCESS = 200;
    String LOGOUT_SUCCESS_MSG = "退出成功";

    int LOGOUT_FILE = 221;
    String LOGOUT_FILE_MSG = "退出失败";

    int USER_NOT_EXIST = 0;
    String USER_NOT_EXIST_MSG = "用户不存在";

    int USER_EXIST = 225;
    String USER_EXIST_MSG = "用户名已存在";

    int USERNAME_EXIST = 227;
    String USERNAME_EXIST_MSG = "用户代码已存在";

    int PASSWORD_ERROR = 1;
    String PASSWORD_ERROR_MSG = "用户密码错误";

    int ROLE_ERROR = 226;
    String ROLE_ERROR_MSG = "角色名已存在";

    int COMPOSE_NAME_ERROR = 228;
    String COMPOSE_NAME_ERROR_MSG = "组合名已存在";

    int COMPOSE_CODE_ERROR = 229;
    String PARAM_ERROR_MSG = "参数有误";

    int DICT_CODE_ERROR = 230;
    String DICT_CODE_ERROR_MSG = "字典代码已存在";

    int ITEM_CODE_ERROR = 231;
    String ITEM_CODE_ERROR_MSG = "条目代码已存在";

    String PERMISSION_SUCCESS_MSG = "授权成功";

    int DOWNLOAD_CODE_ERROR = 232;
    String DOWNLOAD_CODE_ERROR_MSG = "下载失败";

    String DOWNLOAD_CODE_SUCCESS_MSG = "下载成功";

    int FILE_CODE_ERROR = 233;
    String FILE_CODE_ERROR_MSG = "文件格式不支持";

    int LABEL_CODE_ERROR = 234;
    String LABEL_CODE_ERROR_MSG = "自定义标签已存在";

    int AUDIT_CODE_ERROR = 234;
    String AUDIT_CODE_ERROR_MSG = "审批已结束";

    int USER_ERROR_PARAM = 256;
    String USER_ERROR_PARAM_MSG = "用户已禁用";

    int ISTITUTION_CODE_ERROR = 241;
    String ISTITUTION_CODE_ERROR_MSG = "自定义主机构关系已存在";

    int ISTITUTION_CODE_NO = 244;
    String ISTITUTION_CODE_MSG = "该下挂机构不存在";


    int FLOW_NAME_ERROR = 257;
    String FLOW_NAME_ERROR_MSG = "流程名称已存在";

    int FLOW_TRANSCODE_ERROR = 258;
    String FLOW_TRANSCODE_ERROR_MSG = "已存在启用的业务类型";

    int FLOW_STATUS_ERROR = 259;
    String FLOW_STATUS_ERROR_MSG = "禁用失败，存在未结束的审批";

    int FLOW_DELETE_ERROR = 260;
    String FLOW_DELETE_ERROR_MSG = "删除失败，存在未结束的审批";

    int FLOW_SAVE_ERROR = 261;
    String FLOW_SAVE_ERROR_MSG = "保存失败，存在未结束的审批";

    int FLOW_EXIST_ERROR = 262;
    String FLOW_EXIST_ERROR_MSG = "保存失败，流程已不存在";

    int FLOW_PARAM_CH_ERROR = 263;
    String FLOW_PARAM_CN_ERROR_MSG = "参数中文名称已存在";

    int FLOW_PARAM_EN_ERROR = 264;
    String FLOW_PARAM_EN_ERROR_MSG = "参数英文名称已存在";

    int USER_ROLE_ERROR = 265;
    String USER_ROLE_ERROR_MSG = "用户未拥有任何角色信息";

    int COMPOSITION_NAME_ERROR = 266;
    String COMPOSITION_NAME_ERROR_MSG = "组合名称已存在";

    int COMPOSITION_SHORTNAME_ERROR = 267;
    String COMPOSITION_SHORTNAME_ERROR_MSG = "组合简称已存在";

    int COMPOSITION_DELETE_ERROR = 268;
    String COMPOSITION_DELETE_ERROR_MSG = "组合已存在头寸，无法禁用";

    int CONTERPARTY_LEVEL_ERROR = 269;
    String CONTERPARTY_LEVEL_ERROR_MSG = "修改失败，该流程不是草稿状态";

    int CONTERPARTY_LEVEL_ERROR2 = 270;
    String CONTERPARTY_LEVEL_ERROR2_MSG = "修改失败，修改前后级别不能一致";

    int ORGRANK_ERROR = 271;
    String ORGRANK_ERROR_ERROR_MSG = "无机构数据，请重新执行模型";

    int RANKMODEL_UPDATE_NAME_ERROR = 272;
    String RANKMODEL_UPDATE_NAME_ERROR_MSG = "模型名称不能重复";

    int RANKMODEL_UPDATE_STATUS_ERROR = 273;
    String RANKMODEL_UPDATE_STATUS_ERROR_MSG = "同一模型类型仅能启用一个";

    int FILEUP_STATUS_ERROR = 274;
    String FILEUP_STATUS_ERROR_MSG = "有机构处于待审批状态，请通过之后再次发起操作";

    int FILEUP_LEVEL_ERROR = 275;
    String FILEUP_LEVEL_ERROR_MSG = "调整前后级别不能一致";

    int MUST_NULL = 276;
    static final String MUST_NULL_MSG = "必填字段不可为空";
}

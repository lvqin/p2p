﻿package cn.mw.p2p.unitily;

public class MsgEnum {
	
	/**
	 * webservice命名空间
	 */
	public static final String NAME_SPACE = "urn:cu";

	/**
	 * Handler 消息内容列表
	 */
	public static final int LOGIN_ERROR = 0; // 登陆失败
	public static final int LOGIN_SUCCESS = 1; // 登陆成功
	public static final int SERVER_ERROR = 2; // 服务器异常
	public static final int SERVER_NO_DATA = 3; // 服务器无数据
	public static final int DATA_DETAIL = 4; // 有数据，提示详细信息
	public static final int GO_ORDER = 5; // 可以点菜
	public static final int ERROR = 6; // 错误操作
	public static final int OK = 7; // 操作正常
	public static final int NEW_ORDER_FINASH = 8; // 新增更新完成
	public static final int UPDATE_ORDER_FINASH = 9; // 修改更新完成
	public static final int DELETE_ORDER_FINASH = 10; // 删除更新完成
	public static final int ERROR_ORDER_FINASH = 11; // 错误更新完成
	public static final int MAKE_ORDER_OK = 12; // 数据发送完成
	public static final int MAKE_ORDER_ERROR = 13; // 数据发送错误
	public static final int BASE_MODIFY_OK = 14; // 信息修改成功
	public static final int PASSWORD_MODIFY_OK = 15; // 信息修改成功
	public static final int EMAIL_EXISTS = 16; // 邮箱已经存在
	public static final int BASE_ERROR = 17; // 基本信息修改错误
	public static final int PASSWORD_ERROR = 18; // 密码修改错误
	public static final int PASSWORD_OLD_REEOR = 19; // 原密码错误
	public static final int MCU_VERSION_DOWN = 20;// MCU版本过低
	public static final int USERNAME_NULL = 21;// 用户名不存在
	public static final int LOGIN_FUNCTION = 22;// 登录方法名
	public static final int PLAYURL_FUNCTION = 23;// 获取播放地址方法名
	public static final int PLAYURL_SUCCESS = 24;// 获取播放地址成功
	public static final int PLAYURL_FAILE = 25;// 获取播放地址失败
	public static final int DEVICE_EXISTS_NULL = 27;// 设备不存在
	public static final int GETPARAM_SUCCESS = 28;//获取设备配置参数成功
	public static final int GETPARAM_ERROR = 29;//获取设备配置参数失败
	public static final int GETPARAM_FUNCTION = 30;//获取设备配置参数方法名
	public static final int SETPARAM_FUNCTION = 33;//设置设备配置参数方法名
	public static final int ADDDEV_FUNCTION = 34;//添加设备
	public static final int DELDEV_FUNCTION = 35;//删除设备
	public static final int GETPWD_FUNCTION = 36;//动态获取密码
	public static final int SENDMESSAGE_ERROR = 37;//发送短信失败
	public static final int GETEACCOUNT_FUNCTION = 38;//查询E帐户
	public static final int SETEACCOUNT_FUNCTION = 39;//设置E帐户
	public static final int SETDEVKEY_FUNCTION = 40;//设置设备密码
	public static final int SETMAXLINENUM_FUNCTION = 41;//设置同时观看人数
	public static final int REBOOTDEV_FUNCTION = 42;//重启摄像机
	public static final int HASHTOKEN_NULL = 43;//验证码不存在
	public static final int REG_FUNCTION = 44;//用户注册方法
	public static final int ACCOUNT_NOT_ACTIVE = 45;//用户未激活
	public static final int ACCOUNT_ISEXISTS = 46;//用户已经注册
	public static final int MSGPWD_ERROR = 47;//短信密码错误
	public static final int GETRECPOLICY_FUNCTION = 48;//获取录像策略
	public static final int DELRECPOLICY_FUNCTION = 49;//删除录像策略
	public static final int ADDRECPOLICY_FUNCTION = 50;//添加录像策略
	public static final int CONTROLPTZ_FUNCTION = 51;//云台控制
	public static final int STARTREC_FUNCTION = 52;//开始录像
	public static final int STOPREC_FUNCTION = 53;//停止录像
	public static final int STOPREC_SUCCESS = 54;//停止录像成功
	public static final int STARTREC_SUCCESS = 55;//开始录像成功
	public static final int GETSYSLOG_FUNCTION = 56;//获取系统日志
	public static final int SETDEVNAME_FUNCTION = 57;//设置设备名称
	public static final int UPGRADE_FUNCTION = 58;//远程升级通知
	public static final int GETDEVESION_FUNCTION = 59;//远程升级通知
	public static final int PLAYVIDEO_ERROR = 60;//播放视频失败
	public static final int DEVICE_EXIST = 68;//设备已经存在
	public static final int SETALARMSTATUS_FUNCTION = 69;//设置布防状态
	public static final int GETALARMSTATUS_FUNCTION = 70;//查询布防状态
	public static final int SELECTUSERINFO_FUNCTION = 71;//查询用户信息
	public static final int UPDATEUSERINFO_FUNCTION = 72;//修改用户信息
	public static final int SELECTDEVRECOR_FUNCTION = 73;//设备录像文件查询
	public static final int PLAYRECORDURL_FUNCTION = 74;//获取设备录像文件地址
	public static final int GETDEVCHANNELNAME_FUNCTION = 75;//获取设备通道名称
	public static final int SETUSERKEY_FUNCTION = 76;//设置用户密码
	public static final int GETALARMSTATUS_FAILE = 77;//查询布防状态失败
	public static final int SETALARMSTATUS_FAILE = 78;//设置布防状态失败
	public static final int REFDEV_FUNCTION = 79;//刷新设备列表方法
	public static final int GETAUTHCODE_FUNCTION = 80;//获取验证码方法
	public static final int SETDEVNAME_SUCCESS = 81;//设置设备名称成功
	public static final int SETDEVKEY_SUCCESS = 82;//设置设备密码成功
	public static final int SETMAXLINE_SUCCESS = 83;//设置同时观看人数成功
	public static final int SETDEVREBOOT_SUCCESS = 84;//设备重启成功
	public static final int DEV_IS_OTHERUSER = 85;//设备属于其他用户
	public static final int ADDSHARE_SUCCESS = 86;//设备分享成功
	public static final int CANCELSHARE_SUCCESS = 87;//取消设备分享成功
	public static final int ADDSHARE_FUNCTION = 88;//设备分享方法
	public static final int CANCELSHARE_FUNCTION = 89;//取消设备分享方法
	public static final int AGENTREQUEST_FAINLD = 90;//设备反向代理请求发送失败
	public static final int AGENTRESPONSE_FAINLD = 91;//设备反向大理请求超时
	public static final int NOT_PROXY = 92;//设备不支持反向代理
	public static final int ADRESS_NOTAVAILABLE = 93;//设备直连地址不可用
	public static final int NOT_STREAMMEDIA = 94;//无可用流媒体
	public static final int MAX_CONNECTED = 95;//已达设备最大直连数
	public static final int DEV_NOTONLINE = 96;//设备不在线
	public static final int REQUEST_FAINLD = 97;//请求失败
	public static final int OPERRATE_TIMEOUT =98;//操作超时
	public static final int PARAMETER_INVALID = 99;//参数无效
	public static final int OPERRATE_FAINLD = 100;//操作失败
	public static final int ACCOUNT_UNUSABLE = 101;//用户不可用
	//云台控制
	public static final int PTZ_UP = 61;
	public static final int PTZ_DOWN = 62;
	public static final int PTZ_LEFT = 63;
	public static final int PTZ_RIGHT = 64;
	public static final int SINGLEDOWN = 65;
	public static final int TIMEHIDDLEN = 66;
	public static final int BARHID = 67;
	//常用的枚举值
	public static final int SUCCESS = 31;//执行成功
	public static final int ACCOUNT_NOT_EXIST = 32;//用户不存在
	public static final int SESSION_TIMEOUT = 26;// SESSION过期
	public static final int MOVE_SUCCESS = 100;
	
	
	/**
	 * 列名约束 constraint
	 */
	public static final String TEXT_NOT_NULL = "text not null";
	public static final String TEXT_DEFAULT_NULL = "text default null";
	public static final String INTEGER_NOT_NULL = "integer not null";
	public static final String INTEGER_DEFAULT_NULL = "integer default null";
	
	/**
	 * 分辩率名称
	 */
	public static final String[] DIP_Name = { "请选择", "720p(1280*720)",
			"VGA(640*480)", "D1(704*576)", "CIF(176*144)", "QVGA(320*240)" };
	
	/**
	 * 分辩率值
	 */
	public static final String[] DIP_VALUE = { "0", "1", "2", "3", "4", "5" };
	
	/**
	 * 画质数据
	 */
	public static final String[] HUAZHI_NAME = { "请选择", "1", "2", "3", "4", "5" };

	public static final String[] HUAZHI_VALUE = { "0", "1", "2", "3", "4", "5" };
	
	/**
	 * 音频输入开关
	 */
	public static final String[] AUDIOINPUT_NAME = { "开启", "关闭" };

	public static final String[] AUDIOINPUT_VALUE = { "1", "0" };
	
	/**
	 * 录像策略
	 */
	public static final String SKIPTYPE_RECORDS = "0";
	
	/**
	 * 安全策略
	 */
	public static final String SKIPTYPE_SAFE = "1";
	
	/**
	 * 周期数据
	 */
	public static final String[] WEEK_NAME = {"请选择", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
	
	public static final String[] WEEK_VALUE = {"0", "1", "2", "3", "4", "5", "6", "7"};
	
	/**
	 * 休眠唤醒
	 */
	public static final String[] SLEEP_NAME = { "休眠", "唤醒" };

	public static final String[] SLEEP_VALUE = { "0", "1" };
	
	/**
	 * 同时观看人数
	 */
	public static final String[] PERSON = { "请选择", "1", "2", "3" };
	
	/**
	 * 码流类型
	 */
	public static final String[] STREAME_TYPE = { "请选择", "主码流", "子码流" };

	public static final String[] STREAME_VALUE = { "0", "1", "2" };
	
	public static final String[] NET_TYPE = { "无线", "有线" };
	
	/**
	 * 文件类型
	 */
	public static final String[] EXTENSIONS = new String[] {
		".mp4", 
		".flv", 
		".avi", 
		".wmv",
		".ts",
		".mkv",
		".rmvb",
		".3gp",
		".3gpp",
		".mp3",
		".mpg",
		".jpg",
		".gif",
		".bmp",
		".png"
	};
	
	/**
	 * 录像类型
	 */
	public static final String[] RECORD_TYPE_NAME = { "所有录像", "告警录像", "定时录像", "手动录像" };
	public static final String[] RECORD_TYPE_VALUE = { "0", "1" , "2", "3"};

}

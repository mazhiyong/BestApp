package com.lr.best.api;

/**
 */
public class MethodUrl {




    public static final String REFRESH_TOKEN = "token/refresh";//获取刷新的token
    public static final String tempToken        = "token";//获取临时Token
    public static final String imageCode        = "imgcode";//图形验证码
    public static final String headPath         = "user/uploadheadpic";//上传文件
    public static final String appVersion = "sys/appVersion";//更新app信息
    public static final String shareUrl = "user/codeurl";//注册分享的url

    public static final String nameCode = "code/all";//获取应用全局字典信息
    public static final String userAuthInfo = "verify/authinfo";//用户认证信息
    public static final String resetPassCode = "sms/pwd";// 重置密码获取短信验证码

    public static final String installCode= "sms/ca";// 安装证书 所需要的验证码操作
    public static final String tradeList = "acct/trade";// 交易记录列表
    public static final String borrowList = "loan/list";// 借款记录
    public static final String repaymentList = "repay/list";// 还款记录
    public static final String repayConfig = "repay/config";// 还款申请配置
    public static final String repayLixi = "repay/interest";// 利息计算

    public static final String shoumoneyList = "loan/pondinfo2";// 应收账款列表信息





    public static final String RESET_PASSWORD = "Register/forgotPassword";// 重置密码提交
    public static final String LOGIN_ACTION = "Register/userLogin";//登录
    public static final String REGIST_SMSCODE = "Register/code";//注册短信验证码
    public static final String REGIST_ACTION = "Register/userReg";//注册提交

    public static final String BANNNER_INFO = "Home/index";// 首页公告信息
    public static final String NOTICE_LIST = "Home/noticeList";//公告列表
    public static final String NOTICE_LIST_ITEM = "Home/noticeDetails";//公告列表
    public static final String LIST_INFO = "Home/listInfo";//首页实时数据
    public static final String HELP_INFO = "Home/helpDetails";//帮助中心详情
    public static final String LIST_HELP = "Home/helpList";//帮助中心列表




    public static final String USER_INFO = "Personal/index";//获取用户基本信息
    public static final String USER_HEAD_IMAGE = "Personal/portrait";//用户头像上传
    public static final String USER_NAME = "Personal/editName";//修改昵称
    public static final String UPDATE_PAYCODE = "Personal/editPwd";//修改支付密码
    public static final String IS_IDENTITY = "Personal/isIdentity";//是否实名认证
    public static final String IDENTITY_ACTIVE = "Personal/identityActive";//实名认证操作
    public static final String PAY_WAY_LIST = "Personal/paymentMethod";//收款方式列表
    public static final String UPLOAD_FILE = "Personal/upload";//上传图片文件
    public static final String UPDATE_BANK = "Personal/bankEdit";// 添加银行卡支付方式
    public static final String UPDATE_ALIPAY = "Personal/alipayEdit";//添加支付宝支付方式
    public static final String UPDATE_WECHAT = "Personal/wechatEdit";//添加微信支付方式
    public static final String CONTACT_US = "Personal/contactUs";//联系我们
    public static final String ABOUT_US = "Personal/aboutUs";//关于我们
    public static final String INVIAT_ATION = "Personal/invitation";//邀请链接
    public static final String EDIT_ACCOUNT = "Personal/editAccount";//修改账户手机号
    public static final String SAFE_CENTER = "Personal/safetyCenter";//安全中心
    public static final String USER_GVRP = "Register/gvrp";//用户协议
    public static final String MY_TREAM = "user_info/myTeam";//我的团队
    public static final String CHAT_RECEIVER = "Chart_info/obtain_chart";//客服聊天轮询
    public static final String CHAT_SEND = "Chart_info/send_chart";//客服发送消息
    public static final String CHAT_RECORD = "Chart_info/open_chat";//客服聊天记录








    public static final String CHAT_QRCODE = "chat_user/myQrcode";//我的二维码
    public static final String CHAT_SEARCH_FRIEND = "chat_user/selectFriend";//添加好友（搜索）
    public static final String CHAT_ADD_FRIEND = "chat_user/addFriend";//添加好友操作
    public static final String CHAT_FRIEND_APPLY = "chat_user/friendApply";//好友申请列表操作
    public static final String CHAT_AGREE_REFUSE = "chat_user/checkApply";//同意拒绝好友申请
    public static final String CHAT_MY_FRIENDS = "chat_user/myFriend";//获取好友列表
    public static final String CHAT_RECENTLY_LIST = "Chat/recentChat";//最近聊天列表
    public static final String CHAT_MY_GROUPS = "chat_group/myGroup";//我的群组
    public static final String CHAT_CREAT_GROUPS = "chat_group/createGroup";//创建群组
    public static final String CHAT_GROUPS_INFO = "chat_group/groupInfo";//群组信息
    public static final String CHAT_FRIEDN_INFO = "chat_user/friendDetail";//朋友信息
    public static final String CHAT_CHANAGE_STATUS = "chat_user/editFriendStatus";//修改置顶聊天/消息免打扰状态
    public static final String CHAT_REASON_LIST = "chat/complaintList";//投诉原因列表
    public static final String CHAT_REASON_DEAL = "chat/complaintActive";//投诉操作
    public static final String CHAT_QUERY_ID = "Chat/selectId";//根据融云id查询平台id
    public static final String CHAT_QUERY_RCID = "Chat/selectRcId";//根据平台id查询融云id
    public static final String CHAT_QUERY_USERINFO = "chat/userInfo";//通过融云ID查询用户信息
    public static final String CHAT_GROUP_CHANAGE_STATUS = "chat_group/editGroupStatus";//修改置顶聊天/消息免打扰状态
    public static final String CHAT_GROUP_EDIT_NAME = "chat_group/editGroup";//修改群名
    public static final String CHAT_GROUP_ADD = "chat_group/inviteGroup";//邀请入群
    public static final String CHAT_GROUP_ADD_MEMBER = "chat_group/addGroupActive";//申请入群
    public static final String CHAT_GROUP_DELETE = "chat_group/expelGroup";//删除群员
    public static final String CHAT_GROUP_ADMIN = "chat_group/administrators";//群管理员管理界面
    public static final String CHAT_GROUP_MANAGE = "chat_group/editStatus";//更改全体禁言/群员保护/群认证状态
    public static final String CHAT_GROUP_ADMIN_ADD = "chat_group/addAdmin";//添加管理员
    public static final String CHAT_GROUP_ADMIN_DELETE = "chat_group/cancelAdmin";//删除管理员
    public static final String CHAT_GROUP_TRANSFER = "chat_group/transfer";//转让群
    public static final String CHAT_GROUP_EXIT = "chat_group/quitGroup";//退出群聊
    public static final String CHAT_SWEEP_CODE = "chat/sweepCode";//扫码显示用户/群信息
    public static final String CHAT_SEND_NEWS = "chat_user/chatActive";//好友发送消息
    public static final String CHAT_GROUP_SEND_NEWS = "chat_group/chatActive";//群聊发送消息
    public static final String CHAT_SEND_RED = "chat_red/sendRed";//发红包操作
    public static final String CHAT_RED_PAGE = "chat_red/assets";//发红包页面信息
    public static final String CHAT_ZHUANZHANG_TYPE = "chat_red/coinType";//红包可以转账的类型
    public static final String CHAT_RED_INFO = "chat_red/redInfo";//点击查看红包信息
    public static final String CHAT_RED_RECORD = "chat_red/redRecord";//查看红包信息记录
    public static final String CHAT_ZHUANZHANG_PAGE = "chat_red/transferPage";//转账界面
    public static final String CHAT_SEND_ZHUANZHANG = "chat_red/transferActive";//转账操作
    public static final String CHAT_ZHUANZHANG_INFO= "chat_red/tranInfo";//查看转账信息
    public static final String CHAT_ZHUANZHANG_RECORD = "chat_red/tranRecord";//转账记录记录
    public static final String CHAT_TIME_LONG = "chat/chatTime";//聊天时长
    public static final String CHAT_GROUP_HEADIMG = "chat_group/editPortrait";//修改群头像
    public static final String CHAT_GROUP_RLEASE = "chat_group/dissolution";//解散群



















    public static final String AVIABLE_MONEY = "Contract/userContractInfo";//保证金动态收益信息
    public static final String CHICANG_LIST = "Contract/userDepot";//持仓列表
    public static final String WEITUO_LIST = "Contract/queryContractEntrust";//委托列表
    public static final String CHENGJIAO_LIST = "Contract/closeDepot";//成交列表
    public static final String PING_CANG = "contract_trade/contractSell";//平仓操作
    public static final String PING_CANG_ALL = "contract_trade/contractSellAll";//一键平仓操作
    public static final String CHE_XIAO = "contract_trade/cancelEntrust";//撤销操作
    public static final String PAIR_DEPTH = "Contract/queryPairDepth";//合约价格及合并深度信息
    public static final String CONTRACT_LEVER = "Contract/getContractLever";//获取杠杆信息
    public static final String AREA_ALL = "Contract/areaAll";//交易区
    public static final String COIN_ACCOUNT = "Contract/queryCoinAccount";//用户合约币、USDT资产
    public static final String CONTRACT_TRADE = "contract_trade/index";//交易买卖







    public static final String ENTRUST_LIST = "Coin/queryEntrust";//委托单查询
    public static final String COIN_AREAALL = "Coin/areaAll";//交易区列表
    public static final String AREA_ITEM = "Coin/queryArea";//获取对应交易区币对列表
    public static final String AREA_COIN_ACCOUNT = "Coin/queryCoinAccount";//账户当前交易区交易币可用
    public static final String CANCEL_WEITUO = "coin_trade/revocationOrder";//撤销委托
    public static final String CURRENT_PRICE = "Coin/coinCurrent";//币币查询当前币价格信息
    public static final String K_LINE = "Coin/k_chart";//K线图
    public static final String GUADAN_TRADE = "coin_trade/index";//买卖挂单操作
    public static final String COIN_DEPTH = "Coin/queryDepth";//市场深度数据(右侧买卖数据)
    public static final String COIN_ADD_CANCEL = "Coin/addOptional";//添加取消自选





    public static final String OTC_TRADE = "Outside/getPair";//法币列表交易区
    public static final String HISTORY_DINGDAN = "Outside/myMatch";//订单历史
    public static final String FB_ENTRUSTLIST = "Outside/entrustList";//法币买卖挂单列表
    public static final String USER_BUY_DEAL = "outside_trade/tradeEntrust";//用户买卖操作
    public static final String DING_INFO = "outside_trade/tradeMatch";//订单详情
    public static final String DING_CANCEL = "outside_trade/revocationMatch";//取消订单
    public static final String BUYER_SURE = "outside_trade/confirmPay";//买家确认支付
    public static final String SELLER_SURE = "outside_trade/confirmLoan";//卖家确认收到款
    public static final String USER_ENTRUST_DEAL = "outside_trade/index";//买卖挂单操作
    public static final String USER_ENTRUSTLIST = "Outside/myEntrust";//用户挂单记录
    public static final String CANCEL_ENTRUSTLIST = "outside_trade/doRevocation";//撤销挂单








    public static final String ZICHAN_ALL = "user_info/generalUsdtAssets";//总资产
    public static final String ZICHAN_ACCOUNT = "user_info/accountUsdt";//各账户资产信息
    public static final String CHONGTI_LIST = "user_info/pairLit";//充提币列表
    public static final String CHONG_BI = "user_info/rechargePage";//充币页面
    public static final String CHONG_BI_DEAL = "Finance/chargeCoin";//充币操作
    public static final String CHONGTI_RECORD_LIST = "user_info/withdrawalRecord";//充提币记录
    public static final String ADDRESS_LIST = "user_info/userAddress";//提币地址列表
    public static final String ADDRESS_ADD = "user_info/addAddress";//添加提币地址
    public static final String ADDRESS_DELETE = "user_info/delAddress";//删除地址列表
    public static final String ADDRESS_EDIT = "user_info/editAddress";//修改地址列表
    public static final String TI_BI = "Finance/withDrawalFee";//提现手续费/最小提币数量
    public static final String TI_BI_DEAL = "Finance/withdrawal";//提币操作
    public static final String HUANZHUAN_BI_TYPE = "user_info/symbolList";//可划转币类型
    public static final String ACCOUNT_AVAIABLE_MONEY = "user_info/assets";//用户某个账户 某个币可用余额
    public static final String HUAZHUAN_DEAL = "user_info/dealTransfer";//划转币操作
    public static final String HUAZHUAN_LIST = "user_info/transferRecordApp";//划转记录
    public static final String DUIHUAN_INFO = "Coin/changeChipInfo";//兑换信息
    public static final String DUIHUAN_ACTION = "user_info/changeToChip";//兑换操作
    public static final String DUIHUAN_LIST = "user_info/exchangeLog3";//兑换记录
    public static final String RELEASE_LIST = "user_info/releaseLog";//释放记录
    public static final String MY_DELIVERYINFO = "user_info/deliveryInfo";//我的交割金
    public static final String BOUNTY_INFO = "user_info/bountyInfo";//交割未释放指标和已释放指标
    public static final String DELIVERY_ACTION= "user_info/delivery";//交割
    public static final String MY_PROFIT= "user_info/profit";//我的分红
    public static final String TEAM_PROFIT= "user_info/teamProfit";//我的收益
    public static final String PADIAN_PROFIT = "user_info/padian";//帕点收益
















}

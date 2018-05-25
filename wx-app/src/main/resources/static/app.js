//app.js 
var https = require('utils/https.js')
function Login(code, userInfo) {
  var that = this 
  var nickName = userInfo.nickName;
  var avatarUrl = userInfo.avatarUrl;
  var gender = userInfo.gender;
  var language = userInfo.language;
  var url = `https://api.stiles.cc/wechat/onLogin?code=${code}&nickName=${nickName}&avatarUrl=${avatarUrl}&gender=${gender}&language=${language}`;
  https.get(url, function (res) {
    var token = res.token;
    wx.setStorageSync('token', token);
  },function(){
     //wx.setStorageSync('token', "defalutToken")
     // 在这里你要考虑到用户登录失败的情况 
  },false)
} 
App({
  globalData: {
    domain: "https://api.stiles.cc",
    detailUrl:"https://api.stiles.cc/novels/get",
    novelsUrl: "https://api.stiles.cc/novels",
    recommendUrl: "https://api.stiles.cc/novels/recommend",
    chaptersUrl: "https://api.stiles.cc/novels/getChapters",
    chaptersContentUrl: "https://api.stiles.cc/novels/getChaptersContent",
    categoryUrl: "https://api.stiles.cc/novels/getCategory",
    searchUrl: "https://api.stiles.cc/novels/search",
    feedbackUrl: "https://api.stiles.cc/feedback/send",
    novelSerialNumbersUrl: "https://api.stiles.cc/novels/getNovelSerialNumbers",
    searchKeywords: "https://api.stiles.cc/novels/getSearchKeywords",
    token:null,
    userInfo: null
  },
   onLaunch: function () {
    // wx.clearStorage();
    this.getUserInfo();
    this.removeCache();
  },
  removeCache:function(){
    var value = wx.getStorageSync('cateListTime')
    var nowTime = new Date().getTime();
    if(value){
      if (value < nowTime) {
        wx.removeStorage("cateList");
      }
    }
    value = wx.getStorageSync('searchKeysTime')
    if (value) {
      if (value < nowTime) {
        wx.removeStorage("searchKeysTime");
      }
    }
  },
  WechatLogin:function(){
    var that = this
    wx.login({
      success: function (res) {
        //登录成功 
        if (res.code) {
          // 这里是用户的授权信息每次都不一样 
          var code = res.code;
          wx.getUserInfo({
            success: function (info) {
              wx.setStorageSync('userInfo', info.userInfo);
              that.globalData.userInfo = info.userInfo;
              typeof cb == "function" && cb(that.globalData.userInfo)
              // 请求自己的服务器 
              Login(code, info.userInfo);
            },fail:function(e){
              console.log(e);
            }
          })
        }
      }, fail: function (e) {
         console.log(e);
      }
    })
  },
  getUserInfo: function (cb) {
    var that = this
    if (this.globalData.userInfo) {
      console.log(cb);
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
        wx.checkSession({
        success: function () {
          var userInfo=wx.getStorageSync('userInfo');
          if(userInfo){
            that.globalData.userInfo = userInfo;
          }else{
            that.WechatLogin();
          }
          console.log("success login");
        },fail(){
          that.WechatLogin();
        }});
    } 
   }
})
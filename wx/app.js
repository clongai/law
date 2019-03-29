//app.js
App({
  globalData: {
    url: 'http://127.0.0.1:8082/',
    //url: 'https://law.loadpeople.com/'
  }, 
  onLaunch: function () {
  },
  getOpenId: function () {
    var that = this;
    return new Promise(function (resolve, reject) {
      wx.login({
        success: function (res) {
          //code 获取用户信息的凭证
          if (res.code) {
            //请求获取用户openid
            wx.request({
              url: that.globalData.url + 'getOpenId',
              data: { "code": res.code },
              method: 'POST',
              header: {
                'Content-type': 'application/json'
              },
              success: function (res) {
                wx.setStorageSync('openId', res.data)
                var res = {
                  status: 200,
                  data: res.data
                }
                resolve(res);
              },
              fail: function (res){
                wx.showModal({
                  title: '提示',
                  showCancel: false,
                  content: 'app获取用户openid失败:' + res.errMsg,
                })
                reject('error');
              }
            });
          } else {
            wx.showModal({
              title: '提示',
              showCancel: false,
              content: '获取用户临时登录凭证失败:' + res.errMsg,
            })
            reject('error');
          }
        }
      })
    });
  }, 
})
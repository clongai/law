// pages/mine/mine.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasUserInfo: false,
    userInfo: '',
    relationData:'',
    relationDataCount:'',
    parentData:''
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onLoad: function () {
    if (wx.getStorageSync('userInfo')){
      this.setData({
        userInfo: wx.getStorageSync('userInfo')
      });
    }
    if (!wx.getStorageSync('openId')) {
      app.getOpenId().then(function (res) {
        if (res.status == 200) {
          this.setData({
            openId: wx.setStorageSync('openId', res.data)
          })
        }
      });
    } else {
      this.setData({
        openId: wx.getStorageSync('openId')
      })
    }

  },
  showQR: function () {
    wx.navigateTo({
      url: '../showQr/showQr',
    })
  },
  phoneCall:function(e){
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.phone
    })
  },
  copyTBL: function (e) {
    var self = this;
    wx.setClipboardData({
      data: 'qllm_service@163.com',
      success: function (res) {
        // self.setData({copyTip:true}),
        wx.showModal({
          title: '提示',
          content: '复制成功',
          success: function (res) {
            if (res.confirm) {
              console.log('确定')
            } else if (res.cancel) {
              console.log('取消')
            }
          }
        })
      }
    });
}
 
})
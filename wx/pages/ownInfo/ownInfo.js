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
    this.getRelationData();
  },
  showQR: function () {
    wx.navigateTo({
      url: '../showQr/showQr',
    })
  },
  getRelationData: function () {
    var that = this;
    wx.request({
      url: app.globalData.url + 'getRelationData',
      header: { 'Content-Type': 'application/json' },
      data: {
        openId: wx.getStorageSync('openId')
      },
      method: 'POST',
      success: res => {
        wx.setStorageSync('relationData', res.data.child)
        that.setData({
          relationData: res.data.child,
          relationDataCount: res.data.child.length,
          parentData: res.data.parent
        })
      }
    })
  }
})
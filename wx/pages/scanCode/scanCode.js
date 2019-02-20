// pages/mine/mine.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasUserInfo: false,
    userInfo: '',
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    qCode: ''
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onLoad: function () {
    if (wx.getStorageSync('userInfo')) {
      this.setData({
        userInfo: wx.getStorageSync('userInfo')
      });
    }
    if (wx.getStorageSync('qCode')) {
      this.setData({
        qCode: wx.getStorageSync('qCode')
      });
    } else {
      this.getwxacode();
    }
  },
  getwxacode: function () {
    var that = this;
    wx.request({
      url: app.globalData.url + 'getQCode',
      header: { 'Content-Type': 'application/json' },
      data: {
        scene: wx.getStorageSync('openId')
      },
      method: 'POST',
      success: res => {
        wx.setStorageSync('qCode', res.data)
        that.setData({
          qCode: res.data
        })
      }
    })
  },
  bindGetUserInfo: function (e) {
    var that = this;
    wx.showLoading({
      mask: true
    })
    wx.getUserInfo({
      success: function (res) {
        wx.setStorageSync('userInfo', res.userInfo)
        that.setData({
          userInfo: res.userInfo
        });
      },
      complete: function (res) {
        wx.hideLoading()
      }
    })
  }
})
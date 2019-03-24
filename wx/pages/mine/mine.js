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
  goMyRelation: function (e) {
    wx.navigateTo({
      url: '../myRelation/myRelation',
    })
  },
  goUseHelp: function (e) {
    wx.navigateTo({
      url: '../useHelp/useHelp',
    })
  },
  goOwnInfo: function(){
    wx.navigateTo({
      url: '../ownInfo/ownInfo',
    })
  },
  toChat: function (e) {
    // wx.navigateTo({
    //   url: '../chat/chat',
    // })
  },
  toRelationMe: function(e){
    wx.navigateTo({
      url: '../relationMe/relationMe',
    })
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onLoad: function () {
    if (wx.getStorageSync('userInfo')){
      this.setData({
        userInfo: wx.getStorageSync('userInfo')
      });
      this.updatePromoterInfo(this.data.userInfo.nickName, this.data.userInfo.avatarUrl);
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
    this.getwxacode();
    
   

  },
  showQR: function () {
    wx.navigateTo({
      url: '../showQr/showQr',
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
      complete: function (res){
        wx.hideLoading()
      }
    })
  },
  onShareAppMessage: function (e) {
    debugger
    var imgUrl = '/images/qcode.jpg'
    // if (this.data.qCode != '') {
    //   imgUrl = this.data.qCode
    // }
    var obj = {
      title: '',
      imageUrl: '',
      path: ''
    }
    obj.title = '清莲律盟'
    obj.imageUrl = imgUrl
    obj.path = '/pages/chooseType/chooseType?openId=' + this.data.openId
    return obj
    // return {
    //   title: '清莲律盟',
    //   imageUrl: imgUrl,
    //   path: '/pages/chooseType/chooseType?openId=' + this.openId
    // }
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
  updatePromoterInfo: function (nickName, avatarUrl) {
    var that = this;
    wx.request({
      url: app.globalData.url + 'updatePromoterInfo',
      header: { 'Content-Type': 'application/json' },
      data: {
        openId: wx.getStorageSync('openId'),
        nickName: nickName,
        avatarUrl:avatarUrl       
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
  scanCode: function () {
    wx.scanCode({
      onlyFromCamera: true,
      success(res) {
        console.log(res)
      }
    })
  }
})
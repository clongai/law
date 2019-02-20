// pages/payedOrderDetail/payedOrderDetail.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderId: null,
    orderOp:null,
    status: null,
    laywerName: '王晓明',
    degree: 3,
    area: '合同纠纷，婚姻纠纷，劳动争议，工伤，民间借贷，商事仲裁等',
    // message是初审已付款和终审已付款通用的
    message: '您好，我已收到您的诉讼请求，请按照下列要求准备材料，届时期待与您的会面。',
    prepare: 'XXXXX,XXXX',
    meetTime: '',
    contactPeople: '王晓明',
    contactPhone: '13811111111',
    payedPngBase64: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getDiamondBase64()
    this.getPayedBase64();
   
    console.log("status=" + options.status);
    this.setData({
      orderId: options.orderId,
      status: options.status
    });
    this.getPageInitData();
  },
  getPageInitData: function () {

    wx.request({
      url: app.globalData.url + 'getPayedPageInitData',
      data: {
        orderId: this.data.orderId
      },
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      success: res => {
        this.setData({
          orderOp: res.data,
          meetTime: res.data.acceptStartTime
        })
      }
    })
  },
  getPayedBase64: function () {
    wx.request({
      url: app.globalData.url + 'getBase64',
      data: {
        "fileName": "payed.png"
      },
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      success: res => {
        this.setData({
          payedPngBase64: res.data
        })
      }
    })
  },
  getDiamondBase64: function () {
    wx.request({
      url: app.globalData.url + 'getBase64',
      data: {
        "fileName": "diamond.png"
      },
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      success: res => {
        this.setData({
          diamondPngBase64: res.data
        })
      }
    })
  },
  orderDetail: function () {
    wx.navigateTo({
      url: '../orderDetail/orderDetail?orderId=' + this.data.orderId + '&status=' + this.data.status,
    })
  }
})
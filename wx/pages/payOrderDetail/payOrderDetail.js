//index.js
//获取应用实例

const app = getApp()

Page({
  data: {
    openId: '',
    status: null,
    showContract: false,
    current: 0,
    autoplay: false,
    indicatorDots: true,
    selectedPrice: null,
    agreed: false,
    serviceId: null,
    serviceLevel: null,
    payType: "预付费",
    firstTrailPriceList: [],
    finalTrailPriceList: [],
    prosecutor: '',
    diamondPngBase64: '',
    payPngBase64: '',
    body: '',
    hiddenmodalput: true,
    customerRefuseReason: '',
    termsType:'A'
  },
  onLoad: function (options) {
    this.getDiamondBase64()
    this.getPayBase64()
    this.setData({
      orderId: options.orderId,
      status: options.status,
      showContract: (options.status == '4' || options.status == '8') ? true : false,
    })
    this.getLawOrder()
    if (this.data.status == '1') {
      this.getLawSerivce()
    }
  },
  getLawOrder: function () {
    wx.request({
      url: app.globalData.url + 'getLawOrder',
      header: { 'Content-Type': 'application/json' },
      data: {
        orderId: this.data.orderId
      },
      method: 'POST',
      success: res => {
        var _obj = {
          prosecutor: '',
          showContract: this.data.showContract
        }
        // 整合当事人的名字
        for (let i = 0; i < res.data.lawPerson1.length; i++) {
          _obj.prosecutor += res.data.lawPerson1[i].personName
          if (res.data.lawPerson1[i + 1] !== null && res.data.lawPerson1[i + 1] !== undefined) {
            _obj.prosecutor += '/'
          }
        }
        // 终审需要同意服务条款
        if (res.data.lawOrder.status == '4' || res.data.lawOrder.status == '8') {
          _obj.showContract = true
        }
        this.setData(_obj);
        // 将请求到的数据与data整合
        for (var attr in res.data.lawOrder) {
          var obj = {};
          if (res.data.lawOrder[attr] != null && attr !== 'status') {
            obj[attr] = res.data.lawOrder[attr];
          }
          this.setData(obj);
        }
        if (this.data.status == '4' || this.data.status == '8') {
          /*var prePay = {
            price: null,
            interPrice: null,
            rate: '3%',
            type: "预付费"
          }
          var suffixPay = {
            price: null,
            interPrice: null,
            rate: '16%',
            type: "后付费"
          } 
          */
    
          var quetoA = {
            price: res.data.orderPO.quoteA,
            interPrice: res.data.orderPO.quoteA,
            rate: '3%',
            termsType:'A',
            type: "提供法律文书，当事人自行处理"
          }
          var quetoB = {
            price: res.data.orderPO.quoteB,
            interPrice: res.data.orderPO.quoteB,
            rate: '16%',
            termsType: 'B',
            type: "提供法律文书，全程跟踪指导"
          } 
          var quetoC = {
            price: res.data.orderPO.quoteC,
            interPrice: res.data.orderPO.quoteC,
            rate: '16%',
            termsType: 'C',
            type: "与山东聚青签署委托代理协议"
          } 
          //debugger;
          /*根据涉案金额计算中介费费*/
           /*prePay.price = res.data.lawOrder.involvingMoney
          prePay.interPrice = prePay.price * 0.03 //预付费
          suffixPay.price = res.data.lawOrder.involvingMoney
          suffixPay.interPrice = prePay.price * 0.15 //
          */
          /*根据代理金额计算中代理费*/
         /* prePay.price = res.data.lawOrder.agentMoney
          prePay.interPrice = res.data.orderPO.agentMoneyPay;
          suffixPay.price = res.data.lawOrder.agentMoney
          suffixPay.interPrice = res.data.orderPO.taxAgentMoneyPay;
           */
        

          var finalTrailPriceList = [quetoA, quetoB, quetoC]
          this.setData({
            finalTrailPriceList: finalTrailPriceList,
            selectedPrice: quetoA.interPrice
          })
        }


      }
    })
  },
  getLawSerivce: function () {
    wx.request({
      url: app.globalData.url + 'queryLawService',
      data: {},
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      success: res => {
        var arr = res.data
        this.setData({
          firstTrailPriceList: res.data,
          selectedPrice: res.data[0].serviceFee,
          serviceId: res.data[0].serviceId,
          body: res.data[0].serviceGroup + "-" + res.data[0].serviceProject
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
  getPayBase64: function () {
    wx.request({
      url: app.globalData.url + 'getBase64',
      data: {
        "fileName": "pay.png"
      },
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      success: res => {
        this.setData({
          payPngBase64: res.data
        })
      }
    })
  },
  swiperChange: function (e) {
    
    var obj = {
      selectedPrice: this.data.selectedPrice,
      payType: this.data.payType,
      serviceId: this.data.serviceId,
      serviceLevel: this.data.serviceLevel,
      body: this.data.body,
      termsType:this.data.termsType
    };
    if (this.data.status == '1') {
      obj.selectedPrice = this.data.firstTrailPriceList[e.detail.current].serviceFee
      obj.payType = '预付费'
      obj.serviceId = this.data.firstTrailPriceList[e.detail.current].serviceId
      obj.serviceLevel = this.data.firstTrailPriceList[e.detail.current].serviceLevel
    } else if (this.data.status == '4' || this.data.status == '8') {
      obj.selectedPrice = this.data.finalTrailPriceList[e.detail.current].interPrice
      obj.payType = '预付费'
      obj.termsType = this.data.finalTrailPriceList[e.detail.current].termsType;
    }
    
    this.setData(obj)
  },
  radioChange: function (e) {
    this.setData({
      agreed: true
    })
  },
  submit: function () {
    let payOrder = {
      "orderId": this.data.orderId,
      "fee": this.data.selectedPrice,
      "feeType": null,
      "payType": null,
      "tradeState": "0",
      "serviceId": this.data.serviceId,
      "serviceLevel": this.data.serviceLevel,
      "termsType":null
    }
    if (this.data.status == '') {
      payOrder.feeType = 0;//咨询费
    } else if (this.data.status == '4' || this.data.status == '8') {
      payOrder.feeType = 1;//中介费
      payOrder.termsType = this.data.termsType;
    }
    if (this.data.payType == '预付费') {
      payOrder.payType = 0;
    } else if (this.data.payType == '后付费') {
      payOrder.payType = 1;
    }
    if (!wx.getStorageSync('openId')) {
      app.getOpenId().then(function (res) {
        if (res.status == 200) {
          this.setData({
            openId: wx.getStorageSync('openId')
          })
          this.dopay(payOrder, this.data.status)
        }
      });
    } else {
      this.setData({
        openId: wx.getStorageSync('openId')
      })
      this.dopay(payOrder, this.data.status)
    }
  },
  dopay: function (payOrder, status) {
    debugger;
    wx.request({
      url: app.globalData.url + '/getWXPayParam',
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      data: {
        openId: this.data.openId,
        status: status,
        termsType: payOrder.termsType,
        lawPay: payOrder
      },
      success: res => {
        if (res.data.flag == "post-payment success") {
          wx.navigateTo({
            url: '../index/index',
          })
        } else {
          console.log(JSON.stringify(res))
          this.pay(res.data)
        }
      }
    })
  },
  pay: function (val) {
    wx.requestPayment({
      timeStamp: val.timeStamp,
      nonceStr: val.nonceStr,
      package: val.package,
      signType: 'MD5',
      paySign: val.paySign,
      success: function (res) {

      },
      fail: function (res) {
      },
      complete: function (res) {
        if (res.errMsg == 'requestPayment:ok') {
          wx.switchTab({
            url: '../index/index',
          })
        } else if (res.errMsg == 'requestPayment:fail cancel') {
          wx.showToast({
            title: '您已取消支付',
            icon: 'none',
            duration: 5000
          })
        }
      }
    })
  },
  modalinput: function () {
    this.setData({
      hiddenmodalput: !this.data.hiddenmodalput
    })
  },
  //取消按钮  
  cancel: function () {
    this.setData({
      hiddenmodalput: true
    });
  },
  //确认  
  confirm: function (e) {
    wx.request({
      url: app.globalData.url + 'customeRefuseOrder',
      header: { 'Content-Type': 'application/json' },
      method: 'POST',
      data: {
        orderId: this.data.orderId,
        refuseReason: this.data.customerRefuseReason
      },
      success: res => {
        this.setData({
          hiddenmodalput: true
        })
        wx.switchTab({
          url: '../index/index',
        })        
      }
    })
    
  },
  inputCustomerRefuseReason: function(e){
    this.setData({
      customerRefuseReason: e.detail.value
    })
  } 
})

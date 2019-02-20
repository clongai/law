//chooseType.js
//获取应用实例
const app = getApp()
import { Validator } from '../../utils/validate.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    openId: "",
    showModalStatus: false,
    phone: '',
    bg_mb_display: 'block',
    promoterInfo: '',
    isBindPromoterPhone: false
  },
  move: function () { },

  turnToManual: function () {
    if (!this.data.isBindPromoterPhone) {
      this.setData({
        showModalStatus: true,
        bg_mb_display: 'block'
      })
      return false;
    }
    wx.navigateTo({
      url: '../listDetail/listDetail',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
    var sceneFromQcode = decodeURIComponent(options.scene)
    var sceneFromShare = options.openId
    if (sceneFromQcode != "undefined") {
      this.dobind(sceneFromQcode)
    } else if (sceneFromShare != "undefined") {
      this.dobind(sceneFromShare)
    }
    
        // this.bindPromoter('oBy6H5Nue94I3lPqh0oY8i0Z2LSY', 'oBy6H5KGsaz44kTUEAJqDhZLzDTM')
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (!this.data.isBindPromoterPhone){
      this.setData({
        showModalStatus: true,
        bg_mb_display: 'block'
      })
    }
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },
   /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  },
  bindPromoterPhone: function (currentStatu,that){
    wx.request({
      url: app.globalData.url + 'bindPromoterPhone',
      header: { 'Content-Type': 'application/json' },
      data: {
        openId: this.data.openId,
        phone: this.data.phone
      },
      method: 'POST',
      success: function (res) {
        console.log(JSON.stringify(res))
        
        if (res.statusCode == 200 && res.data) {
          that.utilFun(currentStatu);
          that.setData({
            isBindPromoterPhone:true
          })
        }
      },
      fail: function () {
        wx.showToast({
          title: '系统异常！',
          icon: 'none',
          duration: 3000
        })
      }
    })
  },
  /**
   * 绑定普法宣传员上下级关系
   */
  bindPromoter: function (openId, parentOpenId) {
    var that = this;
    wx.request({
      url: app.globalData.url + 'bindPromoter',
      header: { 'Content-Type': 'application/json' },
      data: {
        openId: openId,
        parentOpenId: parentOpenId
      },
      method: 'POST',
      success: function (res) {
        console.log(JSON.stringify(res))
     
        that.setData({
          promoterInfo: res
        })
        that.beginLogin(that.data.promoterInfo);
        if (res.statusCode == 200 && res.data.status == 'new') {
          
          wx.showToast({
            title: '注册成功！',
            icon: 'success',
            duration: 3000
          })
        }
      },
      fail: function () {
        wx.showToast({
          title: '系统异常！',
          icon: 'none',
          duration: 3000
        })
      }
    })
  },
  dobind: function(scene) {
    if (scene != "undefined") {
      var that = this;
      if (!wx.getStorageSync('openId')) {
        app.getOpenId().then(function (res) {
          if (res.status == 200) {
            that.setData({
              openId: wx.getStorageSync('openId')
            })
            that.bindPromoter(that.data.openId, scene)
          }
        });
      } else {
        that.setData({
          openId: wx.getStorageSync('openId')
        })
        that.bindPromoter(that.data.openId, scene)
      }
    }

  },
  beginLogin: function(res){
    if (res!=""&&res.statusCode == 200) {

      if (res.data.promoter.promoterTel == null) {

        this.setData({
          showModalStatus: true,
          bg_mb_display: 'block'
        })
      }else{
        
        this.setData({
          showModalStatus: false,
          bg_mb_display: 'none',
          isBindPromoterPhone: true
        })
      }
    }
  }
  ,
  powerDrawer: function (e) {
    var currentStatu = e.currentTarget.dataset.statu;
    var isSure = e.currentTarget.dataset.sure;
    console.log(isSure)
    if (isSure){
      var validator = new Validator();
      validator.add('personPhone1', this.data.phone, [{
        strategy: 'isMobile',
        errorMsg: '请输入正确的' + this.data.phone + '手机号'
      }]);
      var errorMsg = validator.start();
      if (errorMsg) {
        wx.showModal({
          title: '提示',
          showCancel: false,
          content: errorMsg
        })
        return false;
      }
      this.bindPromoterPhone(currentStatu,this);
    }else{
      this.utilFun(currentStatu)
    }
  },
  inputEvent:function(e){
    console.log(e.detail.value)
    this.setData({
      phone: e.detail.value
    })
  },
  utilFun: function (currentStatu) {
    /* 动画部分 */
    // 第1步：创建动画实例   
    var animation = wx.createAnimation({
      duration: 200,  //动画时长  
      timingFunction: "linear", //线性  
      delay: 0  //0则不延迟  
    });

    // 第2步：这个动画实例赋给当前的动画实例  
    this.animation = animation;

    // 第3步：执行第一组动画  
    animation.opacity(0).rotateX(-100).step();

    // 第4步：导出动画对象赋给数据对象储存  
    this.setData({
      animationData: animation.export()
    })

    // 第5步：设置定时器到指定时候后，执行第二组动画  
    setTimeout(function () {
      // 执行第二组动画  
      animation.opacity(1).rotateX(0).step();
      // 给数据对象储存的第一组动画，更替为执行完第二组动画的动画对象  
      this.setData({
        animationData: animation
      })

      //关闭  
      if (currentStatu == "close") {
        this.setData(
          {
            showModalStatus: false,
            bg_mb_display: 'none'
          }
        );
      }
    }.bind(this), 200)

    // 显示  
    if (currentStatu == "open") {
      this.setData(
        {
          showModalStatus: true,
          bg_mb_display: 'block'
        }
      );
    }
  }
})
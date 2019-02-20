// pages/person/person.js
import { Validator } from '../../utils/validate.js'
import { getAge } from '../../utils/util.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    edit: true,
    personType: "",
    lawPerson: [{
      personType: "",
      personName: '',
      personAge: null,
      personCardNo: '',
      personPhone: '',
      personEmail:'',
      landline:''
    }]
  },
  cardBlur: function (e) {
    const personCardNo = this.data.lawPerson[e.currentTarget.dataset.idx].personCardNo;
    const age = getAge(personCardNo);
    var obj = {};
    const key = 'lawPerson[' + e.currentTarget.dataset.idx + '].personAge';
    obj[key] = age;
    this.setData(obj);

  },
  inputEvent: function (e) {
    var obj = {};
    const key = 'lawPerson[' + e.currentTarget.dataset.idx + '].' + e.currentTarget.dataset.key;
    obj[key] = e.detail.value;
    this.setData(obj);
  },
  /**
   * 校验方法
   */
  validateFunc: function () {
    const that = this;
    var validator = new Validator();
    for (let i = 0; i < that.data.lawPerson.length; i++) {
      (function(i) {
        const personType = that.data.lawPerson[i].personType;
        const personKey = personType === '1' ? '当事人' : (personType === '2' ? '对方当事人' : '关系人');
        for (let key in that.data.lawPerson[i]) {
          if (key === "personName") {
            const name = 'personName' + i;
            validator.add(name, that.data.lawPerson[i].personName, 'isNonEmpty', personKey+(i+1)+'姓名不能为空');
          } else if (key === 'personPhone') {
            const name = 'personPhone' + i;
            validator.add('personPhone1', that.data.lawPerson[i].personPhone, [{
              strategy: 'isMobile',
                errorMsg: '请输入正确的' + personKey + (i+1) +'手机号'
            }]);
          } else if (key === 'personEmail' && that.data.lawPerson[i].personEmail!="") {
            const name = 'personEmail' + i;
            validator.add('personEmail1', that.data.lawPerson[i].personEmail, [{
              strategy: 'isEmail',
              errorMsg: '请输入正确的' + personKey + (i + 1) + '邮箱号'
            }]);
          }
        }
      })(i)
    }
    
    var validate = validator.start();

    return validate;
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    try {
      let type = options.type;
      let edit = options.edit === 'false' ? false : true;
      const lawPerson = JSON.parse(options.lawPerson);
      if (lawPerson.length > 0) {
        this.setData({
          personType: type,
          lawPerson: lawPerson,
          edit: edit
        });
      } else {
        this.setData({
          personType: type,
          'lawPerson[0].personType': type,
          edit: edit
        });
      }
      var title = ""
      if (type === "1") {
        title = "当事人";
      }
      if (type === "2") {
        title = "对方当事人"
      }
      if (type === "3") {
        title = "关系人"
      }
      wx.setNavigationBarTitle({
        title: title
      })
    } catch (e) {
      console.log('unknow error!')
    }
  },
  addPerson: function (e) {
    const idx = this.data.lawPerson.length - 1;
    const person = this.data.lawPerson;
    person.push({
      personType: this.data.personType,
      personName: '',
      personAge: '',
      personCardNo: '',
      personPhone: ''
    });
    this.setData({
      lawPerson: person 
    });
  },
  submit: function () {
    var errorMsg = this.validateFunc();
    if (errorMsg) {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: errorMsg
      })
      return false;
    }
    const pages = getCurrentPages(); // 获取页面栈
    const currPage = pages[pages.length - 1]; // 当前页面
    const prevPage = pages[pages.length - 2]; // 上一个页面
    const key = 'person'+this.data.personType;
    const that = this;
    switch (that.data.personType) {
      case '1':
        prevPage.setData({
          person1: that.data.lawPerson // 假数据
        });
        break;
      case '2':
        prevPage.setData({
          person2: that.data.lawPerson // 假数据
        });
        break;
      case '3':
        prevPage.setData({
          person3: that.data.lawPerson // 假数据
        });
        break;
    }
    // 返回上一页
    wx.navigateBack({
      url: '../listDetail/listDetail',
    })
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

  }
})
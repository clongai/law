// Validator class
import formStrategies from './validateRules.js'
var Validator = function () {
  this.cache = [];
};

Validator.prototype.add = function (dom,value, rules, errorMsg) {
  var self = this;
  if (!(rules.constructor === Array)) {
    var ary = rules.split(':');
    setCache(ary, value, errorMsg);
  } else {
    for (var i = 0, rule; rule = rules[i++];) {
      (function (rule) {
        var ary = rule.strategy.split(':');
        var errorMsg = rule.errorMsg;
        setCache(ary, value, errorMsg);
      })(rule);
    }
  }
  function setCache(ary, value, errorMsg) {
    self.cache.push(function () {
      var strategy = ary.shift();
      ary.unshift(value);
      ary.push(errorMsg);
      return formStrategies[strategy].apply(dom, ary);
    })
  }
};

Validator.prototype.start = function () {
  for (var i = 0, validatorFunc; validatorFunc = this.cache[i++];) {
    var msg = validatorFunc();
    if (msg) {
      return msg;
    }
  }
};

module.exports = {
  Validator: Validator
}
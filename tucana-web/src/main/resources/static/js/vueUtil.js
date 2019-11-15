var common = function () {
    return {
        f1: function () {
            console.log("this is common f1().");
        },
        f2: function () {
            console.log(" out vist f222222().");
        },
        // 小写转大写
        toUppercase: function (o, parName) {
            if (o && o[parName]) {
                o[parName] = o[parName].toUpperCase();
            }
        }
    }
}();
if (typeof (Vue) == "function") Vue.prototype.common = common;
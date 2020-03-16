var common = function () {
    return {
        dateFormatter: function (row, column, cellValue, index) {
            if (cellValue) {
                return moment(cellValue).format("YYYY-MM-DD HH:mm:ss");
            }
            return '';
        }
    }
}();
if (typeof (Vue) == "function") Vue.prototype.common = common;
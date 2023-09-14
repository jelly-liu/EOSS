/**
 * 扩展easyui的validator插件rules，支持更多类型验证
 */
$.extend($.fn.validatebox.defaults.rules, {
    sameAs: {
        // 确认密码 一致校验, param[0]=密码input的id值, <input id="confirm" />
        validator: function (value, param){
            // console.log('same as', param[0])
            let val = $('input[name="' + param[0] + '"]').val();
            // console.log('same as', value, val)
            return value === val;
        },
        message: '密码必须输入一致'
    },
    selectBox: {
        // 下拉框，必须选择一项, param[0] = name of input, like <input name=xxx/>
        // <input class="easyui-textbox" value=" " data-options="required:true, validType:['radioBox[&quot;leaf&quot;]'], width:1, height:1" />
        validator: function (value, param) {
            // input name=aaBB, $('input[name="aaBB"]') 找不到远到，jquery只能找到 小写的 aabb，所以这里只能自个过滤了
            let findEmpty = false;
            $('input').each(function (index, element) {
                let name = $(this).attr('name');
                // console.log('--->attrName=' + name)
                if(name != undefined && name != null){
                    if(name == param[0]){
                        let val = $(element).val();
                        val = $.trim(val)
                        if(val == ''){
                            findEmpty = true;
                            return;
                        }
                    }
                }
            });

            if(findEmpty){
                return false;
            }

            return true;
        },
        message: '请选择有效的项目'
    },
    radioBox: {
        // 单选必须选择一项, param[0] = name of input, like <input name=xxx/>
        // <input class="easyui-textbox" value=" " data-options="required:true, validType:['radioBox[&quot;leaf&quot;]'], width:1, height:1" />
        validator: function (value, param) {
            // console.log('validator', param, param[0])
            let $checked = $('input[name="' + param[0] + '"]').filter(':checked');
            let length = $checked.length
            // console.log('radio checked', length)
            return length === 1;
        },
        message: '请选择1项'
    },
    checkBox: {
        // 多选必须选择至少一项, param[0] = name of input, like <input name=xxx/>
        // <input class="easyui-textbox" value=" " data-options="required:true, validType:['checkBox[&quot;leaf&quot;, 1]'], width:1, height:1" />
        validator: function (value, param) {
            if(param[1] === 0){
                return true;
            }

            let $checked = $('input[name="' + param[0] + '"]').filter(':checked');
            let checkedLength = $checked.length
            return checkedLength > 0;
        },
        message: '请选择至少1项'
    },
    minLength: { // 判断最小长度
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '最少输入 {0} 个字符'
    },
    length: { // 长度
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "输入内容长度必须介于{0}和{1}之间"
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    phoneAndMobile: {// 电话号码或手机号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value) || /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '电话号码或手机号码格式不正确'
    },
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value) || /^\d{18}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    integer: {// 验证整数
        validator: function (value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    chinese: {// 验证中文
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/i.test(value);
        },
        message: '请输入中文'
    },
    chineseAndLength: {// 验证中文及长度
        validator: function (value) {
            var len = $.trim(value).length;
            if (len >= param[0] && len <= param[1]) {
                return /^[\u0391-\uFFE5]+$/i.test(value);
            }
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    englishAndLength: {// 验证英语及长度
        validator: function (value, param) {
            var len = $.trim(value).length;
            if (len >= param[0] && len <= param[1]) {
                return /^[A-Za-z]+$/i.test(value);
            }
        },
        message: '请输入英文'
    },
    englishUpperCase: {// 验证英语大写
        validator: function (value) {
            return /^[A-Z]+$/.test(value);
        },
        message: '请输入大写英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    username: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno: {// 验证传真
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    engOrChinese: {// 可以是中文或英文
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入中文'
    },
    engOrChineseAndLength: {// 可以是中文或英文
        validator: function (value) {
            var len = $.trim(value).length;
            if (len >= param[0] && len <= param[1]) {
                return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
            }
        },
        message: '请输入中文或英文'
    },
    carNo: {
        validator: function (value) {
            return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
        },
        message: '车牌号码无效（例：粤B12350）'
    },
    carenergin: {
        validator: function (value) {
            return /^[a-zA-Z0-9]{16}$/.test(value);
        },
        message: '发动机型号无效(例：FG6H012345654584)'
    },
    same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致!'
    }
})
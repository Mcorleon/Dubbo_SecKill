/**
 * Created by mcorl on 2019/2/22.
 */
$(function () {
    //获取当前session下的用户
    var uid;
    $.ajax({
        type: "get",
        url: "/user/getCurrentUser",
        data: {},
        dataType: "json",
        async:false,
        success: function (data) {

            if (data == null) {
                window.location.href = "/login";
            } else {
                console.log(data);
                $("#p1").text("欢迎," + data.nickname);
                uid=data.id;
            }
        },
        error: function () {
            window.location.href = "/login";
            console.log("ajax error");
        }
    });

    layui.use(['layer', 'form', 'table'], function () {
        var form = layui.form,
            layer = layui.layer,
            table = layui.table;
        //表单
        form.render();
        table.render({
            elem: '#tb',
            url: '/order/getOrderVoByUid',
            method:'post',
            limits: [5, 10],
            where: {uid:uid},
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                }
            }
            , cols: [[
                {field: 'id', title: '订单编号', width: 180, align: 'center'}
                , {field: 'goods_id', title: '商品编号', width: 180, align: 'center'}
                , {field: 'goods_name', title: '商品名称', width: 150, align: 'center'}
                , {field: 'goods_num', title: '数量', width: 130, align: 'center'}
                , {field: 'good_price', title: '商品价格', width: 130, align: 'center'}
                , {field: 'address_detail', title: '收货地址', width: 200, align: 'center'}
                , {field: 'stateVo', title: '状态', width: 130, align: 'center'}
                , {field: 'order_time', title: '下单时间', width: 180, align: 'center'}
                , {title: '操作', toolbar: '#barDemo', width: 180, align: 'center'}
            ]]
            , page: true
            ,done:function (res) {

            }
        });
        table.on('tool(tb)', function (obj) {
            var data = obj.data;
            console.log(obj.data);
            if (obj.event === 'buy') {

            }
        });
    });
})

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

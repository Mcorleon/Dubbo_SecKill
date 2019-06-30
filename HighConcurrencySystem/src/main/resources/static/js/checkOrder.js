/**
 * Created by mcorleon 2019/2/22.
 */
$(function () {
    //获取当前session下的用户
    $.ajax({
        type: "get",
        url: "/user/getCurrentUser",
        data: {},
        dataType: "json",
        async: false,
        success: function (data) {

            if (data == null) {
                window.location.href = "/login";
            } else {
                console.log(data);
                $("#nickname").text(data.nickname);
            }
        },
        error: function () {
            window.location.href = "/login";
            console.log("ajax error");
        }
    });
    //获取商品信息
    var miaosha_id = getUrlParam("miaosha_id");
    $.ajax({
        type: "post",
        url: "/goods/getMiaoshaGoodByID",
        data: {id: miaosha_id},
        dataType: "json",
        success: function (data) {
            console.log(data);
            $("#goods_name").text(data.name);
            $("#miaosha_price").text(data.miaosha_price);
        },
        error: function () {
            console.log("ajax error");
        }
    });

    layui.use(['layer', 'form'], function () {
        var form = layui.form,
            layer = layui.layer;
        importAddress();
        form.verify({
            address: function (value) {
                if (value == 0) {
                    return '请选择地址';
                }
            }
        });

        form.on('submit(ok)', function (data) {
            var nickName = $("#nickname").html().trim();
            var goods_num = $("#goods_num").html().trim();
            var address_id = $("#address").val();

            //提交订单
            $.ajax({
                type: "post",
                url: "/order/generateOrder",
                async: false,
                data: {nickName: nickName,miaosha_id:miaosha_id,goods_num:goods_num,address_id:address_id},
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    if(data.code==200){
                        layer.msg("下单成功");
                        window.location.href="/order_list";
                    }else {
                        layer.alert(data.msg,function () {
                            history.go(-1);
                        });
                    }
                },
                error:function (data) {
                    console.log("ajax error");
                    window.location.href="/Err";
                }
            });
        });
        $("#cancel").click(function () {
            history.go(-1);
        });
    });

})


//请求收货地址选项
function importAddress() {
    var nickName = $("#nickname").html().trim();
    $.ajax({
        type: "post",
        url: "/user/getAddressByNickName",
        async: false,
        data: {nickName: nickName},
        dataType: "json",
        success: function (data) {

            $("#address").append("<option value= 0>--请选择--</option>");
            for (var i = 0; i < data.length; i++) {
                $("#address").append("<option value=" + data[i].id + ">" + data[i].address_detail + "</option>")
            }
            refreshForm();
        }
    });
}
//刷新表单
function refreshForm() {
    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        form.render();
    });
}
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

